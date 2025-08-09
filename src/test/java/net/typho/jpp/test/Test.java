package net.typho.jpp.test;

import net.typho.jpp.BasicProject;
import net.typho.jpp.io.ResourceFolder;
import net.typho.jpp.io.ResourceLocation;
import net.typho.jpp.parsing.IfStatement;
import net.typho.jpp.parsing.Instruction;
import net.typho.jpp.parsing.ParsingStream;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        BasicProject project = new BasicProject(ResourceFolder.of("src", "test", "resources"));

        ParsingStream in = project.resources().openSyntax(ResourceLocation.of("Parse.java"));
        Instruction.parse(new IfStatement(), in);
        Instruction.parse(new IfStatement(), in);
    }
}
