package net.typho.jpp.parsing;

import net.typho.jpp.lexical.LexicalIterator;

public class ParsingException extends RuntimeException {
    public final Object src;
    public final int x, y, width;

    public ParsingException(LexicalIterator it, String message) {
        super(it.src() + " (iterator last returned " + it.last() + ") at row " + it.row() + ", column " + it.col() + ", and " + it.width() + " chars:\n" + message);
        this.src = it.src();
        this.x = it.col();
        this.y = it.row();
        this.width = it.width();
    }
}
