package net.typho.jpp.assembly;

import java.io.IOException;

public class AddStatic64Insn implements Insn {
    public Register64 dst;
    public int value;

    public AddStatic64Insn(Register64 dst, int imm) {
        this.dst = dst;
        this.value = imm;
    }

    protected boolean small() {
        return value == (byte) value;
    }

    @Override
    public int bytes() {
        return small() ? 4 : 7;
    }

    @Override
    public void write(int before, ASMOutputStream out) throws IOException {
        out.write(0x48);

        if (small()) {
            out.write(0x83);
            out.write(0xC0 | dst.ordinal());
            out.write(value);
        } else {
            out.write(0x81);
            out.write(0xC0 | dst.ordinal());
            out.ints(value);
        }
    }
}