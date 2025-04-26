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
                String imp = it.concatUntil(";");
                System.out.println("Import " + imp);
                parent.imports.add(imp);
                break;
            }
            case "public", "private": {
                parent.modifiers.add(token);
                break;
            }
            case "class": {
                System.out.println("Create class " + parent.modifiers + " " + it.next());
                parent.modifiers.clear();
                parent.current = new ClassInfoParser(parent);
                break;
            }
        }
    }
}
