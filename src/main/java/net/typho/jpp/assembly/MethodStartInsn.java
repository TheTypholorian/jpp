package net.typho.jpp.assembly;

import net.typho.jpp.tree.MethodNode;

import java.io.IOException;

public class MethodStartInsn extends MultiInsn {
    public final MethodNode node;
    protected final SubStatic64Insn insn;

    public MethodStartInsn(MethodNode node) {
        this.node = node;
        instructions.add(new ByteArrayInsn(0x55, 0x48, 0x89, 0xE5));
        instructions.add(insn = new SubStatic64Insn(Register64.rsp, node.local) {
            @Override
            protected boolean small() {
                return false;
            }
        });
    }

    @Override
    public void write(int before, ASMOutputStream out) throws IOException {
        node.address = before;
        insn.value = node.local;
        super.write(before, out);
    }
}
