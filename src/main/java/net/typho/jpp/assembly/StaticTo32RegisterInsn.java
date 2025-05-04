package net.typho.jpp.assembly;

import java.io.IOException;

public class StaticTo32RegisterInsn implements Insn {
    public final int value;
    public final Register32 dst;

    public StaticTo32RegisterInsn(int value, Register32 dst) {
        this.value = value;
        this.dst = dst;
    }

    @Override
    public int bytes() {
        return 5;
    }

    @Override
    public void write(ASMOutputStream out) throws IOException {
        out.write(0xB8 | dst.ordinal());
        out.write(new int[]{value});
    }
}
