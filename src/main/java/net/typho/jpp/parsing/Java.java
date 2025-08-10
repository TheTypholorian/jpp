package net.typho.jpp.parsing;

import net.typho.jpp.parsing.insn.If;
import net.typho.jpp.parsing.insn.InvokeMethod;

import java.util.Collections;

public class Java extends SimpleLanguage {
    public static final InstructionSupplier[] INSTRUCTIONS = {
            If::new,
            InvokeMethod::new
    };

    public Java() {
        Collections.addAll(instructions, INSTRUCTIONS);
    }
}
