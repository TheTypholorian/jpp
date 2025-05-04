package net.typho.jpp.assembly;

import java.io.IOException;

public class MovByteToMemOffsetInsn implements Insn {
    public byte value;
    public Register64 base;
    public int offset;

    public MovByteToMemOffsetInsn(byte value, Register64 base, int offset) {
        this.value = value;
        this.base = base;
        this.offset = offset;
    }

    @Override
    public int bytes() {
        return 4;
    }

    @Override
    public void write(int before, ASMOutputStream out) throws IOException {
        out.write(0xC6);
        out.write(0x40 | base.ordinal());
        out.write(offset);
        out.write(value);
    }
}
