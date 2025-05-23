package net.typho.jpp.refactor.parse;

public interface Token {
    Type type();

    enum Type {
        BLOCK
    }
}
