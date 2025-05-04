package net.typho.jpp.assembly;

import java.io.IOException;

public class SyscallInsn implements Insn {
    @Override
    public int bytes() {
        return 2;
    }

    @Override
    public void write(ASMOutputStream out) throws IOException {
        out.write(0x0F);
        out.write(0x05);
    }
}
