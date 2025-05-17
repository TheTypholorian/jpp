package net.typho.jpp.assembly;

public abstract class MethodInsn extends MultiInsn {
    public MethodInsn(String name) {
        MethodStartInsn start = new MethodStartInsn(name);
        instructions.add(start);
        start.local = body();
        instructions.add(new MethodEndInsn(start));
    }

    protected abstract int body();
}
