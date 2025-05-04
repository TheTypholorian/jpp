package net.typho.jpp.assembly;

import java.io.IOException;

public class Xor32RegisterInsn implements Insn {
    public final Register32 src, dst;

    public Xor32RegisterInsn(Register32 src, Register32 dst) {
        this.src = src;
        this.dst = dst;
    }

    public Xor32RegisterInsn(Register32 reg) {
        this(reg, reg);
    }

    @Override
    public int bytes() {
        return 2;
    }

    @Override
    public void write(int before, ASMOutputStream out) throws IOException {
        out.write(0x31);
        out.write(0b11000000 | (src.ordinal() << 3) | dst.ordinal());
    }
}
