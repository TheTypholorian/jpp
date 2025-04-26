package net.typho.jpp.parsing;

import net.typho.jpp.lexical.LexicalIterator;

import java.util.LinkedList;
import java.util.List;

public class ClassAttribParser implements Parser {
    public final DefaultParser parent;
    public final ClassBodyParser body;
    String name;
    final List<String> typeParams = new LinkedList<>();

    public ClassAttribParser(DefaultParser parent, ClassBodyParser body) {
        this.parent = parent;
        this.body = body;
    }

    @Override
    public void take(String token, LexicalIterator it) {
        switch (token) {
            case "(": {
                if (name == null) {
                    System.out.println("Constructor");
                    parent.current = new ConstructorParser(parent, this);
                } else {
                    System.out.println("Method");
                    parent.current = new MethodInfoParser(parent, this);
                }

                break;
            }
            case "<": {
                do {
                    typeParams.add(it.next());
                } while (!it.next().equals(">"));

                System.out.println("Type params " + typeParams);

                break;
            }
            default: {
                if (name == null) {
                    name = token;
                } else {
                    System.out.println("Field");
                    (parent.current = new FieldParser(parent, this)).take(token, it);
                }
                break;
            }
        }
    }
}
