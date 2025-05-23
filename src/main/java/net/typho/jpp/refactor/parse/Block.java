package net.typho.jpp.refactor.parse;

import net.typho.jpp.refactor.ParsingException;

import java.util.*;

public class Block implements Token {
    public String name;
    public BlockType type;
    public final Map<ExtraBlockInfo, Object> extra = new LinkedHashMap<>();
    public final List<String> modifiers = new LinkedList<>();

    public Block() {
    }

    @SuppressWarnings("unchecked")
    public Block(Iterator<String> it) {
        while (it.hasNext()) {
            String next = it.next();

            switch (next) {
                case "class": {
                    if (!it.hasNext()) {
                        throw new ParsingException("No class name");
                    }

                    name = it.next();
                    break;
                }
                case "extends", "implements", "inherits", ":": {
                    List<String> parents = (List<String>) extra.computeIfAbsent(ExtraBlockInfo.PARENTS, k -> new LinkedList<>());

                    while (it.hasNext()) {
                        next = it.next();

                        if (next.equals("static")) {
                            parents.add("static " + it.next());
                        } else {
                            parents.add(next);
                        }

                        next = it.next();

                        if (!next.equals(",")) {
                            break;
                        }
                    }
                    break;
                }
                case "alloc": {
                    int i = Integer.parseInt(it.next());
                    extra.compute(ExtraBlockInfo.STACK, (k, v) -> v == null ? i : (int) v + i);
                    break;
                }
                default: {
                    modifiers.add(next);
                    break;
                }
            }
        }
    }

    @Override
    public Type type() {
        return Type.BLOCK;
    }
}
