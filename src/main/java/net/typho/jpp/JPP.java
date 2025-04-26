package net.typho.jpp;

import net.typho.jpp.lexical.Lexer;
import net.typho.jpp.lexical.StringLexer;
import net.typho.jpp.parsing.DefaultParser;
import net.typho.jpp.parsing.Parser;
import net.typho.jpp.parsing.ParsingException;

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
            if (!p.endsWith("JPP.java")) {
                try {
                    System.out.println("Parsing " + p);
                    Lexer lexer = new StringLexer(p);
                    Parser parser = new DefaultParser();

                    parser.parse(lexer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ParsingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        parse(Path.of("src", "main", "java", "net", "typho", "jpp"));
    }
}
