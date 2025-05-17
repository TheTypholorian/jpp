package net.typho.jpp.assembly;

import java.io.IOException;

public class SubStatic64Insn implements Insn {
    public Register64 dst;
    public int value;

    public SubStatic64Insn(Register64 dst, int value) {
        this.dst = dst;
        this.value = value;
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
            out.write(0xE8 | dst.ordinal()); // /5 for SUB r/m64, imm8
            out.write(value);
        } else {
            out.write(0x81);
            out.write(0xE8 | dst.ordinal()); // /5 for SUB r/m64, imm32
            out.ints(value);
        }
    }
}
