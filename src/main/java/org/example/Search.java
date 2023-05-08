package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Search {

    int numberOfSteps = 0;
    Search() {

    }

    public SudokuBoard iterativeDepthSearch(int limit, SudokuBoard su) throws CloneNotSupportedException {

        if(su.isSolution()) return su;

        SudokuBoard result = null;

        for(int depth = 1; depth <= limit; depth++) {

            System.out.println("\nIteração: " + depth);

            result = this.depthLimitedSearch(depth, su);

//            System.out.println("\n----------");
//            result.printBoard();
//            System.out.println("----------");

            //System.out.println(depth);

            if(result.isSolution()) {
                System.out.println("Solução encontrada com profundidade " + depth);
                return result;
            }

        }

        return result;
    }

    public SudokuBoard depthLimitedSearch(int depth, SudokuBoard su) throws CloneNotSupportedException {

        SudokuBoard selected_su = null;

        int numberOfSteps = 0;

//        Stack<Cell> stack = new Stack<>();
//        stack.push(new Cell(0,0));

        Stack<SudokuBoard> stack = new Stack<>();
        stack.push(su);

        ArrayList<SudokuBoard> checkedSus = new ArrayList<>();

        while(numberOfSteps < depth) {

            numberOfSteps++;

            if(stack.isEmpty()) {
                System.out.println("Solução não encontrada!");
                break;
            }

            selected_su = stack.pop();

            checkedSus.add(selected_su);

            if(selected_su.isSolution()) {
                System.out.println("Solução encontrada!");
                selected_su.printBoard();
                return selected_su;
            }

            ArrayList<SudokuBoard> new_sus = selected_su.extendBoard();

            if(new_sus == null) return selected_su;

            if(new_sus.size() > 0) {
                for(int i = new_sus.size() - 1; i >= 0; i--) {

//                    System.out.println("\nStack");
//                    for(int n = 0; n < stack.size(); n++) {
//                        stack.get(n).printBoard();
//                    }
//
//                    System.out.println("\nChecked");
//                    for(int n = 0; n < checkedSus.size(); n++) {
//                        checkedSus.get(n).printBoard();
//                    }


//                    ArrayList<SudokuBoard> arrayStack = new ArrayList(stack);
//
//                    if(!this.boardIsContainedInList(new_sus.get(i).board, arrayStack) && !this.boardIsContainedInList(new_sus.get(i).board, checkedSus)) {
//                        System.out.println("\n\n+++++++++++++");
//                        new_sus.get(i).printBoard();
//                        System.out.println("\n+++++++++++++");
//                        stack.push(new_sus.get(i));
//                    }

                    if(!stack.contains(new_sus.get(i)) && !checkedSus.contains(new_sus.get(i))) {
                        System.out.println("\n\n+++++++++++++");
                        new_sus.get(i).printBoard();
                        System.out.println("\n+++++++++++++");
                        stack.push(new_sus.get(i));
                    }
                }
            }

//            Cell cell = stack.pop();
//            int row = cell.row;
//            int col = cell.col;
//
//            if(su.board[row][col] == 0) {
//
//                for(int value = previousSu.board[row][col] + 1; value <= su.size; value++) {
//                    if(su.isValidMove(row, col, value)) {
//                        su.board[row][col] = value;
//
//                        if(depth == 1 || su.isComplete()) {
//                            //return true;
//                        } else {
//                            Cell nextCell = this.getNextEmptyCell(row, col, su.board, su.size);
//                            stack.push(nextCell);
//                            break;
//                        }
//                    }
//                }
//            } else {
//                Cell nextCell = this.getNextEmptyCell(row, col, su.board, su.size);
//                stack.push(nextCell);
//            }
        }

        return selected_su;

    }

    private boolean boardIsContainedInList(int [][] board, ArrayList<SudokuBoard> boardsList) {
        for(int c = 0; c < boardsList.size(); c++) {
            if(Arrays.equals(board, boardsList.get(c).board)){
                return true;
            }
        }

        return false;
    }

    private Cell getNextEmptyCell(int row, int col, int[][] board, int size) {
        for(int i = row; i < size; i++) {
            for(int j = (i == row) ? col + 1 : 0; j < size; j++) {
                if(board[i][j] == 0) {
                    Cell nextCell = new Cell(i, j);
                    return nextCell;
                }
            }
        }

        return null;
    }

//    public void IterativeDepthSearch(int limit, SudokuBoard board) {
//
//        boolean solutionFound = false;
//
//        for(int n = 0; n < limit; n++) {
//
//            if(solutionFound)   break;
//
//            while(true) {
//                this.numberOfSteps += 1;
//
//                if(this.frontierIsEmpty()) {
//                    System.out.println("No solution found after " + this.numberOfSteps + " steps.");
//                    break;
//                }
//
//                Node selectedNode = this.removeFromFrontier();
//
//                if(selectedNode.isTheSolution()) {
//                    System.out.println("Solution found after " + this.numberOfSteps + " steps.");
//                    System.out.println(selectedNode);
//                    solutionFound = true;
//                    break;
//                }
//
//                newNodes = selectedNode.extendNode();
//
//                if(newNodes.length > 0) {
//                    for(int i = 0; i < newNodes.length; i++) {
//                        if(!this.frontier.contains(newNodes[i]) && !this.checkedNodes.contains(newNodes[i])) {
//                            this.insertToFrontier(newNodes[i]);
//                        }
//                    }
//                }
//
//            }
//        }
//    }
}
