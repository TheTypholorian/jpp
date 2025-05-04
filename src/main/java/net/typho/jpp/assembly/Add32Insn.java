package net.typho.jpp.assembly;

import java.io.IOException;

public class Add32Insn implements Insn {
    public final Register32 src, dst;

    public Add32Insn(Register32 dst, Register32 src) {
        this.dst = dst;
        this.src = src;
    }

    @Override
    public int bytes() {
        return 2;
    }

    @Override
    public void write(ASMOutputStream out) throws IOException {
        out.write(0x01);
        out.write(0xC0 | (src.ordinal() << 3) | dst.ordinal());
    }
}
