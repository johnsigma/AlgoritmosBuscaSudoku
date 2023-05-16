package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class Search {

    int numberOfSteps = 0;

    Search() {

    }

    public SudokuBoard iterativeDepthSearch(int limit, SudokuBoard su) throws CloneNotSupportedException {

        if (su.isSolution())
            return su;

        SudokuBoard result = null;

        for (int depth = 1; depth <= limit; depth++) {

            System.out.println("\nIteração: " + depth);

            result = this.depthLimitedSearch(depth, su);

            // System.out.println("\n----------");
            // result.printBoard();
            // System.out.println("----------");

            // System.out.println(depth);

            if (result.isSolution()) {
                System.out.println("Solução encontrada com profundidade " + depth);
                return result;
            }

        }

        return result;
    }

    public void setHeuristics(ArrayList<SudokuBoard> sus) {

        for (int i = 0; i < sus.size(); i++) {
            sus.get(i).setHeuristic();
        }
    }

    public void setHeuristic2(SudokuBoard su) {

        int heuristic = 0;

        for (int row = 0; row < su.size; row++) {
            for (int col = 0; col < su.size; col++) {
                heuristic += su.getPossibilitiesCount(row, col);
            }
        }

        su.setHeuristicCost(heuristic);

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

    public SudokuBoard aStarSearch(SudokuBoard initialBoard) throws CloneNotSupportedException {

        int numberOfSteps = 0;

        // Inicialização do openSet e visitedNodes
        PriorityQueue<SudokuBoard> openSet = new PriorityQueue<SudokuBoard>(
                Comparator.comparingInt(SudokuBoard::getTotalCost));

        Set<SudokuBoard> visitedNodes = new HashSet<>();

        // Definir o custo inicial e heurística para o estado inicial
        initialBoard.setCostFunction();
        this.setHeuristic2(initialBoard);

        // Adicionar o estado inicial ao openSet
        openSet.add(initialBoard);

        while (!openSet.isEmpty()) {

            numberOfSteps++;

            SudokuBoard currentBoard = openSet.poll();
            currentBoard.setCostFunction();
            this.setHeuristic2(currentBoard);

            // Verificar se o estado atual é a solução
            if (currentBoard.isSolution()) {

                System.out.println("Solução encontrada na profundidade: " + numberOfSteps + ". Explorando "
                        + visitedNodes.size() + " nós.");

                return currentBoard;
            }

            // Adicionar o estado atual ao visitedNodes
            // if (!visitedNodes.contains(currentBoard))
            // visitedNodes.add(currentBoard);

            // Gerar e avaliar estados vizinhos
            List<SudokuBoard> neighbors = currentBoard.extendBoard();

            int currentCost = currentBoard.getTotalCost();

            if (neighbors == null)
                continue;

            if (neighbors.size() > 0) {

                // if (!visitedNodes.contains(currentBoard))
                // visitedNodes.add(currentBoard);

                for (SudokuBoard neighbor : neighbors) {
                    // Verificar se o estado vizinho já foi explorado
                    if (visitedNodes.contains(neighbor)) {
                        continue;
                    }

                    neighbor.setCostFunction();
                    this.setHeuristic2(neighbor);

                    // Calcular o custo para o vizinho
                    int newTotalCost = neighbor.getTotalCost();

                    if (!openSet.contains(neighbor) || newTotalCost < currentCost) {
                        openSet.add(neighbor);
                        if (!visitedNodes.contains(currentBoard))
                            visitedNodes.add(currentBoard);
                    }
                }
            }

        }

        System.out.println("Solução não encontrada!");
        return null; // Nenhuma solução encontrada
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

                for (int i = 0; i < new_sus.size(); i++) {
                    if (!stack.contains(new_sus.get(i)) && !checkedSus.contains(new_sus.get(i))) {
                        this.addInOrderToList(stack, new_sus.get(i));
                    }
                }
            }
        }

        return selected_su;

    }

    public SudokuBoard depthLimitedSearch(int depth, SudokuBoard su) throws CloneNotSupportedException {

        SudokuBoard selected_su = null;

        int numberOfSteps = 0;

        // Stack<Cell> stack = new Stack<>();
        // stack.push(new Cell(0,0));

        Stack<SudokuBoard> stack = new Stack<>();
        stack.push(su);

        ArrayList<SudokuBoard> checkedSus = new ArrayList<>();
        ArrayList<SudokuBoard> exploredSus = new ArrayList<>();

        while (numberOfSteps < depth) {

            numberOfSteps++;

            if (stack.isEmpty()) {
                System.out.println("Solução não encontrada!");
                break;
            }

            selected_su = stack.pop();

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
                for (int i = new_sus.size() - 1; i >= 0; i--) {

                    // System.out.println("\nStack");
                    // for(int n = 0; n < stack.size(); n++) {
                    // stack.get(n).printBoard();
                    // }
                    //
                    // System.out.println("\nChecked");
                    // for(int n = 0; n < checkedSus.size(); n++) {
                    // checkedSus.get(n).printBoard();
                    // }

                    // ArrayList<SudokuBoard> arrayStack = new ArrayList(stack);
                    //
                    // if(!this.boardIsContainedInList(new_sus.get(i).board, arrayStack) &&
                    // !this.boardIsContainedInList(new_sus.get(i).board, checkedSus)) {
                    // System.out.println("\n\n+++++++++++++");
                    // new_sus.get(i).printBoard();
                    // System.out.println("\n+++++++++++++");
                    // stack.push(new_sus.get(i));
                    // }

                    if (!stack.contains(new_sus.get(i)) && !checkedSus.contains(new_sus.get(i))) {
                        stack.push(new_sus.get(i));
                    }
                }
            }

            // Cell cell = stack.pop();
            // int row = cell.row;
            // int col = cell.col;
            //
            // if(su.board[row][col] == 0) {
            //
            // for(int value = previousSu.board[row][col] + 1; value <= su.size; value++) {
            // if(su.isValidMove(row, col, value)) {
            // su.board[row][col] = value;
            //
            // if(depth == 1 || su.isComplete()) {
            // //return true;
            // } else {
            // Cell nextCell = this.getNextEmptyCell(row, col, su.board, su.size);
            // stack.push(nextCell);
            // break;
            // }
            // }
            // }
            // } else {
            // Cell nextCell = this.getNextEmptyCell(row, col, su.board, su.size);
            // stack.push(nextCell);
            // }
        }

        return selected_su;

    }

    private boolean boardIsContainedInList(int[][] board, ArrayList<SudokuBoard> boardsList) {
        for (int c = 0; c < boardsList.size(); c++) {
            if (Arrays.equals(board, boardsList.get(c).board)) {
                return true;
            }
        }

        return false;
    }

    private Cell getNextEmptyCell(int row, int col, int[][] board, int size) {
        for (int i = row; i < size; i++) {
            for (int j = (i == row) ? col + 1 : 0; j < size; j++) {
                if (board[i][j] == 0) {
                    Cell nextCell = new Cell(i, j);
                    return nextCell;
                }
            }
        }

        return null;
    }

    // public void IterativeDepthSearch(int limit, SudokuBoard board) {
    //
    // boolean solutionFound = false;
    //
    // for(int n = 0; n < limit; n++) {
    //
    // if(solutionFound) break;
    //
    // while(true) {
    // this.numberOfSteps += 1;
    //
    // if(this.frontierIsEmpty()) {
    // System.out.println("No solution found after " + this.numberOfSteps + "
    // steps.");
    // break;
    // }
    //
    // Node selectedNode = this.removeFromFrontier();
    //
    // if(selectedNode.isTheSolution()) {
    // System.out.println("Solution found after " + this.numberOfSteps + " steps.");
    // System.out.println(selectedNode);
    // solutionFound = true;
    // break;
    // }
    //
    // newNodes = selectedNode.extendNode();
    //
    // if(newNodes.length > 0) {
    // for(int i = 0; i < newNodes.length; i++) {
    // if(!this.frontier.contains(newNodes[i]) &&
    // !this.checkedNodes.contains(newNodes[i])) {
    // this.insertToFrontier(newNodes[i]);
    // }
    // }
    // }
    //
    // }
    // }
    // }
}
