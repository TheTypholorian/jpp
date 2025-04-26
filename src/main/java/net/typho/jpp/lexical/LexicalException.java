package net.typho.jpp.lexical;

public class LexicalException extends RuntimeException {
    public LexicalException() {
    }

    public LexicalException(String message) {
        super(message);
    }

    public LexicalException(String message, Throwable cause) {
        super(message, cause);
    }

    public LexicalException(Throwable cause) {
        super(cause);
    }

    public LexicalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
