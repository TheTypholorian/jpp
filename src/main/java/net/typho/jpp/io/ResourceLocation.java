package net.typho.jpp.io;

import java.nio.file.Path;
import java.util.Arrays;

/**
 * Represents a localized pointer to a resource file
 */
public interface ResourceLocation extends ResourceFolder {
    String file();

    default Path path() {
        String first = folder()[0];
        String[] next = new String[folder().length];
        System.arraycopy(folder(), 1, next, 0, next.length - 1);
        next[next.length - 1] = file();
        return Path.of(first, next);
    }

    default ResourceLocation append(String path) {
        String file = file();
        String[] folder = new String[folder().length + 1];
        System.arraycopy(folder(), 0, folder, 0, folder.length - 1);
        folder[folder.length - 1] = path;

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

    default ResourceLocation withFile(String file) {
        String[] folder = Arrays.copyOf(folder(), folder().length);

        return new ResourceLocation() {
            @Override
            public String[] folder() {
                return folder;
            }

            @Override
            public String file() {
                return file;
            }
        };
    }

    static ResourceLocation of(String... tokens) {
        String file = tokens[tokens.length - 1];
        String[] folder = new String[tokens.length - 1];
        System.arraycopy(tokens, 0, folder, 0, folder.length);

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
}
