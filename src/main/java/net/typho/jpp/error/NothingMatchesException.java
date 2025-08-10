package net.typho.jpp.error;

import net.typho.jpp.Project;

public class NothingMatchesException extends RuntimeException {
    public NothingMatchesException(Project project) {
        this(project, "Text doesn't match any instructions");
    }

    public NothingMatchesException(Project project, String message) {
        super(message, null, !project.isDebug(), project.isDebug());
    }
}
