package net.typho.jpp.parsing;

import net.typho.jpp.Project;
import net.typho.jpp.error.OutOfTextException;

public interface ParsingStream {
    char[] DELIMITERS = {' ', '\n', '\t', '\r', '(', ')', '{', '}', '[', ']'};

    char readChar();

    char peekChar();

    boolean hasMore();

    Project project();

    default boolean hasMoreSignificant() {
        ParsingStream split = split();

        while (split.hasMore()) {
            if (!isDelimiter(split.readChar(), DELIMITERS)) {
                return true;
            }
        }

        return false;
    }

    ParsingStream split();

    default ParsingStream parent() {
        return null;
    }

    void merge(ParsingStream child);

    default void merge() {
        parent().merge(this);
    }

    default String readAll() {
        StringBuilder builder = new StringBuilder();

        while (hasMore()) {
            builder.append(readChar());
        }

        return builder.toString();
    }

    default String readToken(char... delimiters) {
        StringBuilder builder = new StringBuilder();

        try {
            while (true) {
                char c = readChar();

                if (!isDelimiter(c, delimiters)) {
                    builder.append(c);
                    break;
                }
            }

            while (true) {
                char c = peekChar();

                if (isDelimiter(c, delimiters)) {
                    return builder.toString();
                }

                builder.append(readChar());
            }
        } catch (OutOfTextException e) {
            return builder.toString();
        }
    }

    default String readToken() {
        return readToken(DELIMITERS);
    }

    static boolean isDelimiter(char c, char... delimiters) {
        for (char d : delimiters) {
            if (c == d) {
                return true;
            }
        }

        return false;
    }

    default ParsingStream closure(ClosureType type) {
        while (true) {
            char c = readChar();

            if (c == type.open()) {
                break;
            }
        }

        return new ClosureParsingStream(this, type);
    }
}
