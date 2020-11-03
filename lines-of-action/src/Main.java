import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GamePlay game = new GamePlay();
        System.out.println("\n:::::::::: LINES OF ACTION :::::::::::\n");
        System.out.println(game.printBoard());
        Scanner scanner = new Scanner(System.in);
        int choice;
        String input;

        while (true){
            System.out.println("1. Move");
            System.out.println("2. Checker Count");
            System.out.println("3. Print Board");
            System.out.println("4. Exit");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Format : (W/B):row,col:final_row,final_col");
                    input = scanner.next();
                    String playerType = input.split(":")[0];
                    BoardPosition init = game.getBoard()[Integer.parseInt(input.split(":")[1].split(",")[0])][Integer.parseInt(input.split(":")[1].split(",")[1])];
                    BoardPosition next = game.getBoard()[Integer.parseInt(input.split(":")[2].split(",")[0])][Integer.parseInt(input.split(":")[2].split(",")[1])];
                    Move move = new Move(playerType, init, next);
                    game.gameMove(move);
                    break;
                case 2:
                    System.out.println("Format : row:col");
                    input = scanner.next();
                    int row = Integer.parseInt(input.split(":")[0]);
                    int col = Integer.parseInt(input.split(":")[1]);
                    System.out.println("Horizontal : "+ game.getBoard()[row][col].getHorizontal().checkerCount);
                    System.out.println("Vertical : "+ game.getBoard()[row][col].getVertical().checkerCount);
                    System.out.println("Leading Diagonal : "+ game.getBoard()[row][col].getLeadingDiagonal().checkerCount);
                    System.out.println("Counter Diagonal : "+ game.getBoard()[row][col].getCounterDiagonal().checkerCount);
                    break;
                case 3:
                    System.out.println(game.printBoard());
                    break;
                default:
                    break;
            }
            if (choice==4) break;
        }

    }
}
