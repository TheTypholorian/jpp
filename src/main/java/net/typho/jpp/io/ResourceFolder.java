package net.typho.jpp.io;

import java.net.URI;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Represents a localized directory for resources to be in
 */
public interface ResourceFolder {
    String[] folder();

    default Path path() {
        String first = folder()[0];
        String[] next = new String[folder().length - 1];
        System.arraycopy(folder(), 1, next, 0, next.length);
        return Path.of(first, next);
    }

    default ResourceFolder append(ResourceFolder path) {
        String[] append = path.folder();
        String[] folder = new String[folder().length + append.length];
        System.arraycopy(folder(), 0, folder, 0, folder().length);
        System.arraycopy(append, 0, folder, folder().length, append.length);

        return () -> folder;
    }

    default ResourceLocation append(ResourceLocation path) {
        String file = path.file();
        String[] append = path.folder();
        String[] folder = new String[folder().length + append.length];
        System.arraycopy(folder(), 0, folder, 0, folder().length);
        System.arraycopy(append, 0, folder, folder().length, append.length);

        return new ResourceLocation() {
            @Override
            public String file() {
                return file;
            }

            @Override
            public String[] folder() {
                return folder;
            }
        };
    }

    default ResourceFolder append(String path) {
        String[] folder = new String[folder().length + 1];
        System.arraycopy(folder(), 0, folder, 0, folder.length - 1);
        folder[folder.length - 1] = path;

        return () -> folder;
    }

    static ResourceFolder of(String... tokens) {
        return () -> tokens;
    }
}
