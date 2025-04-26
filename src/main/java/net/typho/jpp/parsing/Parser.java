package net.typho.jpp.parsing;

import net.typho.jpp.lexical.Lexer;
import net.typho.jpp.lexical.LexicalIterator;

public interface Parser {
    default void parse(Lexer lexer) {
        LexicalIterator it = lexer.iterator();

        while (it.hasNext()) {
            take(it.next(), it);
        }
    }

    void take(String token, LexicalIterator it);

    default void setChild(Parser p) {
        throw new UnsupportedOperationException();
    }
}
