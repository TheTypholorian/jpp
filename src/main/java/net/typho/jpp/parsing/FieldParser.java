package net.typho.jpp.parsing;

import net.typho.jpp.lexical.LexicalIterator;

public class FieldParser implements Parser {
    public final DefaultParser parent;
    public final ClassAttribParser attrib;
    public final String name;

    public FieldParser(DefaultParser parent, ClassAttribParser attrib, String name) {
        this.parent = parent;
        this.attrib = attrib;
        this.name = name;
    }

    @Override
    public void take(String token, LexicalIterator it) {
        switch (token) {
            case "=": {
                System.out.println("\tInitialized with " + it.concatUntil(";", ","));
                parent.current = attrib.body;
                attrib.body.modifiers.clear();
                break;
            }
            case ";", ",": {
                System.out.println("\tInitialized with null");
                parent.current = attrib.body;
                attrib.body.modifiers.clear();
                break;
            }
        }
    }
}
