package net.typho.jpp.lexical;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public interface LexicalIterator extends Iterator<String> {
    default void takeUntil(Consumer<String> out, String... s) {
        while (true) {
            String next = next();

            for (String c : s) {
                if (next.equals(c)) {
                    return;
                }
            }

            out.accept(next);
        }
    }

    default List<String> concatUntil(String... s) {
        List<String> list = new LinkedList<>();

        takeUntil(list::add, s);

        return list;
    }

    Object src();

    int row();

    int col();

    int width();

    static LexicalIterator of(Object src, Iterator<Token> it) {
        return new LexicalIterator() {
            Token last = null;

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public String next() {
                last = it.next();
                return last.text();
            }

            @Override
            public Object src() {
                return src;
            }

            @Override
            public int row() {
                return last == null ? 1 : last.row();
            }

            @Override
            public int col() {
                return last == null ? 1 : last.col();
            }

            @Override
            public int width() {
                return last == null ? 1 : last.width();
            }
        };
    }
}
