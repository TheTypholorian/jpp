package net.typho.jpp.parsing;

import java.util.LinkedList;
import java.util.List;

public class SimpleLanguage implements Language {
    protected final List<InstructionSupplier> instructions = new LinkedList<>();

    @Override
    public Instruction parse(ParsingStream in) {
        for (InstructionSupplier supplier : instructions) {
            Instruction insn = supplier.create();

            if (insn.splitParse(in)) {
                return insn;
            }
        }

        return null;
    }
}
