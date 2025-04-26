package net.typho.jpp.parsing;

import net.typho.jpp.lexical.LexicalIterator;

public class MethodInfoParser implements Parser {
    public final DefaultParser parent;
    public final ClassAttribParser attrib;

    public MethodInfoParser(DefaultParser parent, ClassAttribParser attrib) {
        this.parent = parent;
        this.attrib = attrib;
    }

    @Override
    public void take(String token, LexicalIterator it) {
    }
}
