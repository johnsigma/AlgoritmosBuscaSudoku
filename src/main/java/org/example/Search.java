package org.example;

import java.util.*;

public class Search {

    private static final int MAX_MOVES = 10000;
    private static final double INITIAL_TEMPERATURE = 1.0;
    private static final double COOLING_FACTOR = 0.95;
    private static final double FINAL_TEMPERATURE = 0.0001;

    Search() {

    }

    public SudokuBoard iterativeDepthSearch(int limit, SudokuBoard su) throws CloneNotSupportedException {

        if (su.isSolution())
            return su;

        SudokuBoard result = null;

        for (int depth = 1; depth <= limit; depth++) {

            System.out.println("\nIteração: " + depth);

            result = this.depthLimitedSearch(depth, su);

            if (result.isSolution()) {
                System.out.println("Solução encontrada com profundidade " + depth);
                return result;
            }

        }

        return result;
    }

    public void setHeuristics(ArrayList<SudokuBoard> sus) {
        for (SudokuBoard sudokuBoard : sus) {
            sudokuBoard.setHeuristic();
        }
    }

    public void setHeuristics2(ArrayList<SudokuBoard> sus) {

        for (SudokuBoard su : sus) {
            int heuristic = 0;

            for (int row = 0; row < su.size; row++) {
                for (int col = 0; col < su.size; col++) {
                    heuristic += su.getPossibilitiesCount(row, col);
                }
            }

            su.setHeuristicCost(heuristic);
        }

    }

    private void addInOrderToList(ArrayList<SudokuBoard> list, SudokuBoard su) {

        boolean isAdded = false;

        for (int i = 0; i < list.size(); i++) {
            if (su.getHeuristicCost() < list.get(i).getHeuristicCost()) {
                list.add(i, su);
                isAdded = true;
                break;
            }
        }

        if (!isAdded) {
            list.add(su);
        }
    }

    public SudokuBoard greedySearch(SudokuBoard su) throws CloneNotSupportedException {
        SudokuBoard selected_su = null;
        su.setHeuristic();

        int numberOfSteps = 0;

        ArrayList<SudokuBoard> stack = new ArrayList<>();
        stack.add(su);

        ArrayList<SudokuBoard> checkedSus = new ArrayList<>();

        ArrayList<SudokuBoard> exploredSus = new ArrayList<>();

        while (true) {

            numberOfSteps++;

            // System.out.println("Profundidade: " + numberOfSteps);

            // System.out.println("\nHeurísticas:");

            // for (SudokuBoard suAux : stack) {
            // System.out.println(suAux.getHeuristicCost());
            // }

            // System.out.println("\nEstados:");

            // for (SudokuBoard suAux : stack) {
            // System.out.println("\n+++++++++++++");
            // suAux.printBoard();
            // System.out.println("\n+++++++++++++");
            // }

            if (stack.size() == 0) {
                System.out.println("Solução não encontrada!");
                break;
            }

            selected_su = stack.get(0);

            stack.remove(0);

            checkedSus.add(selected_su);

            if (selected_su.isSolution()) {
                System.out.println("Solução encontrada na profundidade: " + numberOfSteps + ". Explorando "
                        + exploredSus.size() + " nós.");
                // for (SudokuBoard suAux : exploredSus) {
                // suAux.printBoard();
                // System.out.println("\n");
                // }
                return selected_su;
            }

            ArrayList<SudokuBoard> new_sus = selected_su.extendBoard();

            if (new_sus == null)
                continue;

            if (new_sus.size() > 0) {

                exploredSus.add(selected_su);

                this.setHeuristics2(new_sus);

                Collections.sort(new_sus, Comparator.comparing(SudokuBoard::getHeuristicCost));

                for (int i = 0; i < new_sus.size(); i++) {
                    if (!stack.contains(new_sus.get(i)) && !checkedSus.contains(new_sus.get(i))) {
                        this.addInOrderToList(stack, new_sus.get(i));
                    }
                }
            }
        }

        return selected_su;

    }

    public SudokuBoard greedySearch2(SudokuBoard su) throws CloneNotSupportedException {
        SudokuBoard selected_su = null;
        su.setHeuristic();

        int numberOfSteps = 0;

        ArrayList<SudokuBoard> stack = new ArrayList<>();
        stack.add(su);

        ArrayList<SudokuBoard> checkedSus = new ArrayList<>();

        ArrayList<SudokuBoard> exploredSus = new ArrayList<>();

        while (true) {

            numberOfSteps++;

            // System.out.println("Profundidade: " + numberOfSteps);

            // System.out.println("\nHeurísticas:");

            // for (SudokuBoard suAux : stack) {
            // System.out.println(suAux.getHeuristicCost());
            // }

            // System.out.println("\nEstados:");

            // for (SudokuBoard suAux : stack) {
            // System.out.println("\n+++++++++++++");
            // suAux.printBoard();
            // System.out.println("\n+++++++++++++");
            // }

            if (stack.size() == 0) {
                System.out.println("Solução não encontrada!");
                break;
            }

            selected_su = stack.get(0);

            stack.remove(0);

            checkedSus.add(selected_su);

            if (selected_su.isSolution()) {
                System.out.println("Solução encontrada na profundidade: " + numberOfSteps + ". Explorando "
                        + exploredSus.size() + " nós.");
                // for (SudokuBoard suAux : exploredSus) {
                // suAux.printBoard();
                // System.out.println("\n");
                // }
                return selected_su;
            }

            ArrayList<SudokuBoard> new_sus = selected_su.extendBoard();

            if (new_sus == null)
                continue;

            if (new_sus.size() > 0) {

                exploredSus.add(selected_su);

                this.setHeuristics(new_sus);

                Collections.sort(new_sus, Comparator.comparing(SudokuBoard::getHeuristicCost));

                for (SudokuBoard newSus : new_sus) {
                    if (!stack.contains(newSus) && !checkedSus.contains(newSus)) {
                        this.addInOrderToList(stack, newSus);
                    }
                }
            }
        }

        return selected_su;

    }

    public SudokuBoard depthLimitedSearch(int depth, SudokuBoard su) throws CloneNotSupportedException {

        SudokuBoard selectedSudokuBoard = null;

        int numberOfSteps = 0;

        Stack<SudokuBoard> stack = new Stack<>();
        stack.push(su);

        ArrayList<SudokuBoard> visitedSudokuBoardNode = new ArrayList<>();
        ArrayList<SudokuBoard> exploredSudokuBoardNodes = new ArrayList<>();

        while (numberOfSteps < depth) {

            numberOfSteps++;

            if (stack.isEmpty()) {
                System.out.println("Solução não encontrada!");
                break;
            }

            selectedSudokuBoard = stack.pop();

            visitedSudokuBoardNode.add(selectedSudokuBoard);

            if (selectedSudokuBoard.isSolution()) {
                System.out.println("Solução encontrada na profundidade: " + numberOfSteps + ". Explorando "
                        + exploredSudokuBoardNodes.size() + " nós.");
                return selectedSudokuBoard;
            }

            ArrayList<SudokuBoard> newSudokuBoardNodesExtendedFromActualNode = selectedSudokuBoard.extendBoard();

            if (newSudokuBoardNodesExtendedFromActualNode == null)
                continue;

            addExpandedNodesThatWasNotVisitedIntoStack(selectedSudokuBoard, stack, visitedSudokuBoardNode, exploredSudokuBoardNodes, newSudokuBoardNodesExtendedFromActualNode);
        }

        return selectedSudokuBoard;

    }

    private static void addExpandedNodesThatWasNotVisitedIntoStack(SudokuBoard selectedSudokuBoard, Stack<SudokuBoard> stack, ArrayList<SudokuBoard> visitedSudokuBoardNode, ArrayList<SudokuBoard> exploredSudokuBoardNodes, ArrayList<SudokuBoard> newSudokuBoardNodesExtendedFromActualNode) {
        if (newSudokuBoardNodesExtendedFromActualNode.size() > 0) {
            exploredSudokuBoardNodes.add(selectedSudokuBoard);
            for (int i = newSudokuBoardNodesExtendedFromActualNode.size() - 1; i >= 0; i--) {
                if (!stack.contains(newSudokuBoardNodesExtendedFromActualNode.get(i)) && !visitedSudokuBoardNode.contains(newSudokuBoardNodesExtendedFromActualNode.get(i))) {
                    stack.push(newSudokuBoardNodesExtendedFromActualNode.get(i));
                }
            }
        }
    }


    public SudokuBoard simulatedAnnealing(SudokuBoard sudokuBoard) {
        int currentCost = sudokuBoard.calculateCostOfRepeatedNumbersInRowColumnOrSubGrid(sudokuBoard.board);
        int bestCost = currentCost;
        double temperature = INITIAL_TEMPERATURE;

        while (temperature > FINAL_TEMPERATURE) {
            for (int i = 0; i < MAX_MOVES; i++) {
                int[][] newBoard = sudokuBoard.perturbBoard();
                int newCost = sudokuBoard.calculateCostOfRepeatedNumbersInRowColumnOrSubGrid(newBoard);
                if (sudokuBoard.acceptPerturbedSolution(newCost, currentCost, temperature)) {
                    sudokuBoard.board = newBoard;
                    currentCost = newCost;
                    if (currentCost < bestCost) {
                        bestCost = currentCost;
                    }
                }
            }
            temperature *= COOLING_FACTOR;
        }
        return sudokuBoard;
    }

    private static final int MAX_ITERATIONS = 10000;
    public SudokuBoard hillClimbing(SudokuBoard sudokuBoard) {
        //putIntoEmptyCellsRandomNumbers(board);
        Random rand = new Random();
        int currentCost = sudokuBoard.calculateCostOfRepeatedNumbersInRowColumnOrSubGrid(sudokuBoard.board);
        int bestCost = currentCost;
        int quantidadeTentativasMovimentosLaterais = 0;
        int MAX_MOVES_WITHOUT_IMPROVEMENT = 100;
        boolean foundBetter = false;
        int maxIterations = 0;

        int row = 0, column = 0;

        while (!sudokuBoard.isSolution() || maxIterations < MAX_ITERATIONS) {

            for (int i = 0; i < sudokuBoard.board.length; i++) {
                for (int j = 0; j < sudokuBoard.board.length; j++) {
                    if(sudokuBoard.board[i][j] == 0 ) {
                        row = i;
                        column = j;
                        foundBetter = false;
                        SudokuBoard newPositionToTry = new SudokuBoard(sudokuBoard.board.length, sudokuBoard.getSudokuBoardType());
                        newPositionToTry.board = sudokuBoard.board;
                        newPositionToTry.size = sudokuBoard.size;
                        ModifiedBoard modifiedBoard = newPositionToTry.perturbBoardForHillClimbing(row, column);

                        int custoVizinho = newPositionToTry.calculateCostOfRepeatedNumbersInRowColumnOrSubGridOrEmptyCells(modifiedBoard.newBoard);
                        int custoBoardOriginal = sudokuBoard.calculateCostOfRepeatedNumbersInRowColumnOrSubGridOrEmptyCells(sudokuBoard.board);

                        if (custoVizinho < custoBoardOriginal) {
                            sudokuBoard.board = modifiedBoard.newBoard;
                            custoBoardOriginal = custoVizinho;
                            foundBetter = true;
                        }
                    }


                    if(!foundBetter) {
                        int newScore = -1;
                        while (true)  {
                            SudokuBoard newBoardCopied = new SudokuBoard(sudokuBoard.board.length, sudokuBoard.getSudokuBoardType());
                            newBoardCopied.board = sudokuBoard.board;
                            newBoardCopied.size = sudokuBoard.size;

                            ModifiedBoard modifiedBoard = newBoardCopied.perturbBoardForHillClimbing(row, column);

                            int custoVizinho = newBoardCopied.calculateCostOfRepeatedNumbersInRowColumnOrSubGridOrEmptyCells(modifiedBoard.newBoard);
                            int custoBoardOriginal = sudokuBoard.calculateCostOfRepeatedNumbersInRowColumnOrSubGridOrEmptyCells(sudokuBoard.board);

                            quantidadeTentativasMovimentosLaterais++;
                            if (custoVizinho < custoBoardOriginal) {
                                custoBoardOriginal = custoVizinho;
                                sudokuBoard.board = newBoardCopied.board;
                                quantidadeTentativasMovimentosLaterais = 0;
                                break;
                            }

                            if(quantidadeTentativasMovimentosLaterais >= MAX_MOVES_WITHOUT_IMPROVEMENT) {

                                quantidadeTentativasMovimentosLaterais = 0;
                                break;
                            }
                        }
                    }
                }
            }

            maxIterations++;
        }
        return sudokuBoard;
    }

}
