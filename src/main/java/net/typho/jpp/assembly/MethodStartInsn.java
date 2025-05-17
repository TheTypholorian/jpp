package net.typho.jpp.assembly;

import java.io.IOException;

public class MethodStartInsn extends MultiInsn {
    public final String name;
    protected final SubStatic64Insn insn;
    public int local = 0;

    public MethodStartInsn(String name) {
        this.name = name;
        instructions.add(new ByteArrayInsn(0x55, 0x48, 0x89, 0xE5));
        instructions.add(insn = new SubStatic64Insn(Register64.rsp, local) {
            @Override
            protected boolean small() {
                return false;
            }
        });
    }

    @Override
    public void write(int before, ASMOutputStream out) throws IOException {
        insn.value = local;
        super.write(before, out);
    }
}
