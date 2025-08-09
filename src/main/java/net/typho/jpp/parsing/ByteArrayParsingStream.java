package net.typho.jpp.parsing;

import net.typho.jpp.error.OutOfTextException;

public class ByteArrayParsingStream implements ParsingStream {
    public final ParsingStream parent;
    public final byte[] b;
    public int i;

    public ByteArrayParsingStream(ParsingStream parent, byte[] b, int i) {
        this.parent = parent;
        this.b = b;
        this.i = i;
    }

    public ByteArrayParsingStream(ParsingStream parent, byte[] b) {
        this.parent = parent;
        this.b = b;
    }

    public ByteArrayParsingStream(byte[] b, int i) {
        this(null, b, i);
    }

    public ByteArrayParsingStream(byte[] b) {
        this(null, b);
    }

    @Override
    public char readChar() {
        if (!hasMore()) {
            throw new OutOfTextException();
        }

        return (char) b[i++];
    }

    @Override
    public char peekChar() {
        return (char) b[i];
    }

    @Override
    public boolean hasMore() {
        return i < b.length - 1;
    }

    @Override
    public ParsingStream parent() {
        return parent;
    }

    @Override
    public ParsingStream split() {
        return new ByteArrayParsingStream(this, b, i);
    }

    @Override
    public void merge(ParsingStream child) {
        if (child instanceof ByteArrayParsingStream bytes) {
            i = bytes.i;
        }
    }
}
