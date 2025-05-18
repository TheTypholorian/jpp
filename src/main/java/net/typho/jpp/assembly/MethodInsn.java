package net.typho.jpp.assembly;

import net.typho.jpp.tree.MethodNode;

public abstract class MethodInsn extends MultiInsn {
    public MethodInsn(MethodNode node) {
        MethodStartInsn start = new MethodStartInsn(node);
        instructions.add(start);
        node.local = body();
        instructions.add(new MethodEndInsn(start));
    }

    protected abstract int body();
}
