package net.typho.jpp.assembly;

import java.io.IOException;

public class MethodEndInsn extends MultiInsn {
    public final MethodStartInsn start;
    protected final AddStatic64Insn insn;

    public MethodEndInsn(MethodStartInsn start) {
        this.start = start;
        instructions.add(insn = new AddStatic64Insn(Register64.rsp, start.local) {
            @Override
            protected boolean small() {
                return false;
            }
        });
        instructions.add(new ByteArrayInsn(0x5D, 0xC3));
    }

    @Override
    public void write(int before, ASMOutputStream out) throws IOException {
        insn.value = start.local;
        super.write(before, out);
    }
}
