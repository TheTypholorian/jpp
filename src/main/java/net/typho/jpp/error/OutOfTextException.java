package net.typho.jpp.error;

import net.typho.jpp.Project;

public class OutOfTextException extends RuntimeException {
    public OutOfTextException(Project project) {
        this(project, "Ran out of text in file while parsing code");
    }

    public OutOfTextException(Project project, String message) {
        super(message, null, !project.isDebug(), project.isDebug());
    }
}
