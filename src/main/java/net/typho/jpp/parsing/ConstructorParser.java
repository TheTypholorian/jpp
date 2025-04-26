package net.typho.jpp.parsing;

import net.typho.jpp.lexical.LexicalIterator;

public class ConstructorParser implements Parser {
    public final DefaultParser parent;
    public final ClassAttribParser attrib;

    public ConstructorParser(DefaultParser parent, ClassAttribParser attrib) {
        this.parent = parent;
        this.attrib = attrib;
    }

    @Override
    public void take(String token, LexicalIterator it) {
    }
}
