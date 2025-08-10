package net.typho.jpp;

import net.typho.jpp.io.FileLoader;
import net.typho.jpp.io.ResourceFolder;

public class BasicProject implements Project {
    public final FileLoader resources;
    public boolean debug = false;

    public BasicProject(FileLoader resources) {
        this.resources = resources;
    }

    public BasicProject(ResourceFolder resourcePath) {
        resources = FileLoader.of(resourcePath, this);
    }

    @Override
    public FileLoader resources() {
        return resources;
    }

    @Override
    public boolean isDebug() {
        return debug;
    }
}
