package de.gaalop.codegenGappIntrinsics;

import de.gaalop.DefaultCodeGenerator;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.codegenGappIntrinsics.riscvv.RISCVVIntrinsicsVisitor;
import de.gaalop.codegenGappIntrinsics.sse3.SSE3IntrinsicsVisitor;


/**
 * Implements a code generator for C++ intrinsics with SSE3 or for RISCV-V processors.
 * 
 * @author 19maximilian98
 */
public class IntrinsicsCodeGenerator extends DefaultCodeGenerator {
    
    private final Plugin plugin;

    public IntrinsicsCodeGenerator(Plugin plugin) {
        super("c");
        this.plugin = plugin;
    }

    /**
     * Generates source code for a control dataflow graph.
     *
     * @param in The graph that should be used for code generation
     * @return The genereated code
     */
    @Override
    protected String generateCode(ControlFlowGraph in) {
        if (plugin.intrinsicType.equals("sse3")) {
            SSE3IntrinsicsVisitor sse3IntrinsicsVisitor = new SSE3IntrinsicsVisitor();
            in.accept(sse3IntrinsicsVisitor);
            return sse3IntrinsicsVisitor.getResultString();
        } else {
            RISCVVIntrinsicsVisitor rISCVVIntrinsicsVisitor = new RISCVVIntrinsicsVisitor();
            in.accept(rISCVVIntrinsicsVisitor);
            return  rISCVVIntrinsicsVisitor.getResultString();
        }
    }
}
