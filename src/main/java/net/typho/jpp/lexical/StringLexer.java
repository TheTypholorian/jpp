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
        this(p, Files.readAllLines(p));
    }

    public StringLexer(Object src, List<String> list) {
        this.src = src;

        StringBuilder[] b = {new StringBuilder()};
        int[] pos = {1, 1};

        Runnable end = () -> {
            String og = b[0].toString();
            String res = og.replaceAll(" ", "");

            if (!res.isEmpty()) {
                int len = og.length();
                tokens.add(new Token(res, pos[0], pos[1] - len, len));
                b[0] = new StringBuilder();
            }
        };

        boolean commented = false;

        for (String s : list) {
            boolean inString = false;
            boolean commented1 = commented;

            c:
            for (int i = 0; i < s.length() - 1; i++) {
                char c = s.charAt(i);

                switch (c) {
                    case '/': {
                        if (!inString) {
                            switch (s.charAt(i + 1)) {
                                case '/': {
                                    int len = s.length();
                                    s = s.substring(0, i) + " ".repeat(len - i);
                                    break c;
                                }
                                case '*': {
                                    if (!commented) {
                                        int len = s.length();
                                        s = s.substring(0, i) + " ".repeat(len - i);
                                        commented = true;
                                        break c;
                                    }
                                }
                            }
                        }
                        break;
                    }
                    case '*': {
                        if (commented && !inString) {
                            switch (s.charAt(i + 1)) {
                                case '/': {
                                    s = s.substring(i + 2);
                                    commented = false;
                                    break c;
                                }
                            }
                        }
                    }
                    case '"': {
                        inString = !inString;
                        break;
                    }
                }
            }

            if (!commented1 || !commented) {
                s.chars().forEach(c -> {
                    pos[1]++;

                    switch (c) {
                        case ' ': {
                            end.run();
                            return;
                        }
                        case '\'', '"', '[', ']', '{', '}', '\\', '|', '`', '~', '<', '>', '/', '?', '.', ',', '-', '=',
                             '+', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', ';', ':': {
                            end.run();

                            tokens.add(new Token(String.valueOf((char) c), pos[0], pos[1] - 1, 1));
                            return;
                        }
                    }

                    b[0].append((char) c);
                });
            }
        }

        pos[0]++;
        pos[1] = 0;

        end.run();
    }

    @Override
    public LexicalIterator iterator() {
        return LexicalIterator.of(src, tokens.iterator());
    }
}
