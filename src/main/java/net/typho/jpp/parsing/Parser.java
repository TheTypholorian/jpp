package net.typho.jpp.parsing;

import net.typho.jpp.lexical.Lexer;
import net.typho.jpp.lexical.LexicalIterator;

import java.util.NoSuchElementException;

public interface Parser {
    default void parse(Lexer lexer) {
        LexicalIterator it = lexer.iterator();

        while (it.hasNext()) {
            String next;

            try {
                next = it.next();
            } catch (NoSuchElementException e) {
                break;
            }

            take(next, it);
        }
    }

    void take(String token, LexicalIterator it);

    default void setChild(Parser p) {
        throw new UnsupportedOperationException();
    }
}
