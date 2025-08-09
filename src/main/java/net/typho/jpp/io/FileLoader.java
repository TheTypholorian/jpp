package net.typho.jpp.io;

import net.typho.jpp.parsing.ByteArrayParsingStream;
import net.typho.jpp.parsing.ParsingStream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public interface FileLoader {
    InputStream open(ResourceLocation location) throws IOException;

    default ParsingStream openSyntax(ResourceLocation location) throws IOException {
        try (InputStream in = open(location)) {
            return new ByteArrayParsingStream(in.readAllBytes());
        }
    }

    static FileLoader of(ResourceFolder folder) {
        return location -> Files.newInputStream(folder.append(location).path());
    }
}
