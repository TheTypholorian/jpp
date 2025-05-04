package net.typho.jpp.assembly;

import java.io.IOException;

public class Add64Insn implements Insn {
    public final Register64 src, dst;

    public Add64Insn(Register64 dst, Register64 src) {
        this.dst = dst;
        this.src = src;
    }

    @Override
    public int bytes() {
        return 3;
    }

    @Override
    public void write(ASMOutputStream out) throws IOException {
        out.write(0x48);
        out.write(0x01);
        out.write(0xC0 | (src.ordinal() << 3) | dst.ordinal());
    }
}
