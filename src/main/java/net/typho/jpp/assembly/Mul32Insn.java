package net.typho.jpp.assembly;

import java.io.IOException;

public class Mul32Insn implements Insn {
    public Register32 src, dst;

    public Mul32Insn(Register32 dst, Register32 src) {
        this.dst = dst;
        this.src = src;
    }

    @Override
    public int bytes() {
        return 3;
    }

    @Override
    public void write(ASMOutputStream out) throws IOException {
        out.write(0x0F);
        out.write(0xAF);
        out.write(0xC0 | (dst.ordinal() << 3) | src.ordinal());
    }
}
