import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GamePlay game = new GamePlay();
        game.initializeBoard();
        System.out.println("\n:::::::::: LINES OF ACTION :::::::::::\n");
        System.out.println(game.printBoard());
        Scanner scanner = new Scanner(System.in);
        int choice;
        String input;
        GamePlay gameNode = GameNode.copyGame(game);

        while (true){
            System.out.println("1. Move");
            System.out.println("2. Checker Count");
            System.out.println("3. Print Board");
            System.out.println("4. Show Possible Moves");
            System.out.println("5. Copy Board");
            System.out.println("6. Move on copied board");
            System.out.println("7. Print copied board");
            System.out.println("10. Exit");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Format : (W/B):row,col:final_row,final_col");
                    input = scanner.next();
                    String playerType = input.split(":")[0];
                    Block init = game.getBoard()[Integer.parseInt(input.split(":")[1].split(",")[0])][Integer.parseInt(input.split(":")[1].split(",")[1])];
                    Block next = game.getBoard()[Integer.parseInt(input.split(":")[2].split(",")[0])][Integer.parseInt(input.split(":")[2].split(",")[1])];
                    Move move = new Move(playerType, init, next);
                    ArrayList<Move> possibleMoves = game.getPossibleMoves(init);
                    boolean canMove = game.gameMove(move, possibleMoves);
                    if (canMove) System.out.println("Move Successful");
                    else System.out.println("Invalid Move");
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
                case 4:
                    System.out.println("Format : row,col");
                    input = scanner.next();
                    row = Integer.parseInt(input.split(",")[0]);
                    col = Integer.parseInt(input.split(",")[1]);
                    possibleMoves = game.getPossibleMoves(game.getBoard()[row][col]);
                    for (Move m: possibleMoves) {
                        System.out.println(m);
                    }
                    break;
                case 5:
                    System.out.println("CLONING");
                    gameNode = GameNode.copyGame(game);
                    System.out.println(gameNode.printBoard());
                    break;
                case 6:
                    System.out.println("Format : (W/B):row,col:final_row,final_col");
                    input = scanner.next();
                    playerType = input.split(":")[0];
                    //gameNode = GameNode.copyGame(game);
                    init = gameNode.getBoard()[Integer.parseInt(input.split(":")[1].split(",")[0])][Integer.parseInt(input.split(":")[1].split(",")[1])];
                    next = gameNode.getBoard()[Integer.parseInt(input.split(":")[2].split(",")[0])][Integer.parseInt(input.split(":")[2].split(",")[1])];
                    move = new Move(playerType, init, next);
                    possibleMoves = gameNode.getPossibleMoves(init);
                    canMove = gameNode.gameMove(move, possibleMoves);
                    if (canMove) System.out.println("Move Successful");
                    else System.out.println("Invalid Move");
                    break;
                case 7:
                    //gameNode = GameNode.copyGame(game);
                    System.out.println(gameNode.printBoard());
                default:
                    break;
            }
            if (choice==10) break;
        }

    }
}
