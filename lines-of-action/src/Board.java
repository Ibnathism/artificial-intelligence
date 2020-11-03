public class Board {
    private int dimension;
    private BoardPosition[][] gameBoard;

    public Board(int dimension) {
        this.dimension = dimension;
        gameBoard = new BoardPosition[dimension][dimension];
    }
}
