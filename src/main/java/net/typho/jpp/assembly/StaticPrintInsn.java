package net.typho.jpp.assembly;

import java.nio.charset.StandardCharsets;

public class StaticPrintInsn extends MultiInsn {
    public StaticPrintInsn(String text) {
        this(text.getBytes(StandardCharsets.UTF_8));
    }

    public StaticPrintInsn(byte[] text) {
        instructions.add(new InlineData(text));
        instructions.add(new StaticTo32RegisterInsn(text.length, Register32.edx));
        instructions.add(new StaticTo32RegisterInsn(1, Register32.edi));
        instructions.add(new StaticTo32RegisterInsn(SysCallInsn.WRITE, Register32.eax));
        instructions.add(new SysCallInsn());
    }
}
