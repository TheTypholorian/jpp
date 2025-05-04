package net.typho.jpp.assembly;

import java.io.IOException;

public class ByteArrayInsn implements Insn {
    public final byte[] b;

    public ByteArrayInsn(byte... b) {
        this.b = b;
    }

    public ByteArrayInsn(int... b) {
        this.b = new byte[b.length];

        for (int i = 0; i < b.length; i++) {
            this.b[i] = (byte) b[i];
        }
    }

    @Override
    public int bytes() {
        return b.length;
    }

    @Override
    public void write(ASMOutputStream out) throws IOException {
        out.write(b);
    }
}
