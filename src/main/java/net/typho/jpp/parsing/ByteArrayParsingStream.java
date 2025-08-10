package net.typho.jpp.parsing;

import net.typho.jpp.Project;

public class ByteArrayParsingStream implements ParsingStream {
    public final ParsingStream parent;
    public final byte[] b;
    public int i;
    public final Project project;

    public ByteArrayParsingStream(ParsingStream parent, byte[] b, int i, Project project) {
        this.parent = parent;
        this.b = b;
        this.i = i;
        this.project = project;
    }

    public ByteArrayParsingStream(ParsingStream parent, byte[] b, Project project) {
        this.parent = parent;
        this.b = b;
        this.project = project;
    }

    public ByteArrayParsingStream(byte[] b, int i, Project project) {
        this(null, b, i, project);
    }

    public ByteArrayParsingStream(byte[] b, Project project) {
        this(null, b, project);
    }

    @Override
    public char readChar() {
        if (!hasMore()) {
            throw project().outOfText();
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
    public Project project() {
        return project;
    }

    @Override
    public ParsingStream parent() {
        return parent;
    }

    @Override
    public ParsingStream split() {
        return new ByteArrayParsingStream(this, b, i, project());
    }

    @Override
    public void merge(ParsingStream child) {
        if (child instanceof ByteArrayParsingStream bytes) {
            i = bytes.i;
        }
    }
}
