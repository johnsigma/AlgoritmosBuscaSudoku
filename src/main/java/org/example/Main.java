package org.example;

import java.io.IOException;
import java.util.Objects;

public class Main {


    public static void main(String[] args) throws IOException, CloneNotSupportedException {

        String fileName = "su2.txt";

        ClassLoader classLoader = Main.class.getClassLoader();

        String path = Objects.requireNonNull(classLoader.getResource(fileName)).getPath();

        Tuple tuple = FileHandler.readFile(path);

        String strTable = tuple.strTable;
        int n = tuple.numberOfLines;

        Type sudokuBoardType = Type.COMPLEX;

        SudokuBoard sudokuBoard = new SudokuBoard(n, sudokuBoardType);
        sudokuBoard.populateBoardByTxtFile(strTable);
        System.out.println("Tipo de tabuleiro: "+ sudokuBoardType);
        System.out.println("-----------Tabuleiro inicial------------");
        sudokuBoard.printBoard();

        // System.out.println(su.isSolution());

        Search search = new Search();

        SudokuBoard suResult = null;

        System.out.println("\nGreedy 1:");
        suResult = search.greedySearch(sudokuBoard);
        System.out.println("\nGreedy 2:");
        suResult = search.greedySearch2(sudokuBoard);
        //suResult = search.iterativeDepthSearch(10000, su);
        System.out.println("\nProfundidade:");
        //suResult = search.depthLimitedSearch(10000, sudokuBoard);
        suResult = search.iterativeDepthSearch(10000, sudokuBoard);

        //System.out.println("\n\n\n\n-----------Tabuleiro Final------------");
        suResult.printBoard();

    }
}