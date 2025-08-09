package net.typho.jpp;

import net.typho.jpp.io.ResourceFolder;
import net.typho.jpp.io.FileLoader;

public class BasicProject implements Project {
    public final FileLoader resources;

    public BasicProject(FileLoader resources) {
        this.resources = resources;
    }

    public BasicProject(ResourceFolder resourcePath) {
        this(FileLoader.of(resourcePath));
    }

    @Override
    public FileLoader resources() {
        return resources;
    }
}
