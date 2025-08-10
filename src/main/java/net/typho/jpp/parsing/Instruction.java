package net.typho.jpp.parsing;

public interface Instruction {
    boolean parse(ParsingStream in);

    default boolean splitParse(ParsingStream in) {
        ParsingStream split = in.split();

        if (!parse(split)) {
            return false;
        }

        split.merge();

        return true;
    }
}
