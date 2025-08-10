package net.typho.jpp.parsing;

import java.util.Iterator;

public interface Language {
    Instruction parse(ParsingStream in);

    default Iterable<Instruction> parseAll(ParsingStream in) {
        return () -> new Iterator<>() {
            @Override
            public boolean hasNext() {
                return in.hasMoreSignificant();
            }

            @Override
            public Instruction next() {
                return parse(in);
            }
        };
    }
}
