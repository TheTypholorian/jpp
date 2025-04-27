package net.typho.jpp.parsing;

import net.typho.jpp.lexical.LexicalIterator;

public class RootParser implements Parser {
    public final DefaultParser parent;

    public RootParser(DefaultParser parent) {
        this.parent = parent;
    }

    @Override
    public void take(String token, LexicalIterator it) {
        switch (token) {
            case "import": {
                String next = it.next();
                String imp = "";

                if (!next.equals("static")) {
                    imp = next;
                }

                imp += String.join("", it.concatUntil(";"));
                System.out.println("Import " + imp);
                parent.imports.add(imp);
                break;
            }
            case "public", "private": {
                parent.modifiers.add(token);
                break;
            }
            case "class", "interface", "enum": {
                System.out.println("Create " + token + " " + parent.modifiers + " " + it.next());
                parent.modifiers.clear();
                parent.current = new ClassInfoParser(parent, token);
                break;
            }
        }
    }
}
