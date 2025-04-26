package net.typho.jpp.lexical;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class StringLexer implements Lexer {
    public final List<Token> tokens = new LinkedList<>();
    public final Object src;

    public StringLexer(Path p) throws IOException {
        this(p, Files.readString(p));
    }

    public StringLexer(Object src, String s) {
        this.src = src;

        StringBuilder[] b = {new StringBuilder()};
        int[] pos = {1, 1, 0};

        Runnable end = () -> {
            String res = b[0].toString().replaceAll(" ", "");

            if (!res.isEmpty()) {
                tokens.add(new Token(res, pos[0], pos[1], pos[2]));
                b[0] = new StringBuilder();

                if (!res.equals("\n")) {
                    pos[1] += pos[2];
                    pos[2] = 0;
                }
            }
        };

        s.chars().forEach(c -> {
            switch (c) {
                case '\r': {
                    return;
                }
                case ' ': {
                    end.run();
                    return;
                }
                case '\'', '"', '[', ']', '{', '}', '\\', '|', '`', '~', '<', '>', '/', '?', '.', ',', '-', '=', '+', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', ';', ':', '\n': {
                    end.run();

                    if (c == '\n') {
                        pos[0]++;
                        pos[1] = 0;
                        pos[2] = 0;
                    }

                    tokens.add(new Token(String.valueOf((char) c), pos[0], pos[1], pos[2]));

                    if (c != '\n') {
                        pos[1]++;
                        pos[2] = 1;
                    }
                    return;
                }
            }

            b[0].append((char) c);
        });

        end.run();
    }

    @Override
    public LexicalIterator iterator() {
        return LexicalIterator.of(src, tokens.iterator());
    }
}
