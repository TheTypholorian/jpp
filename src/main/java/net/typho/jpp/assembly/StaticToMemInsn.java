package net.typho.jpp.assembly;

import java.io.IOException;

public class StaticToMemInsn implements Insn {
    public byte[] value;
    public int address;

    public StaticToMemInsn(byte[] value, int address) {
        this.value = value;
        this.address = address;
    }

    @Override
    public int bytes() {
        return 3 + Integer.BYTES + value.length;
    }

    @Override
    public void write(int before, ASMOutputStream out) throws IOException {
        out.write(0x48);
        out.write(0xC7);
        out.write(0xC0);
        out.ints(address);
        out.bytes(value);
    }
}

