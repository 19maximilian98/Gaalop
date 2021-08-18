package de.gaalop.codegenGappIntrinsics.sse;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EndNode;
import de.gaalop.cfg.StartNode;
import de.gaalop.dfg.Variable;
import de.gaalop.gapp.*;
import de.gaalop.gapp.instructionSet.*;
import de.gaalop.gapp.variables.GAPPValueHolder;
import de.gaalop.gapp.variables.GAPPVector;

import java.util.*;

public class IntrinsicsVisitor extends de.gaalop.gapp.visitor.CFGGAPPVisitor {



    private StringBuilder result;
    private StringBuilder variables;

    private ArrayList<VectorIntrinsics> vectors;

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

    public IntrinsicsVisitor(){
        result = new StringBuilder();
        variables = new StringBuilder();
        vectorsSizeMap = new HashMap<>();
        vectors = new ArrayList<>();
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

        result.append("#include <immintrin.h>\n" +
                "#include <stddef.h>\n" +
                "#include <stdio.h>\n" +
                "#include <math.h>\n\n");

        result.append("void calculate(");
        List<Variable> localVariables = sortVariables(node.getGraph().getLocalVariables());
        int bladeCount = node.getGraph().getAlgebraDefinitionFile().getBladeCount();
       // result.append("const double *inputsVector, ");
        List<Variable> inputVariables = sortVariables(node.getGraph().getInputVariables());
        for (Variable cur : inputVariables){
            result.append("float ");
            result.append(cur.getName());
            result.append(", ");
        }
        for (Variable var : localVariables) {
            result.append("float").append(" ");
            result.append(var.getName());
            result.append("["+bladeCount+"], ");
        }

        if (node.getGraph().getLocalVariables().size() > 0) {
            result.setLength(result.length() - 2);
        }

        result.append(") {\n");
        result.append("float inputsVector[" + inputVariables.size() + "] = {");
        for (Variable cur : inputVariables) {
            result.append(cur.getName());
            result.append(",  ");
        }
        result.deleteCharAt(result.length() - 1);
        result.deleteCharAt(result.length() - 1);
        result.append("};\n");
       /* result.append(
                "double zero[1] = {0};\n" +
                "vfloat64m1_t vZero = vle64_v_f64m1(zero, 1);\n");*/
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
        result.append("//resetMv ");
        result.append(gappResetMv.getDestination().getName());
        result.append("[");
        result.append(gappResetMv.getSize());
        result.append("];\n");


       if (gappResetMv.getDestination().getName().contains("tempmv")){
           result.append("double " + gappResetMv.getDestination().getName() + "[" + gappResetMv.getSize() + "];\n");
       }

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
                    String element = "";
                    if (selector.getSign() == (byte) -1) {
                        element += "-";
                    }
                    element+=name;
                    element+="[";
                    element+=selector.getIndex();
                    element+="]";
                    elements.add(element);
                }
            }
            //localResult.append(",");
        }
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
