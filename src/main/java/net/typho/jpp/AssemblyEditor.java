package net.typho.jpp;

import net.typho.jpp.assembly.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AssemblyEditor {
    private static void writeInt(int value, byte[] output, int offset) {
        output[offset] = (byte)(value & 0xFF);
        output[offset + 1] = (byte)((value >> 8) & 0xFF);
        output[offset + 2] = (byte)((value >> 16) & 0xFF);
        output[offset + 3] = (byte)((value >> 24) & 0xFF);
    }

    public static void main(String[] args) throws IOException {
        /*
        // Simple exit(0x50 + 0x25)
        asm.add(new StaticTo32RegisterInsn(0x50, Register32.edi));
        asm.add(new StaticTo32RegisterInsn(0x25, Register32.ecx));

        asm.add(new Add32Insn(Register32.edi, Register32.ecx));

        asm.add(new StaticTo32RegisterInsn(SyscallInsn.EXIT, Register32.eax));
        asm.add(new SyscallInsn());
         */

        Assembler asm = new Assembler(null);
        byte[] header = Files.readAllBytes(Path.of("header.bin"));

        byte[] text1 = "Hello World\n".getBytes(), text2 = "Hello Again!\n".getBytes();

        int t1 = asm.data(text1);
        int t2 = asm.data(text2);

        asm.add(new DelayedLEA64Insn(Register64.rsi, null, null, 1, 0, asm, t1));
        asm.add(new StaticTo32RegisterInsn(text1.length, Register32.edx));
        asm.add(new StaticTo32RegisterInsn(1, Register32.edi));
        asm.add(new StaticTo32RegisterInsn(SysCallInsn.WRITE, Register32.eax));
        asm.add(new SysCallInsn());

        asm.add(new DelayedLEA64Insn(Register64.rsi, null, null, 1, 0, asm, t2));
        asm.add(new StaticTo32RegisterInsn(text2.length, Register32.edx));
        asm.add(new StaticTo32RegisterInsn(1, Register32.edi));
        asm.add(new StaticTo32RegisterInsn(SysCallInsn.WRITE, Register32.eax));
        asm.add(new SysCallInsn());

        asm.add(new StaticTo32RegisterInsn(99, Register32.edi));
        asm.add(new StaticTo32RegisterInsn(222, Register32.esi));
        asm.add(new Mul32Insn(Register32.edi, Register32.esi));

        asm.add(new StaticTo32RegisterInsn(SysCallInsn.EXIT, Register32.eax));
        asm.add(new SysCallInsn());

        byte[] b = asm.write();

        writeInt(asm.dataBytes(), header, 0x60);
        writeInt(asm.dataBytes(), header, 0x68);

        writeInt(asm.codeBytes(), header, 0x98);
        writeInt(asm.codeBytes(), header, 0xA0);

        try (FileOutputStream out = new FileOutputStream("test.bin")) {
            out.write(header);
            out.write(b);
        }
    }
}
