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

        Assembler asm = new Assembler();

        asm.add(new StaticTo32RegisterInsn(50, Register32.edi));
        asm.add(new StaticTo32RegisterInsn(25, Register32.ecx));

        asm.add(new Add32Insn(Register32.edi, Register32.ecx));

        asm.add(new StaticTo32RegisterInsn(60, Register32.eax));
        asm.add(new SyscallInsn());

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
