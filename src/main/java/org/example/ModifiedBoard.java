package org.example;

public class ModifiedBoard {
    int[][] newBoard;
    int modifiedRow;
    int modifiedColumn;

    int oldValue;

    int newValue;

    public ModifiedBoard(int[][] newBoard, int modifiedRow, int modifiedColumn, int oldValue, int newValue) {
        this.newBoard = newBoard;
        this.modifiedRow = modifiedRow;
        this.modifiedColumn = modifiedColumn;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public ModifiedBoard() {
    }
}
