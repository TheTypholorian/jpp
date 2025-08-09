package net.typho.jpp.parsing;

public interface Instruction {
    boolean parse(ParsingStream in);

    static boolean parse(Instruction insn, ParsingStream in) {
        ParsingStream split = in.split();

        if (!insn.parse(split)) {
            return false;
        }

        split.merge();

        return true;
    }
}
