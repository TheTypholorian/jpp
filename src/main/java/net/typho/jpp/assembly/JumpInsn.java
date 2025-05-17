package net.typho.jpp.assembly;

import java.io.IOException;

public class JumpInsn implements Insn {
    public int num;

    public JumpInsn(int num) {
        this.num = num;
    }

    @Override
    public int bytes() {
        return (num <= 127 && num >= -128) ? 2 : 5;
    }

    @Override
    public void write(int before, ASMOutputStream out) throws IOException {
        if (num <= 127 && num >= -128) {
            out.write(0xEB);
            out.write(num);
        } else {
            out.write(0xE9);
            out.ints(num);
        }
    }
}
