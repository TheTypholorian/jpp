package net.typho.jpp.parsing;

import net.typho.jpp.lexical.LexicalIterator;
import net.typho.jpp.tree.ClassNode;

import java.util.LinkedList;

public class ClassInfoParser implements Parser {
    public final DefaultParser parent;
    public final ClassNode node = new ClassNode();

    public ClassInfoParser(DefaultParser parent, String type) {
        this.parent = parent;
        node.type = type;
        node.parents = new LinkedList<>();
        node.methods = new LinkedList<>();
        parent.classes.add(node);
    }

    @Override
    public void take(String token, LexicalIterator it) {
        switch (token) {
            case "implements", "extends": {
                while (true) {
                    node.parents.add(it.next());
                    String next = it.next();

                    if (!next.equals(",")) {
                        System.out.println("Extends " + node.parents);
                        take(next, it);
                        break;
                    }
                }

                break;
            }
            case "{": {
                parent.current = new ClassBodyParser(parent, this);
                break;
            }
        }
    }
}
