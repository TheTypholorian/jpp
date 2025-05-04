package net.typho.jpp.assembly;

import java.io.IOException;

public class Static64ToRegisterInsn implements Insn {
    public final long value;
    public final Register64 dst;

    public Static64ToRegisterInsn(long value, Register64 dst) {
        this.value = value;
        this.dst = dst;
    }

    @Override
    public int bytes() {
        return 11;
    }

    @Override
    public void write(ASMOutputStream out) throws IOException {
        out.write(0x48);
        out.write(0xC7);
        out.write(0xC0 | dst.ordinal());
        out.longs(value);
    }
}
