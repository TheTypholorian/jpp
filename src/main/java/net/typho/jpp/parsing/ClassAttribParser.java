package net.typho.jpp.parsing;

import net.typho.jpp.lexical.LexicalIterator;

import java.util.LinkedList;
import java.util.List;

public class ClassAttribParser implements Parser {
    public final DefaultParser parent;
    public final ClassBodyParser body;
    String name;

    public ClassAttribParser(DefaultParser parent, ClassBodyParser body) {
        this.parent = parent;
        this.body = body;
    }

    @Override
    public void take(String token, LexicalIterator it) {
        switch (token) {
            case "(": {
                if (name == null) {
                    name = "new";
                }

                System.out.println("\tMethod \"" + name + "\"");

                parent.current = new MethodInfoParser(parent, this, name);
                break;
            }
            case "<": {
                do {
                    body.typeParams.add(it.next());
                } while (!it.next().equals(">"));

                System.out.println("\tType params " + body.typeParams);

                break;
            }
            default: {
                if (name == null) {
                    name = token;
                } else {
                    System.out.println("\tField \"" + name + "\"");
                    (parent.current = new FieldParser(parent, this, name)).take(token, it);
                }
                break;
            }
        }
    }
}
