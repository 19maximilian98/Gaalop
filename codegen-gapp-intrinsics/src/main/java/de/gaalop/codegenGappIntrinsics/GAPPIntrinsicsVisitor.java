package de.gaalop.codegenGappIntrinsics;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EndNode;
import de.gaalop.cfg.StartNode;
import de.gaalop.dfg.Variable;
import de.gaalop.gapp.*;
import de.gaalop.gapp.instructionSet.*;
import de.gaalop.gapp.variables.GAPPValueHolder;
import de.gaalop.gapp.variables.GAPPVector;

import java.util.*;

public class GAPPIntrinsicsVisitor extends de.gaalop.gapp.visitor.CFGGAPPVisitor {



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

    public GAPPIntrinsicsVisitor(){
        result = new StringBuilder();
        variables = new StringBuilder();
        vectorsSizeMap = new HashMap<>();
    }

    public String getResultString(){
        return result.toString();
    }

    @Override
    public void visit(AssignmentNode node) {
       /* result.append("//");
        result.append(node.getVariable().toString());
        result.append(" = ");
        result.append(node.getValue().toString());
        result.append('\n');*/



        if (node.getGAPP() != null) {
            node.getGAPP().accept(this, null);
        }
        node.getSuccessor().accept(this);

    }

    @Override
    public void visit(StartNode node){

        result.append("#include <riscv_vector.h>\n" +
                "#include <stddef.h>\n" +
                "#include <stdio.h>\n" +
                "#include <math.h>\n\n");

        result.append("void calculate(");
        List<Variable> localVariables = sortVariables(node.getGraph().getLocalVariables());
        int bladeCount = node.getGraph().getAlgebraDefinitionFile().getBladeCount();
       // result.append("const double *inputsVector, ");
        List<Variable> inputVariables = sortVariables(node.getGraph().getInputVariables());
        for (Variable cur : inputVariables){
            result.append("double ");
            result.append(cur.getName());
            result.append(", ");
        }
        for (Variable var : localVariables) {
            result.append("double").append(" ");
            result.append(var.getName());
            result.append("["+bladeCount+"], ");
        }

        if (node.getGraph().getLocalVariables().size() > 0) {
            result.setLength(result.length() - 2);
        }

        result.append(") {\n");
        result.append("double inputsVector[" + inputVariables.size() + "] = {");
        for (Variable cur : inputVariables) {
            result.append(cur.getName());
            result.append(",  ");
        }
        result.deleteCharAt(result.length() - 1);
        result.deleteCharAt(result.length() - 1);
        result.append("};\n");
        result.append(
                "double zero[1] = {0};\n" +
                "vfloat64m1_t vZero = vle64_v_f64m1(zero, 1);\n");
        node.getSuccessor().accept(this);
    }

    /**
     * Sorts a set of variables by name to make the order deterministic.
     *
     * @param inputVariables
     * @return
     */
    protected List<Variable> sortVariables(Set<Variable> inputVariables) {
        List<Variable> variables = new ArrayList<Variable>(inputVariables);
        Comparator<Variable> comparator = new Comparator<Variable>() {

            @Override
            public int compare(Variable o1, Variable o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        };

        Collections.sort(variables, comparator);
        return variables;
    }


    @Override
    public void visit(EndNode node) {
        result.append("}");
        result.append("\n\nint main() {\n" +
                "\n" +
                "  \n" +
                "double x4a[32] = {0};" +
                "double DualPP4[32] = {0};" +
                "double a1 = 0; double a2 = 0;double a3=0;\n" +
                "double b1=0; double b2=0.4;double b3=0;\n" +
                "double c1=0;double c2=0.45;double c3=0.2;\n" +
                "double d14=0.5;double d24=0.4;double d34=0.3;" +
                "calculate(a1, a2, a3, b1, b2, b3, c1, c2, c3, d14, d24, d34, DualPP4, x4a);" +
                "  printf(\"x4a: %f\\n\", x4a[0]);\n" +
               /* "  double inputsVector[8] = {0.5, 0.5, 0.5, 0.4, 1.0, 0.0, 0.0, 0.0};\n" +
                "  double a_Refl[32];\n" +
                "  double DotProduct[32] = {0};\n" +
                "  \n" +
                "double a1 = 0.5;\n" +
                "  double a2 = 0.5;\n" +
                "  double a3 = 0.5;\n" +
                "  double a4 = 0.4;\n" +
                "  double m1 = 0;\n" +
                "  double m2 = 0;\n" +
                "  double m3 = 0;\n" +
                "  double m4 = 0;" +
                "  \n" +
                "  calculate(a1, a2, a3, a4, m1, m2, m3, m4,a_Refl, DotProduct);\n" +
                "  printf(\"DotProduct: %f\\n\", DotProduct[0]);\n" +
                "  printf(\"a_Refl: %f, %f, %f, %f, %f, %f\\n\", a_Refl[0], a_Refl[1], a_Refl[2], a_Refl[3], a_Refl[4], a_Refl[5]);" +
                */
                "return 0;" +
                "}");
    }




    @Override
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
        result.append("//assignMv ");
        result.append(gappAssignMv.getDestination().getName());
        //printMultivector(gappAssignMv.getDestination());
        result.append("[");
        PosSelectorset selectors = gappAssignMv.getSelectors();
        for (PosSelector cur : selectors){
            result.append(cur.getIndex());
            result.append(";");
        }
        result.deleteCharAt(result.length() - 1);
        result.append("]");
        // printPosSelectors(gappAssignMv.getSelectors());
        result.append(" = ");
        Valueset valueset = gappAssignMv.getValues();
        result.append("[");
        for (GAPPValueHolder cur : valueset) {
            result.append(cur.prettyPrint());
            result.append(",");
        }
        result.deleteCharAt(result.length() - 1);
        result.append("]");
        //printValueSet(gappAssignMv.getValues());
        result.append(";\n");

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
        result.append("//dotVectors ");
        result.append(gappDotVectors.getDestination().getName());
        // printMultivector(gappDotVectors.getDestination());
        result.append("[");
        result.append(gappDotVectors.getDestSelector().getIndex());
        // printSelector(gappDotVectors.getDestSelector());
        result.append("]");
        result.append(" = ");
        result.append("<");

        LinkedList<GAPPVector> vectors = gappDotVectors.getParts();
        ListIterator<GAPPVector> it = vectors.listIterator();
        while (it.hasNext()) {
            result.append(it.next().getName());
           // printVector(it.next());
            if (it.hasNext()) {
                result.append(",");
            }
        }

        result.append(">");
        //printDotProduct(gappDotVectors.getParts());
        result.append(";\n");



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
        result.append("vfloat64m1_t " + preVector +"0, " + preVector + "1, "+ preVector + "r;");
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
        result.append(preVector + preDotProd + " = vfredosum_vs_f64m1_f64m1 (" + preVector + preDotProd + ", " + preVector + "0, " + preVector + preZero + ", l);");
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
        result.append("//resetMv ");
        result.append(gappResetMv.getDestination().getName());
        result.append("[");
        result.append(gappResetMv.getSize());
        result.append("];\n");

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
        result.append("//setVector ");
        result.append(gappSetVector.getDestination().getName());
        //printVector(gappSetVector.getDestination());
        result.append(" = ");


        LinkedList<SetVectorArgument> list = gappSetVector.getEntries();
        result.append("{");
        for (SetVectorArgument cur : list) {
            if (cur.isConstant())
                result.append(((ConstantSetVectorArgument) cur).getValue());
            else {
                PairSetOfVariablesAndIndices pair = (PairSetOfVariablesAndIndices) cur;
                result.append(pair.getSetOfVariable().prettyPrint());
                result.append("[");
                Selectorset selectorset = pair.getSelectors();
                for (Selector cur2 : selectorset) {
                    if (cur2.getSign() == (byte) -1) {
                        result.append('-');
                    }
                    result.append(cur2.getIndex());
                    result.append(",");
                }
                result.deleteCharAt(result.length() - 1);
                result.append("]");
               // printSelectors(pair.getSelectors());
            }
            result.append(",");
        }
        result.deleteCharAt(result.length() - 1);
        result.append("}");
        //printListOfArguments(gappSetVector.getEntries());


        result.append(";\n");

        result.append("double ");
        result.append(gappSetVector.getDestination().getName());
        StringBuilder localResult = new StringBuilder();
        Integer vectorSize = 0;

        for (SetVectorArgument cur : gappSetVector.getEntries()){
            if (cur.isConstant()) {
                localResult.append(((ConstantSetVectorArgument) cur).getValue());
                vectorSize++;
                localResult.append(",");
            } else {
                PairSetOfVariablesAndIndices pair = (PairSetOfVariablesAndIndices) cur;
                String name = pair.getSetOfVariable().getName();
                Selectorset selectorset = pair.getSelectors();
                for (Selector selector : selectorset){
                    vectorSize++;
                    if (selector.getSign() == (byte) -1) {
                        localResult.append('-');
                    }
                    localResult.append(name);
                    localResult.append("[");
                    localResult.append(selector.getIndex());
                    localResult.append("],");
                }
            }
            //localResult.append(",");
        }
        localResult.deleteCharAt(localResult.length() - 1);
        vectorsSizeMap.put(gappSetVector.getDestination().getName(), vectorSize);
        result.append("[");
        result.append(vectorSize);
        result.append("] = {");
        result.append(localResult);
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
        return null;
    }

    @Override
    public Object visitCalculateMvCoeff(GAPPCalculateMvCoeff gappCalculateMvCoeff, Object arg) {
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
                result.append("-" +  gappCalculateMvCoeff.getOperand1().getName() + "[0]");
                break;
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
        result.append("//assignInputsVector inputsVector = ");
        Variableset variableset = gAPPAssignInputsVector.getValues();
        result.append("[");
        for (GAPPValueHolder cur : variableset) {
            result.append(cur.prettyPrint());
            result.append(",");
        }
        result.deleteCharAt(result.length() - 1);
        result.append("]");
        // printVariableSet(gappAssignInputsVector.getValues());
        result.append(";\n");


        return null;
    }

}
