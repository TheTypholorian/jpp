package net.typho.jpp.parsing;

import net.typho.jpp.lexical.LexicalIterator;

import java.util.LinkedList;
import java.util.List;

public class ClassBodyParser implements Parser {
    public final DefaultParser parent;
    public final ClassInfoParser info;
    List<String> modifiers = new LinkedList<>(), typeParams = new LinkedList<>();
    String type = null;

    public ClassBodyParser(DefaultParser parent, ClassInfoParser info) {
        this.parent = parent;
        this.info = info;
    }

    @Override
    public void take(String token, LexicalIterator it) {
        switch (token) {
            case "public", "private", "protected", "static", "final", "default", "native": {
                modifiers.add(token);
                break;
            }
            case "}": {
                parent.current = info;
                break;
            }
            case "@": {
                StringBuilder s = new StringBuilder(it.next());
                String next = it.next();

                if (next.equals("(")) {
                    int brackets = 1;

                    while (true) {
                        next = it.next();

                        if (next.equals(")")) {
                            brackets--;

                            if (brackets == 0) {
                                break;
                            }
                        } else if (next.equals("(")) {
                            brackets++;
                        } else {
                            s.append(" ").append(next);
                        }
                    }
                } else {
                    take(next, it);
                }

                System.out.println("\tAnnotation @" + s);

                break;
            }
            case "<": {
                do {
                    typeParams.add(it.next());
                } while (!it.next().equals(">"));

                System.out.println("\tType params " + typeParams);

                break;
            }
            default: {
                type = token;
                System.out.println("\tUnknown attribute " + modifiers + " " + type);
                parent.current = new ClassAttribParser(parent, this);
                break;
            }
        }
    }
}
