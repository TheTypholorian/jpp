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

        byte[] text = "Hello World (from java)\n".getBytes();

        int staticAddress = 0x401000;
        int textLength = text.length;

        for (int i = 0; i < textLength; i++) {
            // Write the string bytes to the static address
            asm.add(new StaticTo32RegisterInsn(0x48, Register32.eax)); // mov eax, 0x48 (syscall number for write)
            asm.add(new StaticTo32RegisterInsn(1, Register32.edi)); // mov edi, 1 (stdout)
            asm.add(new StaticTo64RegisterInsn(staticAddress + i, Register64.rsi)); // mov rsi, [address of string + i]
            asm.add(new StaticTo32RegisterInsn(1, Register32.edx)); // mov edx, 1 (length of string)
            asm.add(new SyscallInsn());
        }

// Step 2: Perform a system exit syscall after printing
        asm.add(new StaticTo32RegisterInsn(60, Register32.eax)); // mov eax, 60 (syscall number for exit)
        asm.add(new Xor32RegisterInsn(Register32.edi)); // xor edi, edi (exit code 0)
        asm.add(new SyscallInsn()); // syscall

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
