package de.gaalop.codegenGappIntrinsics.riscvv;

import de.gaalop.StringList;
import de.gaalop.cfg.EndNode;
import de.gaalop.cfg.StartNode;
import de.gaalop.gapp.*;
import de.gaalop.gapp.instructionSet.*;
import de.gaalop.gapp.variables.GAPPValueHolder;
import de.gaalop.gapp.variables.GAPPVector;
import de.gaalop.gapp.visitor.PrettyPrint;

import java.util.*;

public class RISCVVIntrinsicsVisitor extends de.gaalop.gapp.visitor.CFGGAPPVisitor {



    private StringBuilder result;
    private StringBuilder variables;

    private Map<String, Integer> vectorsSizeMap;

    // Müll
    String nextLine = "\n  ";
    String prePointer = "p";
    String preVector = "v";
    String preVL = "l";
    String preN = "n";
    String preDotProd = "r";
    String preZero = "Zero";
    String nextNextLine = "\n    ";

    public RISCVVIntrinsicsVisitor(){
        result = new StringBuilder();
        variables = new StringBuilder();
        vectorsSizeMap = new HashMap<>();
    }

    public String getResultString(){
        return result.toString();
    }

    @Override
    public void visit(StartNode node){

        result.append("#include <riscv_vector.h>\n" +
                "#include <stddef.h>\n" +
                "#include <stdio.h>\n" +
                "#include <math.h>\n\n");

        result.append("void " + node.getGraph().getSource().getName().split("\\.")[0] + "(");
        
        int bladeCount = node.getGraph().getAlgebraDefinitionFile().getBladeCount();
       // result.append("const double *inputsVector, ");
       
        StringList inputs = node.getGraph().getInputs();
        StringList outputs = node.getGraph().getOutputs();
        
        StringList parameters = new StringList();
        for (String cur: inputs)
            parameters.add("double "+cur);
        
        for (String cur: outputs)
            parameters.add("double "+cur+"["+bladeCount+"]");
        
        result.append(parameters.join());

        result.append(") {\n");
        
        // Declare locals, which are no outputs
        for (String var : node.getGraph().getLocals()) {
            if (!outputs.contains(var)) {
                result.append("double "+var+"[" + bladeCount + "] = { 0 };\n");
            }
        }
               
        result.append(
                "double zero[1] = {0};\n" +
                "vfloat64m1_t vZero = vle64_v_f64m1(zero, 1);\n");
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(EndNode node) {
        result.append("}");
    }

    @Override
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
        PrettyPrint printer = new PrettyPrint();
        gappAssignMv.accept(printer, null);
        result.append("//"+printer.getResultString());
        
        PosSelectorset selectors = gappAssignMv.getSelectors();
        Valueset valueset = gappAssignMv.getValues();
        for (int i = 0; i< selectors.size();i++){
            result.append(gappAssignMv.getDestination().getName());
            result.append("[");
            result.append(selectors.get(i).getIndex());
            result.append("]");
            result.append(" = ");
            result.append(valueset.get(i).prettyPrint());
            result.append(";\n");
        }
        return null;
    }

    @Override
    public Object visitDotVectors(GAPPDotVectors gappDotVectors, Object arg) {
        PrettyPrint printer = new PrettyPrint();
        gappDotVectors.accept(printer, null);
        result.append("//"+printer.getResultString());
        
        LinkedList<GAPPVector> vectors = gappDotVectors.getParts();

        result.append("{");
        result.append(nextLine);
        result.append("double *");
        result.append(prePointer + preDotProd);
        result.append(" = &");
        result.append(gappDotVectors.getDestination().getName());
        result.append("[");
        result.append(gappDotVectors.getDestSelector().getIndex());
        result.append("]");
        result.append(";");
        result.append(nextLine);



        result.append("size_t " + preN + " = ");

        int dotSize = vectors.size();
        int vectorSize = 0;
        for (Map.Entry<String, Integer> entry : vectorsSizeMap.entrySet()) {
            if (entry.getKey().equals(vectors.get(0).getName())) {
                vectorSize = entry.getValue();
            }
        }
        result.append(vectorSize);
        result.append(";");
        result.append(nextLine);
        result.append("size_t " + preVL + ";");
        result.append(nextLine);
        result.append("vfloat64m1_t " + preVector +"0, " + preVector + "1;");
        result.append(nextLine);
        result.append("vfloat64m1_t " + preVector +"r = vle64_v_f64m1(zero, 1);");
        result.append(nextLine);



        result.append("for (; " + preN + " > 0; " + preN + " -= " + preVL + ") {");
        result.append(nextNextLine);
        result.append(preVL + " = vsetvl_e64m1(" + preN + ");");
        result.append(nextNextLine);
        result.append( preVector + "0 = vle64_v_f64m1(" + prePointer + vectors.get(0).getName() + ", l);");
        result.append(nextNextLine);
        result.append(prePointer + vectors.get(0).getName() + "+= l;");
        result.append(nextNextLine);
        for (int i = 1; i < dotSize; i++){
            result.append(preVector +  "1 = vle64_v_f64m1(" + prePointer + vectors.get(i).getName() + ", l);");
            result.append(nextNextLine);
            result.append(prePointer + vectors.get(i).getName() + "+= l;");
            result.append(nextNextLine);
            result.append(preVector + "0 = vfmul_vv_f64m1(" + preVector + "0, " + preVector + "1, l);");
            result.append(nextNextLine);
        }
        result.append(preVector + preDotProd + " = vfredosum_vs_f64m1_f64m1 (" + preVector + preDotProd + ", " + preVector + "0, " + preVector + "r" + ", l);");
        result.append(nextNextLine);

        result.append("}");
        result.append(nextLine);



        //result.append("vse64_v_f64m1 ("+ gappDotVectors.getDestination().getName() + "[" + gappDotVectors.getDestSelector().getIndex() + "]" + ", " + preVector + preDotProd + ", 1);");

        result.append("vse64_v_f64m1 ("+ prePointer + preDotProd + ", " + preVector + preDotProd + ", 1);");
        result.append(nextLine);
        result.append("}\n");



        return null;
    }

    @Override
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
        PrettyPrint printer = new PrettyPrint();
        gappResetMv.accept(printer, null);
        result.append("//"+printer.getResultString());

       if (gappResetMv.getDestination().getName().contains("tempmv")){
           result.append("double " + gappResetMv.getDestination().getName() + "[" + gappResetMv.getSize() + "];\n");
       }


        result.append("{");
        result.append(nextLine);
        result.append("size_t " + preN + " = ");
        result.append(gappResetMv.getSize());
        result.append(";");
        result.append(nextLine);
        result.append("size_t " + preVL + ";");
        result.append(nextLine);
        result.append("double *" + prePointer + "0 = " + gappResetMv.getDestination().getName() + ";");
        result.append(nextLine);
        result.append("vfloat64m1_t " + preVector + "0;");
        result.append(nextLine);
        result.append("for (; " + preN + " > 0; " + preN + " -= " + preVL + ") {");
        result.append(nextNextLine);
        result.append(preVL + " = vsetvl_e64m1(" + preN + ");");
        result.append(nextNextLine);
        result.append(preVector + "0 = " + "vfmv_v_f_f64m1(0," + preVL + ");");
        result.append(nextNextLine);
        result.append("vse64_v_f64m1 ("+ prePointer + "0, " + preVector + "0, " + preVL + ");");
        result.append(nextNextLine);
        result.append(prePointer + "0 += l;");
        result.append(nextLine);
        result.append("}\n");
        result.append("}\n");
        return null;
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        PrettyPrint printer = new PrettyPrint();
        gappSetMv.accept(printer, null);
        result.append("//"+printer.getResultString());

        PosSelectorset selectors = gappSetMv.getSelectorsDest();
        for (int i = 0; i< selectors.size();i++){
            result.append(gappSetMv.getDestination().getName());
            result.append("[");
            result.append(selectors.get(i).getIndex());
            result.append("]");
            result.append(" = ");
            result.append(gappSetMv.getSource().getName());
            result.append("[");
            result.append(gappSetMv.getSelectorsSrc().get(i).getIndex());
            result.append("]");
            result.append(";\n");
        }
        return null;
    }

    @Override
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {
        PrettyPrint printer = new PrettyPrint();
        gappSetVector.accept(printer, null);
        result.append("//"+printer.getResultString());

        result.append("double ");
        result.append(gappSetVector.getDestination().getName());
        
        Integer vectorSize = 0;
        
        StringList localList = new StringList();
        for (SetVectorArgument cur : gappSetVector.getEntries()){
            if (cur.isConstant()) {
                localList.add(""+((ConstantSetVectorArgument) cur).getValue());
                vectorSize++;
            } else {
                PairSetOfVariablesAndIndices pair = (PairSetOfVariablesAndIndices) cur;
                String name = pair.getSetOfVariable().getName();
                Selectorset selectorset = pair.getSelectors();
                for (Selector selector : selectorset){
                    vectorSize++;
                    StringBuilder localResult = new StringBuilder();
                    if (selector.getSign() == (byte) -1) {
                        localResult.append('-');
                    }
                    localResult.append(name);
                    localResult.append("[");
                    localResult.append(selector.getIndex());
                    localResult.append("]");
                    localList.add(localResult.toString());
                }
            }
            //localResult.append(",");
        }

        vectorsSizeMap.put(gappSetVector.getDestination().getName(), vectorSize);
        result.append("[");
        result.append(vectorSize);
        result.append("] = {");
        result.append(localList.join());
        result.append("}");
        result.append(";\n");

        result.append("const double *p" );
        result.append(gappSetVector.getDestination().getName());
        result.append(" = ");
        result.append(gappSetVector.getDestination().getName());
        result.append(";\n");
        return null;
    }

    @Override
    public Object visitCalculateMv(GAPPCalculateMv gappCalculateMv, Object arg) {
        PrettyPrint printer = new PrettyPrint();
        gappCalculateMv.accept(printer, null);
        result.append("//"+printer.getResultString());
        
        return null;
    }

    @Override
    public Object visitCalculateMvCoeff(GAPPCalculateMvCoeff gappCalculateMvCoeff, Object arg) {
        PrettyPrint printer = new PrettyPrint();
        gappCalculateMvCoeff.accept(printer, null);
        result.append("//"+printer.getResultString());
        
        result.append("");
     //   result.append("{\n").append("double " + preVector + " = " + gappCalculateMvCoeff.getOperand1().getName() + "[0];\n");
        result.append(gappCalculateMvCoeff.getDestination().getName());
        result.append("[");
        result.append(gappCalculateMvCoeff.getDestination().getBladeIndex());
        result.append("] = ");
        CalculationType type = gappCalculateMvCoeff.getType();
        switch (type){
            case DIVISION:
                result.append(gappCalculateMvCoeff.getOperand1().getName());
                result.append("[0] / ");
                result.append(gappCalculateMvCoeff.getOperand2().getName());
                result.append("[0]");
                break;
            case EXPONENTIATION:
                result.append("pow(" +  gappCalculateMvCoeff.getOperand1().getName() + "[0], " + gappCalculateMvCoeff.getOperand2().getName() + "[0]");
                result.append(")");
                break;
            case INVERT:
                throw new IllegalArgumentException("Invert not allowed in calculateMvCoeff!");
            case ABS:
                result.append("abs(" +  gappCalculateMvCoeff.getOperand1().getName() + "[0]");
                result.append(")");
                break;
            case ACOS:
                result.append("acos(" +  gappCalculateMvCoeff.getOperand1().getName() + "[0]");
                result.append(")");
                break;
            case ASIN:
                result.append("asin(" +  gappCalculateMvCoeff.getOperand1().getName() + "[0]");
                result.append(")");
                break;
            case ATAN:
                result.append("atan(" +  gappCalculateMvCoeff.getOperand1().getName() + "[0]");
                result.append(")");
                break;
            case CEIL:
                result.append("ceil(" +  gappCalculateMvCoeff.getOperand1().getName() + "[0]");
                result.append(")");
                break;
            case COS:
                result.append("cos(" + gappCalculateMvCoeff.getOperand1().getName() + "[0]");
                result.append( ")");
                break;
            case EXP:
                result.append("exp(" +  gappCalculateMvCoeff.getOperand1().getName() + "[0]");
                result.append( ")");
                break;
            case FACT:
                result.append("fact(" +  gappCalculateMvCoeff.getOperand1().getName() + "[0]");
                result.append( ")");
                break;
            case FLOOR:
                result.append("floor(" +  gappCalculateMvCoeff.getOperand1().getName() + "[0]");
                result.append( ")");
                break;
            case LOG:
                result.append("log(" +  gappCalculateMvCoeff.getOperand1().getName() + "[0]");
                result.append(")");
                break;
            case SIN:
                result.append("sin(" +  gappCalculateMvCoeff.getOperand1().getName() + "[0]");
                result.append(")");
                break;
            case SQRT:
                result.append("sqrt(" +  gappCalculateMvCoeff.getOperand1().getName() + "[0]");
                result.append( ")");
                break;
            case TAN:
                result.append("tan(" +  gappCalculateMvCoeff.getOperand1().getName() + "[0]");
                result.append(")");
                break;
        }
        result.append(";\n");
        return null;
    }

    @Override
    public Object visitAssignInputsVector(GAPPAssignInputsVector gAPPAssignInputsVector, Object arg) {
        PrettyPrint printer = new PrettyPrint();
        gAPPAssignInputsVector.accept(printer, null);
        result.append("//"+printer.getResultString());
        
        StringList localList = new StringList();
        for (GAPPValueHolder holder: gAPPAssignInputsVector.getValues()) 
            localList.add(holder.prettyPrint());
                
        result.append("double inputsVector[" + gAPPAssignInputsVector.getValues().size() + "] = { ");
        result.append(localList.join());
        result.append(" };\n");
        
        return null;
    }

}
