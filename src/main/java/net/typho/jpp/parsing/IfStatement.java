package net.typho.jpp.parsing;

public class IfStatement implements Instruction {
    @Override
    public boolean parse(ParsingStream in) {
        if (!in.readToken().equals("if")) {
            return false;
        }

        ParsingStream bool = in.closure(ClosureTypes.PARENTHESES);
        System.out.println("if [" + bool.readAll() + "]");

        ParsingStream then = in.closure(ClosureTypes.CURLY);
        System.out.println("then [" + then.readAll() + "]");

        return true;
    }
}
