package net.typho.jpp;

import net.typho.jpp.lexical.Lexer;
import net.typho.jpp.lexical.StringLexer;
import net.typho.jpp.parsing.DefaultParser;
import net.typho.jpp.parsing.Parser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class JPP {
    public static void parse(Path p) throws IOException {
        if (Files.isDirectory(p)) {
            try (Stream<Path> s = Files.list(p)) {
                s.forEach(p1 -> {
                    try {
                        parse(p1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } else {
            if (p.toString().endsWith(".java") && p.toString().contains("\\src\\main\\java\\")) {
                try {
                    System.out.println("Parsing " + p);
                    Lexer lexer = new StringLexer(p);
                    Parser parser = new DefaultParser();

                    parser.parse(lexer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void writeInt(int value, byte[] output, int offset) {
        output[offset] = (byte)(value & 0xFF);
        output[offset + 1] = (byte)((value >> 8) & 0xFF);
        output[offset + 2] = (byte)((value >> 16) & 0xFF);
        output[offset + 3] = (byte)((value >> 24) & 0xFF);
    }

    public static void main(String[] args) {
        //parse(Path.of("src", "main", "java", "net", "typho", "jpp"));
        //parse(new File("D:\\_code").toPath());

        try {
            Path p = Path.of("test.jpp");

            System.out.println("Parsing " + p);
            Lexer lexer = new StringLexer(p);
            DefaultParser parser = new DefaultParser();
            parser.current = parser.asm;
            parser.parse(lexer);

            byte[] b = parser.asm.write(), header = Files.readAllBytes(Path.of("header.bin"));

            try (FileOutputStream out = new FileOutputStream("test2.bin")) {
                writeInt(header.length + b.length, header, 0x60);
                writeInt(header.length + b.length, header, 0x68);

                out.write(header);
                out.write(b);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
