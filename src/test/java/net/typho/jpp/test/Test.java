package net.typho.jpp.test;

import net.typho.jpp.BasicProject;
import net.typho.jpp.io.ResourceFolder;
import net.typho.jpp.io.ResourceLocation;
import net.typho.jpp.parsing.Instruction;
import net.typho.jpp.parsing.Java;
import net.typho.jpp.parsing.ParsingStream;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        BasicProject project = new BasicProject(ResourceFolder.of("src", "test", "resources"));
        project.debug = true;

        ParsingStream in = project.resources().openSyntax(ResourceLocation.of("Parse.java"));
        Java java = new Java();

        for (Instruction insn : java.parseAll(in)) {
            if (insn == null) {
                throw project.nothingMatches();
            }

            System.out.println(insn);
        }
    }
}
