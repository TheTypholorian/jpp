package net.typho.jpp.assembly;

import java.io.IOException;

public class MovByteToMemOffsetInsn implements Insn {
    private final byte value;
    private final Register64 base;
    private final int offset;

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
    public void write(ASMOutputStream out) throws IOException {
        out.write(0xC6); // mov byte ptr
        out.write(0x40 | base.ordinal()); // ModRM with 0b10 (disp32) and base
        out.write(offset); // disp8 or disp32 — assume disp32 for simplicity
        out.write(value);
    }
}
