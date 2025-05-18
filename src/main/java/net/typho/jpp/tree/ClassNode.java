package net.typho.jpp.tree;

import java.util.List;
import java.util.Objects;

public class ClassNode {
    public String type;
    public List<String> parents;
    public List<MethodNode> methods;

    public MethodNode getMethod(String name) {
        for (MethodNode method : methods) {
            if (Objects.equals(method.name, name)) {
                return method;
            }
        }

        return null;
    }
}
