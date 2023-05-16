package org.example;

import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        FileHandler fh = new FileHandler();
        String path = "C:\\Users\\johnv\\IdeaProjects\\Trabalho1IA\\src\\main\\resources\\su3.txt";
        Tuple tuple = fh.readFile(path);

        String strTable = tuple.strTable;
        int n = tuple.numberOfLines;

        SudokuBoard su = new SudokuBoard(n);
        su.populateBoardByTxtFile(strTable);
        System.out.println("-----------Tabuleiro inicial------------");
        su.printBoard();

        // System.out.println(su.isSolution());

        Search search = new Search();

        SudokuBoard suResult = null;

        System.out.println("\nGreedy 1:");
        suResult = search.greedySearch(su);
        suResult.printBoard();
        System.out.println("\nGreedy 2:");
        suResult = search.greedySearch2(su);
        //suResult = search.iterativeDepthSearch(10000, su);
        System.out.println("\nProfundidade:");
        suResult = search.depthLimitedSearch(10000, su);

        System.out.println("\nA*:");
        suResult = search.aStarSearch(su);
        suResult.printBoard();

        // System.out.println("\n\n\n\n-----------Tabuleiro Final------------");
        // suResult.printBoard();

    }
}