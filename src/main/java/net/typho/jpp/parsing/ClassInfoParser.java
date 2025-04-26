package net.typho.jpp.parsing;

import net.typho.jpp.lexical.LexicalIterator;

import java.util.LinkedList;
import java.util.List;

public class ClassInfoParser implements Parser {
    public final DefaultParser parent;
    final List<String> parents = new LinkedList<>();

    public ClassInfoParser(DefaultParser parent) {
        this.parent = parent;
    }

    @Override
    public void take(String token, LexicalIterator it) {
        switch (token) {
            case "implements", "extends": {
                while (true) {
                    parents.add(it.next());
                    String next = it.next();

                    if (!next.equals(",")) {
                        System.out.println("Extends " + parents);
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
