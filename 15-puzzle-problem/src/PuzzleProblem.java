import java.util.Scanner;
import java.util.Stack;

public class PuzzleProblem {
    public static void main(String[] args) {
        //System.out.println("Give initial state :");
        Scanner scanner = new Scanner(System.in);
        int[][] init = {{0, 2, 3, 4}, {1, 5, 7, 8}, {10, 6, 11, 12}, {9, 13, 14, 15}};
        int[][] anishaSample2 = {{6, 2, 8, 11}, {3, 0, 12, 4}, {1, 5, 14, 7}, {9, 10, 13, 15}};
        int[][] anishaSample1 = {{0, 2, 3, 4}, {1, 5, 7, 8}, {10, 6, 11, 12}, {9, 13, 14, 15}};
        int[][] goal = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
        int[][] anishaSample1goal = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 13}, {12, 15, 14, 0}};
        int[][] x = {{2, 1, 4, 11}, {0, 12, 3, 5}, {6, 7, 8, 10}, {9, 14, 13, 15}};
        int[][] y = {{0,2,3,4},{1,5,7,8},{10,6,11,13},{9,12,15,14}};
        //Pair[][] initMatrix = Functions.buildInitialMatrix(y);
        //Pair[][] goalMatrix = Functions.buildGoalMatrix(goal);
        Pair[][] goalMatrix = Functions.buildGoalMatrix(goal);
        Pair[][] initMatrix = Functions.buildMatrix(y, goal);
        PuzzleMatrix puzzleMatrix = new PuzzleMatrix(initMatrix, goalMatrix);


        //System.out.println(puzzleMatrix.manhattanSum());

        //System.out.println(puzzleMatrix.countMisplaced());
        //System.out.println(Functions.isSolvable(initMatrix));
        //System.out.println(puzzleMatrix.manhattanSum());
        SearchAStar aStar = new SearchAStar(puzzleMatrix);
        Stack<PuzzleMatrix> stack = new Stack<>();
        PuzzleMatrix finalMatrix;
        if (Functions.isSolvable(puzzleMatrix.getState())) {
            finalMatrix = aStar.search("Misplaced");
            while (finalMatrix.getPrevious() != null) {
                //Functions.printState(finalMatrix.getState());
                stack.push(finalMatrix);
                finalMatrix = finalMatrix.getPrevious();
            }
            while (!stack.empty()){
                Functions.printState(stack.pop().getState());
            }

        }
        else System.out.println("not solvable");
    }
}
