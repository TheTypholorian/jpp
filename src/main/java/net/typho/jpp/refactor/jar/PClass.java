package net.typho.jpp.refactor.jar;

import java.util.LinkedList;
import java.util.List;

public class PClass {
    public String name;
    public final List<String> parents = new LinkedList<>();
    public PNamespace namespace;
}
