package net.typho.jpp.assembly;

public class InlineData extends MultiInsn {
    public InlineData(byte[] data) {
        this(Register64.rsi, data);
    }

    public InlineData(Register64 reg, byte[] data) {
        JumpInsn jump = new JumpInsn(data.length);

        instructions.add(new LEA64Insn(reg, null, null, 1, jump.bytes()));
        instructions.add(jump);
        instructions.add(new ByteArrayInsn(data));
    }
}
