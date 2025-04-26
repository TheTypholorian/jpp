package net.typho.jpp.lexical;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

public interface LexicalIterator extends ListIterator<String> {
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
}
