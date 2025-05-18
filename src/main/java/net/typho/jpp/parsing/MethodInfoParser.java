package net.typho.jpp.parsing;

import net.typho.jpp.Literal;
import net.typho.jpp.assembly.*;
import net.typho.jpp.lexical.LexicalIterator;
import net.typho.jpp.tree.MethodNode;

import java.nio.charset.StandardCharsets;
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
                List<String> stack = new LinkedList<>();
                List<Insn> insns = new LinkedList<>();

                while (!(next = it.next()).equals("}")) {
                    switch (next) {
                        case ";": {
                            parent.asm.instructions.addAll(insns);
                            stack.clear();
                            insns.clear();
                            break;
                        }
                        case "(": {
                            insns.add(new MethodInvokeInsn(String.join("", stack)));
                            stack.clear();
                            break;
                        }
                        case ")": {
                            for (String s : stack) {
                                String string = Literal.parseString(s);

                                if (string != null) {
                                    insns.addFirst(new MultiInsn() {{
                                        instructions.add(new InlineData(string.getBytes(StandardCharsets.UTF_8)));
                                        instructions.add(new ByteArrayInsn(0x56));
                                    }});
                                    continue;
                                }

                                try {
                                    long l = Literal.parseInt(s);
                                    byte[] result = new byte[11];

                                    result[0] = 0x48;
                                    result[1] = (byte) 0xB8;
                                    result[2] = (byte) l;
                                    result[3] = (byte) (l >>> 8);
                                    result[4] = (byte) (l >>> 16);
                                    result[5] = (byte) (l >>> 24);
                                    result[6] = (byte) (l >>> 32);
                                    result[7] = (byte) (l >>> 40);
                                    result[8] = (byte) (l >>> 48);
                                    result[9] = (byte) (l >>> 56);
                                    result[10] = 0x50;

                                    insns.addFirst(new ByteArrayInsn(result));
                                    continue;
                                } catch (NumberFormatException ignored) {
                                }
                            }
                            break;
                        }
                        case ",": {
                            break;
                        }
                        default: {
                            stack.add(next);
                            break;
                        }
                    }
                }

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
                    parent.asm.add(new MethodInsn(node) {
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
