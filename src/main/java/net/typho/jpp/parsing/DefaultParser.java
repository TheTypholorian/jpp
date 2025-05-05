package net.typho.jpp.parsing;

import net.typho.jpp.assembly.Assembler;
import net.typho.jpp.lexical.LexicalIterator;

import java.util.LinkedList;
import java.util.List;

public class DefaultParser implements Parser {
    final List<String> modifiers = new LinkedList<>(), imports = new LinkedList<>();
    public Parser current;
    public Assembler asm = new Assembler(this);

    final Parser root = new RootParser(this);

    public DefaultParser() {
        current = root;
    }

    @Override
    public void take(String token, LexicalIterator it) {
        current.take(token, it);
    }
}
