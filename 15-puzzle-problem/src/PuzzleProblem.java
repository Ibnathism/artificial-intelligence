import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Stack;

public class PuzzleProblem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        BufferedReader br = null;
        try {
            File file = new File("input.txt");
            br = new BufferedReader(new FileReader(file));
            String st;

            int n = Integer.parseInt(br.readLine());

            for (int k = 0; k < n; k++) {
                st = br.readLine();
                int[][] initial = new int[4][4];
                String[] strings = st.split(" ");
                //System.out.println("Insert Initial State");
                int p = 0;
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        initial[i][j] = Integer.parseInt(strings[p]);
                        p++;
                    }
                }
                int[][] goal = {{1,2,3,4}, {5,6,7,8}, {9,10,11,12}, {13,14,15,0}};
                Pair[][] goalMatrix = Functions.buildGoalMatrix(goal);
                Pair[][] initMatrix = Functions.buildMatrix(initial, goal);
                PuzzleMatrix puzzleMatrix = new PuzzleMatrix(initMatrix, goalMatrix);
                SearchAStar aStar = new SearchAStar(puzzleMatrix);
                Stack<PuzzleMatrix> stack = new Stack<>();
                Stack<String> moves = new Stack<>();
                System.out.println("\nGiven ");
                for (int i = 0; i < initial.length; i++) {
                    for (int j = 0; j < initial.length; j++) {
                        System.out.print(initial[i][j]+"\t");
                    }
                    System.out.println();
                }
                int pathCount = 0;
                PuzzleMatrix finalMatrix;
                if (Functions.isSolvable(puzzleMatrix.getState())) {
                    System.out.println("Which one you want to choose?   1. Misplaced Tiles  2.Manhattan");
                    int type = scanner.nextInt();
                    //System.out.println("Search using Manhattan");
                    finalMatrix = aStar.search(type);
                    while (finalMatrix.getPrevious() != null) {
                        //Functions.printState(finalMatrix.getState());
                        stack.push(finalMatrix);
                        moves.push(finalMatrix.getMymove());
                        finalMatrix = finalMatrix.getPrevious();
                        pathCount++;

                    }
                    int step = 0;
                    while (!stack.empty()){
                        step++;
                        System.out.println("Step "+ step);
                        Functions.printState(stack.pop().getState());
                    }
                    System.out.println("Path : "+pathCount);
                    System.out.print("Moves : ");
                    while (!moves.empty()){
                        System.out.print(moves.pop());
                    }
                }
                else System.out.println("not solvable");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



        //System.out.println(puzzleMatrix.manhattanSum());



    }
}
