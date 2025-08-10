package net.typho.jpp.io;

import net.typho.jpp.Project;
import net.typho.jpp.parsing.ByteArrayParsingStream;
import net.typho.jpp.parsing.ParsingStream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public interface FileLoader {
    Project project();

    InputStream open(ResourceLocation location) throws IOException;

    default ParsingStream openSyntax(ResourceLocation location) throws IOException {
        try (InputStream in = open(location)) {
            return new ByteArrayParsingStream(in.readAllBytes(), project());
        }
    }

    static FileLoader of(ResourceFolder folder, Project project) {
        return new FileLoader() {
            @Override
            public Project project() {
                return project;
            }

            @Override
            public InputStream open(ResourceLocation location) throws IOException {
                return Files.newInputStream(folder.append(location).path());
            }
        };
    }
}
