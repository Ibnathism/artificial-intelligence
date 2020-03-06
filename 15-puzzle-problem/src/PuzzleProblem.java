import java.util.Scanner;
import java.util.Stack;

public class PuzzleProblem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[][] initial = new int[4][4];
        System.out.println("Insert Initial State");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                initial[i][j] = scanner.nextInt();
            }
        }
        System.out.println("Insert Goal State");
        int[][] goal = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                goal[i][j] = scanner.nextInt();
            }
        }
        Pair[][] goalMatrix = Functions.buildGoalMatrix(goal);
        Pair[][] initMatrix = Functions.buildMatrix(initial, goal);
        PuzzleMatrix puzzleMatrix = new PuzzleMatrix(initMatrix, goalMatrix);

        SearchAStar aStar = new SearchAStar(puzzleMatrix);
        Stack<PuzzleMatrix> stack = new Stack<>();
        PuzzleMatrix finalMatrix;
        if (Functions.isSolvable(puzzleMatrix.getState())) {

            finalMatrix = aStar.search("Manhattan");
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
