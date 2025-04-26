package net.typho.jpp.parsing;

import net.typho.jpp.lexical.LexicalIterator;

import java.util.LinkedList;
import java.util.List;

public class ClassBodyParser implements Parser {
    public final DefaultParser parent;
    public final ClassInfoParser info;
    final List<String> modifiers = new LinkedList<>();
    String type = null;

    public ClassBodyParser(DefaultParser parent, ClassInfoParser info) {
        this.parent = parent;
        this.info = info;
    }

    @Override
    public void take(String token, LexicalIterator it) {
        switch (token) {
            case "public", "private", "static", "final": {
                modifiers.add(token);
                break;
            }
            case "}": {
                parent.current = info;
                break;
            }
            default: {
                type = token;
                System.out.println("Unknown attribute " + modifiers + " " + type);
                parent.current = new ClassAttribParser(parent, this);
                break;
            }
        }
    }
}
