package net.typho.jpp.assembly;

import java.io.IOException;

public class JumpInsn implements Insn {
    public int num;

    public JumpInsn(int num) {
        this.num = num;
    }

    @Override
    public int bytes() {
        return num == (byte) num ? 2 : 5;
    }

    @Override
    public void write(int before, ASMOutputStream out) throws IOException {
        if (num == (byte) num) {
            out.write(0xEB);
            out.write(num);
        } else {
            out.write(0xE9);
            out.ints(num);
        }
    }
}
