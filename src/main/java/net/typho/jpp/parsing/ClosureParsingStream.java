package net.typho.jpp.parsing;

import net.typho.jpp.error.OutOfTextException;

public class ClosureParsingStream implements ParsingStream {
    public final ParsingStream parent;
    public final ClosureType type;
    protected int i;

    public ClosureParsingStream(ParsingStream parent, ClosureType type) {
        this(parent, type, 0);
    }

    public ClosureParsingStream(ParsingStream parent, ClosureType type, int i) {
        this.parent = parent;
        this.type = type;
        this.i = i;
    }

    @Override
    public char readChar() {
        char c = parent.readChar();

        if (c == type.open()) {
            i++;
        } else if (c == type.close()) {
            i--;
        }

        if (i < 0) {
            throw new OutOfTextException("Closure " + type.open() + type.close() + " read past close");
        }

        return c;
    }

    @Override
    public char peekChar() {
        return parent.peekChar();
    }

    @Override
    public boolean hasMore() {
        if (i < 0) {
            return false;
        }

        char c = peekChar();

        if (c == type.open()) {
            return true;
        } else if (c == type.close()) {
            return i > 0;
        }

        return true;
    }

    @Override
    public ParsingStream split() {
        return new ClosureParsingStream(parent.split(), type, i);
    }

    @Override
    public void merge(ParsingStream child) {
    }
}
