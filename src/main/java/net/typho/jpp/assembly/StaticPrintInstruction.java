package net.typho.jpp.assembly;

import java.nio.charset.StandardCharsets;

public class StaticPrintInstruction extends MultiInsn {
    public StaticPrintInstruction(String text) {
        this(text.getBytes(StandardCharsets.UTF_8));
    }

    public StaticPrintInstruction(byte[] text) {
        JumpInsn jump = new JumpInsn(text.length);

        instructions.add(new LEA64Insn(Register64.rsi, null, null, 1, jump.bytes()));
        instructions.add(jump);
        instructions.add(new ByteArrayInsn(text));
        instructions.add(new StaticTo32RegisterInsn(text.length, Register32.edx));
        instructions.add(new StaticTo32RegisterInsn(1, Register32.edi));
        instructions.add(new StaticTo32RegisterInsn(SysCallInsn.WRITE, Register32.eax));
        instructions.add(new SysCallInsn());
    }
}
