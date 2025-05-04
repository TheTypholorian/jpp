package net.typho.jpp.assembly;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Assembler {
    private final List<Insn> instructions = new LinkedList<>();
    private final List<byte[]> data = new LinkedList<>();
    private int off = 0;

    public void add(Insn insn) {
        instructions.add(insn);
    }

    public int data(byte[] data) {
        int r = off;

        off += data.length;
        this.data.add(data);

        return r;
    }

    public int codeBytes() {
        int b = 0;

        for (Insn insn : instructions) {
            b += insn.bytes();
        }

        return b;
    }

    public int dataBytes() {
        return off;
    }

    public int totalBytes() {
        return codeBytes() + dataBytes();
    }

    public void write(ASMOutputStream out) throws IOException {
        for (Insn insn : instructions) {
            insn.write(out);
        }
    }

    public byte[] write() throws IOException {
        byte[] bytes = new byte[totalBytes()];

        write(new ASMOutputStream() {
            int i = 0;

            @Override
            public void write(int b) {
                bytes[i++] = (byte) (b & 0xFF);
            }
        });

        return bytes;
    }
}
