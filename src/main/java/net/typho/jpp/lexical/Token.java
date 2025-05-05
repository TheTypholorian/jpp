package net.typho.jpp.lexical;

public record Token(String text, int row, int col, int width) {
    public Token(String text, int row, int col, int width) {
        this.text = text;
        this.row = row;
        this.col = col - width;
        this.width = width;
    }
}
