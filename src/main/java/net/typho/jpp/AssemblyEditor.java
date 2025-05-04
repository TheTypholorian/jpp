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
        String code = """
                48 c7 c1 0f 00 00 00
                48 c7 c0 17 00 00 00
                48 f7 e9
                89 c7
                b8 3c 00 00 00
                0f 05
                """;
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();

        String[] lines = code.split("\n");

        for (String line : lines) {
            int index = line.indexOf('/');

            if (index != -1) {
                line = line.substring(0, index);
            }

            line = line.replaceAll(" ", "");

            for (int i = 0; i < line.length(); i += 2) {
                bOut.write(Integer.parseInt(String.valueOf(line.charAt(i)) + line.charAt(i + 1), 16));
            }
        }
         */

        /*
        asm.add(new StaticTo32RegisterInsn(0x50, Register32.edi));
        asm.add(new StaticTo32RegisterInsn(0x25, Register32.ecx));

        asm.add(new Add32Insn(Register32.edi, Register32.ecx));

        asm.add(new StaticTo32RegisterInsn(SyscallInsn.EXIT, Register32.eax));
        asm.add(new SyscallInsn());
         */

        Assembler asm = new Assembler();

        byte[] text = "Hello from java, your one and only friend!".getBytes();

        asm.add(new LEA64Insn(Register64.rsi, null, null, 1, 0) {
            @Override
            public void write(ASMOutputStream out) throws IOException {
                disp = asm.codeBytes() - 0x31;
                super.write(out);
            }

            @Override
            protected boolean hasDisp() {
                return true;
            }
        });
        asm.add(new StaticTo32RegisterInsn(text.length, Register32.edx));
        asm.add(new StaticTo32RegisterInsn(1, Register32.edi));
        asm.add(new StaticTo32RegisterInsn(SysCallInsn.WRITE, Register32.eax));
        asm.add(new SysCallInsn());

        asm.add(new StaticTo32RegisterInsn(69, Register32.edi));
        asm.add(new StaticTo32RegisterInsn(420, Register32.esi));
        asm.add(new Mul32Insn(Register32.edi, Register32.esi));

        asm.add(new StaticTo32RegisterInsn(SysCallInsn.EXIT, Register32.eax));
        asm.add(new SysCallInsn());

        asm.add(new ByteArrayInsn(text));

        byte[] b = asm.write();

        try (FileOutputStream out = new FileOutputStream("test.bin")) {
            byte[] header = Files.readAllBytes(Path.of("header.bin"));

            writeInt(header.length + b.length, header, 0x60);
            writeInt(header.length + b.length, header, 0x68);

            out.write(header);
            out.write(b);
        }
    }
}
