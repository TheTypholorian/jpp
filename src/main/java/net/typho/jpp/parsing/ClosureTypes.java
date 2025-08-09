package net.typho.jpp.parsing;

public enum ClosureTypes implements ClosureType {
    PARENTHESES('(', ')'),
    SQUARE('[', ']'),
    CURLY('{', '}');

    public final char open, close;

    ClosureTypes(char open, char close) {
        this.open = open;
        this.close = close;
    }

    @Override
    public char open() {
        return open;
    }

    @Override
    public char close() {
        return close;
    }
}
