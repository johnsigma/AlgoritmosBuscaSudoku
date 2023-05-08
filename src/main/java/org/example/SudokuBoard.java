package org.example;

import java.util.ArrayList;
import java.util.Random;

public class SudokuBoard implements Cloneable {
    public int[][] board;
    public int size;
    private int [] listOfN;

    public SudokuBoard(int size) {
        this.size = size;
        this.board = new int[size][size];
    }

    public void populateBoardByTxtFile(String str) {

        int auxIndex = 0;

        for(int i = 0; i < this.size; i++) {
            for(int j = 0; j < this.size; j++) {
                while(true){
                    char c = str.charAt(auxIndex);
                    auxIndex += 1;

                    if(!Character.isDigit(c))    continue;

                    if(Character.getNumericValue(c) != 0)
                        this.board[i][j] = Character.getNumericValue(c);

                    break;

                }

            }
        }

    }

    public int getCell(int row, int col) {
        return this.board[row][col];
    }

    public void setCell(int row, int col, int value) {
        boolean isValidMove = this.isValidMove(row, col, value);

        if(isValidMove){
            //System.out.println("Valor " + value + " adicionado na célula [" + row + "][" + col + "].");
            this.board[row][col] = value;
            return;
        }

        //System.out.println("Este movimento não é válido.");
    }

    public int[] drawNumberAndPosition() {
        int row = (int) Math.floor(Math.random() * this.size);
        int col = (int) Math.floor(Math.random() * this.size);
        int element = (int) Math.floor(Math.random() * (10 - 1) + 1);

        int[] positionElement = new int[3];
        positionElement[0] = row;
        positionElement[1] = col;
        positionElement[2] = element;

        return positionElement;
    }

    public void populateBoard() {
        Random generator = new Random(1);

        while(!this.isComplete()) {
            int[] positionElement = this.drawNumberAndPosition();
            int value = positionElement[2];
            int row = positionElement[0];
            int col = positionElement[1];

            if(this.board[row][col] != 0) continue;

            this.setCell(row, col, value);

        }

    }


    public boolean isValidMove(int row, int col, int value) {

        // Verifica se o valor já existe na linha, coluna ou subgrid
        for (int i = 0; i < this.size; i++) {

            if (this.board[row][i] == value || this.board[i][col] == value) {
                return false;
            }

            if(this.size > 3) {
                int subRow = (int) Math.sqrt(this.size) * (row / (int) Math.sqrt(this.size)) + i / (int) Math.sqrt(this.size);
                int subCol = (int) Math.sqrt(this.size) * (col / (int) Math.sqrt(this.size)) + i % (int) Math.sqrt(this.size);
                if (this.board[subRow][subCol] == value) {
                    return false;
                }
            }
        }
        return true;
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
        for(int i = 0; i < this.size; i++) {
            for(int j = 0; j < this.size; j++) {
                if(this.board[i][j] == 0) {
                    int[] nextEmptyCell = {i, j};

                    return nextEmptyCell;
                }
            }
        }

        return null;
    }

    public ArrayList<SudokuBoard> extendBoard() throws CloneNotSupportedException {
        int [] nextEmptyCell = this.getNextEmptyCell();

        if(nextEmptyCell == null)   return null;

        int row = nextEmptyCell[0];
        int col = nextEmptyCell[1];

        ArrayList<SudokuBoard> newSus = new ArrayList<>();

        for(int value = 1; value <= this.size; value++) {
            if(this.isValidMove(row, col, value)) {
                SudokuBoard newSu = (SudokuBoard)this.clone();
                newSu.board[row][col] = value;
//                System.out.println("\n+++++++++++++");
//                newSu.printBoard();
//                System.out.println("+++++++++++++");
                newSus.add(newSu);
            }
        }

        return newSus;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        SudokuBoard cloned = (SudokuBoard) super.clone();
        cloned.size = this.size;
        int [][] board = new int[cloned.size][cloned.size];

        for(int i = 0; i < cloned.size; i++) {
            for(int j = 0; j < cloned.size; j++) {
                board[i][j] = this.board[i][j];
            }
        }

        cloned.board = board;

        return cloned;
    }

    public boolean isComplete() {
        // Verifica se todas as células foram preenchidas
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                if (this.board[row][col] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isSolution() {
        for(int row = 0; row < this.size; row++) {
            for(int col = 0; col < this.size; col++) {
                int value = this.board[row][col];
                this.board[row][col] = 0;
                if(!this.isValidMove(row, col, value) || value == 0) {
                    System.out.println("\nRow: " + row);
                    System.out.println("Col: " + col);
                    System.out.println("Value: " + value);
                    return false;
                }

                this.board[row][col] = value;
            }
        }

        return true;
    }
}
