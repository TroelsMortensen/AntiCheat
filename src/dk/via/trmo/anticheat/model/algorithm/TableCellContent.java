package dk.via.trmo.anticheat.model.algorithm;

public class TableCellContent {
    private final char c1Char, c2Char;
    public int longest;
    public int parentX, parentY;

    public TableCellContent(int longest, int x, int y, char c1Char, char c2Char) {
        this.longest = longest;
        this.parentX = x;
        this.parentY = y;
        this.c1Char = c1Char;
        this.c2Char = c2Char;
    }
}
