package net.typho.jpp.parsing;

public interface ParsingStream {
    char readChar();

    char peekChar();

    boolean hasMore();

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

    default String readToken() {
        StringBuilder builder = new StringBuilder();

        while (true) {
            char c = readChar();

            if (!isDelimiter(c)) {
                builder.append(c);
                break;
            }
        }

        while (true) {
            char c = readChar();

            if (isDelimiter(c)) {
                return builder.toString();
            }

            builder.append(c);
        }
    }

    static boolean isDelimiter(char c) {
        return switch (c) {
            case ' ', '\n', '\t', '\r', '(', ')', '{', '}', '[', ']' -> true;
            default -> false;
        };
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
