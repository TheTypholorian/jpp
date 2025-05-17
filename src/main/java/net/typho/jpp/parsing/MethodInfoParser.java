package net.typho.jpp.parsing;

import net.typho.jpp.Literal;
import net.typho.jpp.assembly.Insn;
import net.typho.jpp.assembly.MethodInsn;
import net.typho.jpp.lexical.LexicalIterator;
import net.typho.jpp.tree.MethodNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MethodInfoParser implements Parser {
    public final DefaultParser parent;
    public final ClassAttribParser attrib;
    public final MethodNode node = new MethodNode();

    public MethodInfoParser(DefaultParser parent, ClassAttribParser attrib, String name) {
        this.parent = parent;
        this.attrib = attrib;
        node.parent = attrib.body.info.node;
        node.parent.methods.add(node);
        node.name = name;
        node.ret = attrib.body.type;
        node.modifiers = attrib.body.modifiers;
    }

    protected void handle(String next, LexicalIterator it) {
        switch (next) {
            case "{": {
                int brackets = 1, lines = 0;

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
                        case ";": {
                            lines++;
                            break;
                        }
                    }
                }

                System.out.println("\t" + lines + " lines");
                break;
            }
            case ";": {
                System.out.println("\tNo method body");
                break;
            }
            case "throws": {
                while (true) {
                    System.out.println("\tThrows " + it.next());

                    next = it.next();

                    if (!next.equals(",")) {
                        handle(next, it);
                        break;
                    }
                }

                break;
            }
            default: {
                throw new ParsingException(it, "Unexpected token " + next);
            }
        }
    }

    protected void body(LexicalIterator it, List<Insn> instructions) {
        while (it.hasNext()) {
            String next = it.next();

            if (next.equals("}")) {
                break;
            }

            parent.asm.take(next, it, instructions);
        }
    }

    protected void code(LexicalIterator it) {
        if (node.modifiers.contains("native")) {
            String start = it.next();
            int alloc;

            if (start.equals("alloc")) {
                alloc = (int) Literal.parseInt(it.next());
                start = it.next();
            } else {
                alloc = 0;
            }

            if (start.equals("{")) {
                if (!Objects.equals(node.name, "main")) {
                    parent.asm.add(new MethodInsn(node.name) {
                        @Override
                        protected int body() {
                            MethodInfoParser.this.body(it, instructions);
                            return alloc;
                        }
                    });
                } else {
                    body(it, parent.asm.instructions);
                }
            }
        } else {
            handle(it.next(), it);
        }

        parent.current = attrib.body;
        attrib.body.modifiers = new LinkedList<>();
    }

    @Override
    public void take(String token, LexicalIterator it) {
        switch (token) {
            case ")": {
                code(it);
                break;
            }
            default: {
                StringBuilder builder = new StringBuilder(token);

                while (true) {
                    String next = it.next();

                    if (next.equals(",")) {
                        break;
                    } else if (next.equals(")")) {
                        code(it);
                        break;
                    }

                    builder.append(" ").append(next);
                }

                System.out.println("\tArgument " + builder);
            }
        }
    }
}
