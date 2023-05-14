package org.example;

import java.io.IOException;
import java.util.Objects;

public class Main {


    public static void main(String[] args) throws IOException, CloneNotSupportedException {

        String fileName = "sudoku-9x9.txt";

        ClassLoader classLoader = Main.class.getClassLoader();

        String path = Objects.requireNonNull(classLoader.getResource(fileName)).getPath();

        Tuple tuple = FileHandler.readFile(path);
        Type sudokuBoardType = Type.SIMPLE;

        String strTable = tuple.strTable;
        int n = tuple.numberOfLines;


        SudokuBoard sudokuBoard = new SudokuBoard(n, sudokuBoardType);
        sudokuBoard.populateBoardByTxtFile(strTable);
        System.out.println("Tipo de tabuleiro: "+ sudokuBoardType);
        System.out.println("-----------Tabuleiro inicial------------");
        sudokuBoard.printBoard();

        // System.out.println(su.isSolution());

        Search search = new Search();

        SudokuBoard suResult;

        long start = System.currentTimeMillis();

        //System.out.println("\nGreedy 1:");
        //suResult = search.greedySearch(sudokuBoard);
        //System.out.println("\nGreedy 2:");
        //suResult = search.greedySearch2(sudokuBoard);
        //suResult = search.iterativeDepthSearch(10000, su);
        //System.out.println("\nBusca Profundidade Iterativa:");
        //suResult = search.depthLimitedSearch(10000, sudokuBoard);
        //suResult = search.iterativeDepthSearch(100000, sudokuBoard);
        //System.out.println("\nTempera simulada: ");
        //suResult = search.simulatedAnnealing(sudokuBoard);

        SudokuBoard sudokuBoard1 = new SudokuBoard(6, sudokuBoardType);
        sudokuBoard1.initializeBoard();
        sudokuBoard1.printBoard();
        search.simulatedAnnealing(sudokuBoard1);

        long finish = System.currentTimeMillis();

        long timeElapsed = finish - start;

        System.out.println("\n\n\n\n-----------Tabuleiro Final------------");
        sudokuBoard1.printBoard();
        System.out.println("\nTempo execução: "+ (timeElapsed/ 1000d) +" segundos");

    }
}