package org.example;

import java.util.ArrayList;

public class SudokuBoard implements Cloneable {
    public int[][] board;
    public int size;
    private int heuristicCost;

    private final Type type;

    public SudokuBoard(int size, Type type) {
        this.size = size;
        this.type = type;
        this.board = new int[size][size];
    }

    public void populateBoardByTxtFile(String str) {

        int auxIndex = 0;

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                while (true) {
                    char c = str.charAt(auxIndex);
                    auxIndex += 1;

                    if (!Character.isDigit(c))
                        continue;

                    if (Character.getNumericValue(c) != 0)
                        this.board[i][j] = Character.getNumericValue(c);

                    break;

                }

            }
        }

    }

    public int getHeuristicCost() {
        return this.heuristicCost;
    }

    public Type getSudokuBoardType() {
        return this.type;
    }

    public void setHeuristic() {
        int emptyCells = 0;

        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                if (this.board[row][col] == 0) {
                    emptyCells += 1;
                }
            }
        }

        this.heuristicCost = emptyCells;
    }

    public void setHeuristicCost(int heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    public int getPossibilitiesCount(int row, int col) {

        if (this.board[row][col] != 0) {
            return 0; // Retorna 0 para células preenchidas
        }

        int count = 0;

        for (int num = 1; num <= this.size; num++) {
            if (isValidMove(row, col, num)) {
                count++;
            }
        }

        return count;
    }

    public boolean isValidMove(int row, int col, int value) {

        // Verifica se o valor já existe na linha, coluna ou subgrid
        for (int i = 0; i < this.size; i++) {

            if (isNumberInRow(row, value, i) || isNumberInColumn(col, value, i)) {
                return false;
            }

            if(isComplexBoard()) {
                if (isNumberInSubGrid(row, col, value, i)) return false;
            }

        }
        return true;
    }

    private boolean isComplexBoard() {
        return Type.COMPLEX.equals(getSudokuBoardType());
    }

    private boolean isNumberInColumn(int col, int value, int i) {
        return this.board[i][col] == value;
    }

    private boolean isNumberInRow(int row, int value, int i) {
        return this.board[row][i] == value;
    }

    private boolean isNumberInSubGrid(int row, int col, int value, int i) {
        if (this.size > 3) {
            int subRow = (int) Math.sqrt(this.size) * (row / (int) Math.sqrt(this.size))
                    + i / (int) Math.sqrt(this.size);
            int subCol = (int) Math.sqrt(this.size) * (col / (int) Math.sqrt(this.size))
                    + i % (int) Math.sqrt(this.size);
            return this.board[subRow][subCol] == value;
        }
        return false;
    }

    public void printBoard() {
        for (int row = 0; row < this.size; row++) {
            if (row % Math.sqrt(this.size) == 0 && row != 0) {
                System.out.print("+");
                for (int i = 0; i < this.size; i++) {
                    System.out.print("-");
                }
                System.out.println("+");
            }
            for (int col = 0; col < this.size; col++) {
                if (col % Math.sqrt(this.size) == 0 && col != 0) {
                    System.out.print("| ");
                }
                System.out.print(this.board[row][col] + " ");
            }
            System.out.println();
        }
    }

    private int[] getNextEmptyCell() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.board[i][j] == 0) {

                    return new int[]{ i, j };
                }
            }
        }

        return null;
    }

    public ArrayList<SudokuBoard> extendBoard() throws CloneNotSupportedException {
        int[] nextEmptyCell = this.getNextEmptyCell();

        if (nextEmptyCell == null)
            return null;

        int row = nextEmptyCell[0];
        int col = nextEmptyCell[1];

        ArrayList<SudokuBoard> newSus = new ArrayList<>();

        for (int value = 1; value <= this.size; value++) {
            if (this.isValidMove(row, col, value)) {
                SudokuBoard newSu = (SudokuBoard) this.clone();
                newSu.board[row][col] = value;
                newSus.add(newSu);
            }
        }

        return newSus;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        SudokuBoard cloned = (SudokuBoard) super.clone();
        cloned.size = this.size;
        int[][] board = new int[cloned.size][cloned.size];

        for (int i = 0; i < cloned.size; i++) {
            System.arraycopy(this.board[i], 0, board[i], 0, cloned.size);
        }

        cloned.board = board;

        return cloned;
    }

    public boolean isSolution() {
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                int value = this.board[row][col];
                this.board[row][col] = 0;
                if (!this.isValidMove(row, col, value) || value == 0) {
                    return false;
                }

                this.board[row][col] = value;
            }
        }

        return true;
    }
}
