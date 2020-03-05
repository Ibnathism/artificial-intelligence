import java.util.Scanner;

public class PuzzleProblem {
    public static void main(String[] args) {
        //System.out.println("Give initial state :");
        Scanner scanner = new Scanner(System.in);
        int[][] init = {{0, 2, 3, 4}, {1, 5, 7, 8}, {10, 6, 11, 12}, {9, 13, 14, 15}};
        int[][] goal = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
        Pair[][] initMatrix = Functions.buildInitialMatrix(init);
        Pair[][] goalMatrix = Functions.buildGoalMatrix(goal);
        PuzzleMatrix puzzleMatrix = new PuzzleMatrix(initMatrix, goalMatrix);

        //System.out.println(puzzleMatrix.countMisplaced());
        //System.out.println(Functions.isSolvable(initMatrix));

    }
}
