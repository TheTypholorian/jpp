package net.typho.jpp;

import net.typho.jpp.error.NothingMatchesException;
import net.typho.jpp.error.OutOfTextException;
import net.typho.jpp.io.FileLoader;

public interface Project {
    FileLoader resources();

    default NothingMatchesException nothingMatches() {
        return new NothingMatchesException(this);
    }

    default NothingMatchesException nothingMatches(String s) {
        return new NothingMatchesException(this, s);
    }

    default OutOfTextException outOfText() {
        return new OutOfTextException(this);
    }

    default OutOfTextException outOfText(String s) {
        return new OutOfTextException(this, s);
    }

    boolean isDebug();
}
