package net.typho.jpp.lexical;

import java.util.Iterator;
import java.util.function.Consumer;

public interface LexicalIterator extends Iterator<String> {
    default void takeUntil(String s, Consumer<String> out) {
        while (true) {
            String next = next();

            if (next.equals(s)) {
                return;
            }

            out.accept(next);
        }
    }

    default String concatUntil(String s) {
        StringBuilder b = new StringBuilder();

        takeUntil(s, b::append);

        return b.toString();
    }
}
