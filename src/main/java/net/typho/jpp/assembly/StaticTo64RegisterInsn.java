package net.typho.jpp.assembly;

import java.io.IOException;

public class StaticTo64RegisterInsn implements Insn {
    public final long value;
    public final Register64 dst;

    public StaticTo64RegisterInsn(long value, Register64 dst) {
        this.value = value;
        this.dst = dst;
    }

    @Override
    public int bytes() {
        return 10;
    }

    @Override
    public void write(ASMOutputStream out) throws IOException {
        out.write(0x48);
        out.write(0xB8 | dst.ordinal());
        out.longs(value);
    }
}
