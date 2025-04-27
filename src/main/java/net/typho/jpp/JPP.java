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

    public static void main(String[] args) throws IOException {
        //parse(Path.of("src", "main", "java", "net", "typho", "jpp"));
        //parse(new File("D:\\_code").toPath());

        try (FileOutputStream out = new FileOutputStream("header.bin")) {
            String header = "7F454C4602010100000000000000000002003E00010000008000400000000000400000000000000000000000000000000000000000000000400038000100400000000000000100000005000000800000000000000080004000000000008000400000000000380000000000000038000000000000000000000010000000000000";

            for (int i = 0; i < header.length(); i += 2) {
                out.write(Byte.parseByte(String.valueOf(header.charAt(i)) + header.charAt(i + 1), 16));
            }
        }
    }
}
