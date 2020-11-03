public class GamePlay {
    private LineOfActionFactory loaFactory;
    private BoardPosition[][] board;

    public GamePlay() {
        loaFactory = new LineOfActionFactory();
        board = new BoardPosition[Constants.DIMENSION][Constants.DIMENSION];
        initializeBoard();
        initializeLineOfAction();
    }

    public BoardPosition[][] getBoard() {
        return board;
    }

    private void initializeBoard() {
        for (int i = 0; i < Constants.DIMENSION; i++) {
            for (int j = 0; j < Constants.DIMENSION; j++) {
                board[i][j] = new BoardPosition(i,j);
            }
        }
    }

    private void initializeLineOfAction() {
        for (int i = 0; i < Constants.DIMENSION; i++) {
            for (int j = 0; j < Constants.DIMENSION; j++) {
                board[i][j].setHorizontal(loaFactory.getLoa(TypesOfLoa.HORIZONTAL, board[i][j], this));
                board[i][j].setVertical(loaFactory.getLoa(TypesOfLoa.VERTICAL, board[i][j], this));
                board[i][j].setLeadingDiagonal(loaFactory.getLoa(TypesOfLoa.LEADING_DIAGONAL, board[i][j], this));
                board[i][j].setCounterDiagonal(loaFactory.getLoa(TypesOfLoa.COUNTER_DIAGONAL, board[i][j], this));
            }
        }
    }
}
