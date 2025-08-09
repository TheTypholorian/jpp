package net.typho.jpp.parsing;

import net.typho.jpp.error.OutOfTextException;

public class ByteArrayParsingStream implements ParsingStream {
    public final byte[] b;
    public int i;

    public ByteArrayParsingStream(byte[] b) {
        this.b = b;
    }

    public ByteArrayParsingStream(byte[] b, int i) {
        this.b = b;
        this.i = i;
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
    public ParsingStream split() {
        return new ByteArrayParsingStream(b, i);
    }

    @Override
    public void merge(ParsingStream child) {
        if (child instanceof ByteArrayParsingStream bytes) {
            i = bytes.i;
        }
    }
}
