package net.typho.jpp.refactor;

import java.util.LinkedList;
import java.util.List;

public interface PIterator<T> {
    String location();

    boolean hasNext();

    T next();

    T peek();

    interface Token<T> {
        T get();

        String location();
    }

    static <T> PIterator<T> of(Token<T>[] arr) {
        return new PIterator<>() {
            int i = 0;

            @Override
            public String location() {
                return arr[i].location();
            }

            @Override
            public boolean hasNext() {
                return i < arr.length;
            }

            @Override
            public T next() {
                return arr[i++].get();
            }

            @Override
            public T peek() {
                return arr[i].get();
            }
        };
    }

    static PIterator<String> of(String s) {
        List<String> list = new LinkedList<>();


    }
}
