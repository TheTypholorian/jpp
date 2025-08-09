package net.typho.jpp.error;

public class OutOfTextException extends RuntimeException {
    public OutOfTextException() {
        this("Ran out of text in file while parsing code");
    }

    public OutOfTextException(String message) {
        super(message, null, true, false);
    }
}
