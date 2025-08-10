package net.typho.jpp.parsing.insn;

import net.typho.jpp.parsing.ClosureTypes;
import net.typho.jpp.parsing.Instruction;
import net.typho.jpp.parsing.ParsingStream;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class InvokeMethod implements Instruction {
    public String name;
    public String[] arguments;

    @Override
    public boolean parse(ParsingStream in) {
        name = in.readToken();

        ParsingStream args = in.closure(ClosureTypes.PARENTHESES);

        List<String> list = new LinkedList<>();

        while (args.hasMoreSignificant()) {
            list.add(args.readToken(',').trim());
        }

        arguments = list.toArray(new String[0]);

        return true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + name + Arrays.toString(arguments);
    }
}
