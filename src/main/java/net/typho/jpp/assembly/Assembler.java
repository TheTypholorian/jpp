package net.typho.jpp.assembly;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Assembler {
    private final List<Insn> instructions = new LinkedList<>();

    public void add(Insn insn) {
        instructions.add(insn);
    }

    public int bytes() {
        int b = 0;

        for (Insn insn : instructions) {
            b += insn.bytes();
        }

        return b;
    }

    public void write(ASMOutputStream out) throws IOException {
        for (Insn insn : instructions) {
            insn.write(out);
        }
    }

    public byte[] write() throws IOException {
        byte[] bytes = new byte[bytes()];

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
