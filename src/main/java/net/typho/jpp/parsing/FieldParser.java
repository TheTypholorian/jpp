package net.typho.jpp.parsing;

import net.typho.jpp.lexical.LexicalIterator;

public class FieldParser implements Parser {
    public final DefaultParser parent;
    public final ClassAttribParser attrib;

    public FieldParser(DefaultParser parent, ClassAttribParser attrib) {
        this.parent = parent;
        this.attrib = attrib;
    }

    @Override
    public void take(String token, LexicalIterator it) {
        switch (token) {
            case "=": {
                break;
            }
        }
    }
}
