package net.typho.jpp.parsing.insn;

import net.typho.jpp.parsing.ClosureTypes;
import net.typho.jpp.parsing.Instruction;
import net.typho.jpp.parsing.ParsingStream;

public class If implements Instruction {
    public String predicate, then;

    @Override
    public boolean parse(ParsingStream in) {
        if (!in.readToken().equals("if")) {
            return false;
        }

        predicate = in.closure(ClosureTypes.PARENTHESES).readAll().trim();
        then = in.closure(ClosureTypes.CURLY).readAll().trim();

        return true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " (" + predicate + ") {\n\t" + then + "\n}";
    }
}
