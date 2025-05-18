package net.typho.jpp.assembly;

import net.typho.jpp.tree.ClassNode;
import net.typho.jpp.tree.MethodNode;

public class MethodInvokeInsn extends MultiInsn {
    public final String name;
    public final AddStatic64Insn insn;

    public MethodInvokeInsn(String name) {
        this.name = name;
        instructions.add(new ByteArrayInsn(0xE8, 0, 0, 0, 0));
        instructions.add(insn = new AddStatic64Insn(Register64.rsp, 0) {
            @Override
            protected boolean small() {
                return false;
            }
        });
    }

    @Override
    public void post(byte[] b, int i, ClassNode node) {
        super.post(b, i, node);

        MethodNode m = node.getMethod(name);
        int address = m.address - i - 5;

        b[i + 1] = (byte) address;
        b[i + 2] = (byte) (address >> 8);
        b[i + 3] = (byte) (address >> 16);
        b[i + 4] = (byte) (address >> 24);

        b[i + 8] = (byte) m.local;
        b[i + 9] = (byte) (m.local >> 8);
        b[i + 10] = (byte) (m.local >> 16);
        b[i + 11] = (byte) (m.local >> 24);
    }
}
