package de.gaalop.codegenGappIntrinsics.sse3;

import de.gaalop.StringList;
import de.gaalop.cfg.EndNode;
import de.gaalop.cfg.StartNode;
import de.gaalop.gapp.*;
import de.gaalop.gapp.instructionSet.*;
import de.gaalop.gapp.variables.GAPPValueHolder;
import de.gaalop.gapp.visitor.PrettyPrint;

import java.util.*;

public class SSE3IntrinsicsVisitor extends de.gaalop.gapp.visitor.CFGGAPPVisitor {



    private StringBuilder result;
    private StringBuilder variables;

    private ArrayList<VectorIntrinsics> vectors;

    private Map<String, Integer> vectorsSizeMap;
    
    private Variableset inputsVectorVariableSet;
    
    // Müll
    String nextLine = "\n  ";
    String prePointer = "p";
    String preVector = "v";
    String preVL = "l";
    String preN = "n";
    String preDotProd = "r";
    String preZero = "Zero";
    String nextNextLine = "\n    ";

    public SSE3IntrinsicsVisitor(){
        result = new StringBuilder();
        variables = new StringBuilder();
        vectorsSizeMap = new HashMap<>();
        vectors = new ArrayList<>();
    }

    public String getResultString(){
        return result.toString();
    }

    @Override
    public void visit(StartNode node){

        result.append("#include <immintrin.h>\n" +
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
            parameters.add("float "+cur);
        
        for (String cur: outputs)
            parameters.add("float "+cur+"["+bladeCount+"]");
        
        result.append(parameters.join());
      

        result.append(") {\n");
        
        // Declare locals, which are no outputs
        for (String var : node.getGraph().getLocals()) {
            if (!outputs.contains(var)) {
                result.append("double "+var+"[" + bladeCount + "] = { 0 };\n");
            }
        }
        
       /* result.append(
                "double zero[1] = {0};\n" +
                "vfloat64m1_t vZero = vle64_v_f64m1(zero, 1);\n");*/
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
        

        result.append("{\n");

        int vectorSize = getVectorByName(gappDotVectors.getParts().get(0).getName()).getSize();
        ArrayList<String> regs = new ArrayList<>();
        for (int i = 0; i<vectorSize; i+=4){
            regs.add("_" + i/4);
        }
        result.append("__m128 ");
        for (String i: regs){
            result.append("v0" + i +",v1"+i+",");
        }
        result.deleteCharAt(result.length()-1);
        result.append(";\n");

        for (int i = 0; i<vectorSize; i+=4){
            VectorIntrinsics v0 = getVectorByName(gappDotVectors.getParts().get(0).getName());
            result.append("v0"+ regs.get(i/4) + " = _mm_set_ps(" + v0.getElement(i+3) + ", " + v0.getElement(i+2) + ", " + v0.getElement(i+1) + ", " + v0.getElement(i) + ");\n");
            //gappDotVectors.getParts().remove(0);
            for (int i2 =1;i2<gappDotVectors.getParts().size();i2++) {

                    VectorIntrinsics v1 = getVectorByName(gappDotVectors.getParts().get(i2).getName());
                    result.append("v1" + regs.get(i / 4) + "= _mm_set_ps(" + v1.getElement(i + 3) + ", " + v1.getElement(i + 2) + ", " + v1.getElement(i + 1) + ", " + v1.getElement(i) + ");\n");
                    result.append("v0" + regs.get(i / 4) + " = _mm_mul_ps(v0" + regs.get(i / 4) + ", v1" + regs.get(i / 4) + ");\n");
            }
        }
        while (regs.size()>1) {
            for (int i = 0; ; i++) {

                if (i >= regs.size() - 1) {
                    break;
                }
                result.append("v0" + regs.get(i) + "=_mm_add_ps(v0" + regs.get(i) + ",v0" + regs.get(regs.size() - 1)+");\n");
                regs.remove(regs.size() - 1);
                break;
            }
        }

        if (vectorSize==1){
            result.append(gappDotVectors.getDestination().getName() + "[" + gappDotVectors.getDestSelector().getIndex() + "] = _mm_cvtss_f32(v0_0);\n");
        } else
        if (vectorSize==2){
            result.append("v0_0 = _mm_add_ss( v0_0, _mm_movehdup_ps( v0_0 ) );\n");
            result.append(gappDotVectors.getDestination().getName() + "[" + gappDotVectors.getDestSelector().getIndex() + "] = _mm_cvtss_f32(v0_0);\n");
        } else {
            result.append("v0_0 = _mm_add_ps( v0_0, _mm_movehl_ps( v0_0, v0_0 ) );\n");
            result.append("v0_0 = _mm_add_ss( v0_0, _mm_movehdup_ps( v0_0 ) );\n");
            result.append(gappDotVectors.getDestination().getName() + "[" + gappDotVectors.getDestSelector().getIndex() + "] = _mm_cvtss_f32(v0_0);\n");
        }

        //result.append(";\n");
        result.append("}\n");
        return null;
    }

    VectorIntrinsics getVectorByName(String name){
        for (VectorIntrinsics i : this.vectors)
            if (i.name.equals(name))
                return i;
        return null;
    }

    @Override
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
        PrettyPrint printer = new PrettyPrint();
        gappResetMv.accept(printer, null);
        result.append("//"+printer.getResultString());


       if (gappResetMv.getDestination().getName().contains("tempmv")){
           result.append("float " + gappResetMv.getDestination().getName() + "[" + gappResetMv.getSize() + "];\n");
       }

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
            if (gappSetMv.getSelectorsSrc().get(i).getSign()== (byte) -1){
                result.append("-");
            }
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

        VectorIntrinsics temp = new VectorIntrinsics(gappSetVector.getDestination().getName());
        vectors.add(temp);
        ArrayList<String> elements = temp.getElements();
        for (SetVectorArgument cur : gappSetVector.getEntries()){
            if (cur.isConstant()) {
                elements.add(String.valueOf( ((ConstantSetVectorArgument) cur).getValue()));
            } else {
                PairSetOfVariablesAndIndices pair = (PairSetOfVariablesAndIndices) cur;
                String name = pair.getSetOfVariable().getName();
                Selectorset selectorset = pair.getSelectors();

                for (Selector selector : selectorset){
                    if (name.equals("inputsVector")){
                        elements.add(((selector.getSign() == (byte) -1) ? "-" : "") + inputsVectorVariableSet.get(selector.getIndex()).prettyPrint());
                    }
                    else {
                        String element = "";

                        if (selector.getSign() == (byte) -1) {
                            element += "-";
                        }
                        element += name;
                        element += "[";
                        element += selector.getIndex();
                        element += "]";
                        elements.add(element);
                    }
                }
            }
            //localResult.append(",");
        }
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
        inputsVectorVariableSet = gAPPAssignInputsVector.getValues();
        
        PrettyPrint printer = new PrettyPrint();
        gAPPAssignInputsVector.accept(printer, null);
        result.append("//"+printer.getResultString());
        
        StringList localList = new StringList();
        for (GAPPValueHolder holder: gAPPAssignInputsVector.getValues()) 
            localList.add(holder.prettyPrint());
        
        result.append("float inputsVector[" + gAPPAssignInputsVector.getValues().size() + "] = { ");
        result.append(localList.join());
        result.append(" };\n");
        
        return null;
    }

}
