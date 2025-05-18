package net.typho.jpp.tree;

import java.util.List;

public class MethodNode {
    public ClassNode parent;
    public String name, ret;
    public List<String> modifiers;
    public int address = 0, local = 0;
}
