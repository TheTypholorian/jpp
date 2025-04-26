package net.typho.jpp.parsing;

import net.typho.jpp.lexical.LexicalIterator;

public class MethodInfoParser implements Parser {
    public final DefaultParser parent;
    public final ClassAttribParser attrib;
    public final String name;

    public MethodInfoParser(DefaultParser parent, ClassAttribParser attrib, String name) {
        this.parent = parent;
        this.attrib = attrib;
        this.name = name;
    }

    @Override
    public void take(String token, LexicalIterator it) {
        switch (token) {
            case ")": {
                String next = it.next();

                if (!next.equals("{")) {
                    throw new ParsingException();
                }

                int brackets = 1;

                while (brackets != 0) {
                    next = it.next();

                    switch (next) {
                        case "{": {
                            brackets++;
                            break;
                        }
                        case "}": {
                            brackets--;
                            break;
                        }
                    }
                }

                System.out.println();

                parent.current = attrib.body;
                attrib.body.modifiers.clear();
                break;
            }
            default: {
                StringBuilder builder = new StringBuilder(token);

                while (true) {
                    String next = it.next();

                    if (next.equals(",")) {
                        break;
                    } else if (next.equals(")")) {
                        it.previous();
                        break;
                    }

                    builder.append(" ").append(next);
                }

                System.out.println("\tArgument " + builder);
            }
        }
    }
}
