package net.typho.jpp.assembly;

import java.io.IOException;

public class Static32ToRegisterInsn implements Insn {
    public final int value;
    public final Register64 dst;

    public Static32ToRegisterInsn(int value, Register64 dst) {
        this.value = value;
        this.dst = dst;
    }

    @Override
    public int bytes() {
        return 7;
    }

    @Override
    public void write(ASMOutputStream out) throws IOException {
        out.write(0x48);
        out.write(0xC7);
        out.write(0xC0 | dst.ordinal());
        out.write(new int[]{value});
    }
}
