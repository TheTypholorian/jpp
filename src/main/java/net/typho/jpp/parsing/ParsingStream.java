package net.typho.jpp.parsing;

import java.util.function.Predicate;

public interface ParsingStream {
    char readChar();

    char peekChar();

    boolean hasMore();

    ParsingStream split();

    void merge(ParsingStream child);

    default void merge() {
        merge(this);
    }

    default String readAll() {
        StringBuilder builder = new StringBuilder();

        while (hasMore()) {
            builder.append(readChar());
        }

        return builder.toString();
    }

    default String readUntil(Predicate<Character> predicate) {
        StringBuilder builder = new StringBuilder();

        while (true) {
            char c = readChar();

            if (predicate.test(c)) {
                return builder.toString();
            }

            builder.append(c);
        }
    }

    default String readToken() {
        return readUntil(ParsingStream::shouldBreak);
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

    static boolean shouldBreak(char c) {
        return switch (c) {
            case ' ', '\n', '\t', '\r', '(', ')', '{', '}', '[', ']' -> true;
            default -> false;
        };
    }
}
