package net.typho.jpp.assembly;

import net.typho.jpp.tree.ClassNode;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MultiInsn implements Insn {
    protected final List<Insn> instructions = new LinkedList<>();

    @Override
    public int bytes() {
        int b = 0;

        for (Insn insn : instructions) {
            b += insn.bytes();
        }

        return b;
    }

    @Override
    public void write(int before, ASMOutputStream out) throws IOException {
        for (Insn insn : instructions) {
            insn.write(before, out);
            before += insn.bytes();
        }
    }

    @Override
    public void post(byte[] b, int i, ClassNode node) {
        for (Insn insn : instructions) {
            insn.post(b, i, node);
            i += insn.bytes();
        }
    }
}
