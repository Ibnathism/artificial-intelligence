import java.util.List;

public class GamePlay {

    private static final int DIMENSION = 8;
    private static final String WHITE_TYPE = "WHITE";
    private static final String BLACK_TYPE = "BLACK";

    private static Board createBoard() {
        int dimension = GamePlay.DIMENSION;
        Board board = new Board(dimension);

        return board;
    }



    public static void main(String[] args) {


        Board board = createBoard();

        //Player whitePlayer = new Player(GamePlay.WHITE_TYPE);
        //Player blackPlayer = new Player(GamePlay.BLACK_TYPE);

    }
}
