package net.typho.jpp;

import net.typho.jpp.lexical.Lexer;
import net.typho.jpp.lexical.StringLexer;
import net.typho.jpp.parsing.DefaultParser;
import net.typho.jpp.parsing.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JPP {
    public static void main(String[] args) throws IOException {
        Lexer lexer = new StringLexer(Files.readString(Path.of("src", "main", "java", "net", "typho", "jpp", "parsing", "DefaultParser.java")));
        Parser parser = new DefaultParser();

        parser.parse(lexer);
    }
}
