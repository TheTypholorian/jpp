package net.typho.jpp.refactor.lexical;

import net.typho.jpp.refactor.PIterator;

public class Lexer {
    public Lexer(String s) {
        this(PIterator.of(s));
    }

    public Lexer(PIterator<String> it) {
    }
}
