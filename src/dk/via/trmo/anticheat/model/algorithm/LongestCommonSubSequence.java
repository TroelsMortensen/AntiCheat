package dk.via.trmo.anticheat.model.algorithm;

import dk.via.trmo.anticheat.model.containers.ClassContainer;
import dk.via.trmo.anticheat.model.containers.PairResult;

public class LongestCommonSubSequence {

    public PairResult calcLongestCommonSubSequence(ClassContainer c1, ClassContainer c2) {
        TableCellContent[][] table = generateTable(c1, c2);
        String longest = constructLongest(table, c1, c2);
        PairResult pr = new PairResult(c1, c2, longest);
        return pr;
    }

    public String constructLongest(TableCellContent[][] table, ClassContainer c1, ClassContainer c2) {

        int currentX = c1.classContent.length();
        int currentY = c2.classContent.length();
        TableCellContent current = table[currentX][currentY];
        String result = "";//c1.classContent.charAt(c1.classContent.length()-1) +"";

        while(true) {

            if(current.parentX != currentX && current.parentY != currentY) { // diagonal
                char in1 = c1.classContent.charAt(currentX-1);
                char in2 = c2.classContent.charAt(currentY-1);
                result = in1 + result;
            }
            currentX = current.parentX;
            currentY = current.parentY;
            current = table[current.parentX][current.parentY];


            if(currentX == 0 || currentY == 0) break;
        }
        return result;
    }

    public TableCellContent[][] generateTable(ClassContainer c1, ClassContainer c2) {
        TableCellContent[][] table = new TableCellContent[c1.classContent.length()+1][c2.classContent.length()+1];

        for (int i = 0; i < c1.classContent.length()+1; i++) {
            table[i][0] = new TableCellContent(0, -1, -1, '-', '-');
        }
        for (int i = 0; i < c2.classContent.length()+1; i++) {
            table[0][i] = new TableCellContent(0, -1, -1,'|', '|');
        }

        for (int y = 1; y <= c2.classContent.length(); y++) {
            for (int x = 1; x <= c1.classContent.length(); x++) {
                char c1Char = c1.classContent.charAt(x - 1);
                char c2Char = c2.classContent.charAt(y - 1);
                if (c1Char == c2Char) { // diagonal
                    TableCellContent cellContent = table[x - 1][y - 1];
                    table[x][y] = new TableCellContent(cellContent.longest + 1, x-1, y-1, c1Char, c2Char);
                } else if (table[x - 1][y].longest <= table[x][y - 1].longest) { // up
                    TableCellContent cc = table[x][y - 1];
                    table[x][y] = new TableCellContent(cc.longest, x, y - 1, c1Char, c2Char);
                } else { // left
                    TableCellContent cc = table[x - 1][y];
                    table[x][y] = new TableCellContent(cc.longest, x - 1, y, c1Char, c2Char);
                }
            }
        }
        return table;
    }
}
