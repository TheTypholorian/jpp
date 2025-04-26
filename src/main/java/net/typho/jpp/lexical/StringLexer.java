package net.typho.jpp.lexical;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class StringLexer implements Lexer {
    public final List<String> tokens = new LinkedList<>();

    public StringLexer() {
    }

    public StringLexer(String s) {
        parse(s);
    }

    public void parse(String s) {
        StringBuilder[] b = {new StringBuilder()};

        Runnable end = () -> {
            String res = b[0].toString().trim();

            if (!res.isEmpty()) {
                tokens.add(res);
                b[0] = new StringBuilder();
            }
        };

        s.chars().forEach(c -> {
            switch (c) {
                case ' ': {
                    end.run();
                    return;
                }
                case '\'', '"', '[', ']', '{', '}', '\\', '|', '`', '~', '<', '>', '/', '?', '.', ',', '-', '=', '+', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', ';', ':': {
                    end.run();

                    tokens.add(String.valueOf((char) c));
                    return;
                }
                case '\n': {
                    end.run();
                    break;
                }
            }

            b[0].append((char) c);
        });

        end.run();
    }

    @Override
    public LexicalIterator iterator() {
        return new LexicalIterator() {
            public final ListIterator<String> it = tokens.listIterator();

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public String next() {
                return it.next();
            }

            @Override
            public boolean hasPrevious() {
                return it.hasPrevious();
            }

            @Override
            public String previous() {
                return it.previous();
            }

            @Override
            public int nextIndex() {
                return it.nextIndex();
            }

            @Override
            public int previousIndex() {
                return it.previousIndex();
            }

            @Override
            public void remove() {
                it.remove();
            }

            @Override
            public void set(String s) {
                it.set(s);
            }

            @Override
            public void add(String s) {
                it.add(s);
            }
        };
    }
}
