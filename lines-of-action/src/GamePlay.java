import java.util.ArrayList;

public class GamePlay {
    private GamePlayUtil loaFactory;
    private Block[][] board;
    private Player black;
    private Player white;

    public GamePlay() {
        loaFactory = new GamePlayUtil();
        board = new Block[Constants.DIMENSION][Constants.DIMENSION];
        black = new Player(Constants.BLACK_TYPE);
        white = new Player(Constants.WHITE_TYPE);
        initializeBoard();
        initializeLineOfAction();
        initializeWhite();
        initializeBlack();
    }

    public boolean gameMove(Move move) {
        boolean isPossible = false;
        Block prev = board[move.initPosition.getRow()][move.initPosition.getColumn()];
        Block next = board[move.finalPosition.getRow()][move.finalPosition.getColumn()];

        prev.setCondition(Constants.EMPTY);
        next.setCondition(move.playerType);

        LineOfAction specific = prev.findSpecificLoa(next);
        if (specific == null) return false;

        ArrayList<LineOfAction> othersOfPrev = prev.findEffectedLoaList(specific);
        for (LineOfAction loa: othersOfPrev) {
            loa.checkerCount--;
        }

        ArrayList<LineOfAction> othersOfNext = next.findEffectedLoaList(specific);
        for (LineOfAction loa: othersOfNext) {
            loa.checkerCount++;
        }

        //TODO: Check checkers that are situated in between
        //isPossible = checkValidity(specific, prev, next);
        //TODO: Capture checkers of opponent

        return true;

    }

    private boolean checkValidity(LineOfAction specificLoa, Block prev, Block next) {
        int count;
        String type;
        if (specificLoa.getType().equals(TypesOfLoa.HORIZONTAL)) {
            count = Math.abs(next.getColumn()-prev.getColumn());
            if (count!=prev.getHorizontal().checkerCount) return false;
            if (prev.getColumn()<next.getColumn()) {
                for (int i = prev.getColumn(); i < next.getColumn() ; i++) {
                    type = board[prev.getRow()][i].getCondition();
                    if(!type.equals(prev.getCondition()) && !type.equals(Constants.EMPTY)) return false;
                }
            }
            if (prev.getColumn()>next.getColumn()) {
                for (int i = next.getColumn(); i >prev.getColumn() ; i--) {
                    type = board[prev.getRow()][i].getCondition();
                    if(!type.equals(prev.getCondition()) && !type.equals(Constants.EMPTY)) return false;
                }
            }

        }
        if (specificLoa.getType().equals(TypesOfLoa.VERTICAL)) {
            count = Math.abs(next.getRow()-prev.getRow());
            if (count!=prev.getVertical().checkerCount) return false;
            if (prev.getRow()<next.getRow()) {
                for (int i = prev.getRow(); i < next.getRow(); i++) {
                    type = board[i][prev.getColumn()].getCondition();
                    if (!type.equals(prev.getCondition()) && !type.equals(Constants.EMPTY)) return false;
                }
            }
            if (prev.getRow()>next.getRow()) {
                for (int i = next.getRow(); i > prev.getRow() ; i--) {
                    type = board[i][prev.getColumn()].getCondition();
                    if (!type.equals(prev.getCondition()) && !type.equals(Constants.EMPTY)) return false;
                }
            }
        }
        if (specificLoa.getType().equals(TypesOfLoa.LEADING_DIAGONAL)) {
            count = 0;
            if (prev.getRow()<next.getRow() && prev.getColumn()<next.getColumn()) {
                for (int i = prev.getRow(),j = prev.getColumn(); i < next.getRow() && j < next.getColumn(); i++, j++) {
                    count++;
                    if (count>prev.getLeadingDiagonal().checkerCount) return false;
                    type = board[i][j].getCondition();
                    if (!type.equals(prev.getCondition()) && !type.equals(Constants.EMPTY)) return false;
                }
            }
            if (prev.getRow()>next.getRow() && prev.getColumn()>next.getColumn()) {
                for (int i = next.getRow(),j = next.getColumn(); i > prev.getRow() && j > prev.getColumn(); i--, j--) {
                    count++;
                    if (count>prev.getLeadingDiagonal().checkerCount) return false;
                    type = board[i][j].getCondition();
                    if (!type.equals(prev.getCondition()) && !type.equals(Constants.EMPTY)) return false;
                }
            }
            if (count!=prev.getLeadingDiagonal().checkerCount) return false;
        }

        return false;
    }

    private void initializeBlack() {
        for (int i = 1; i < Constants.DIMENSION-1; i++) {

            board[0][i].setCondition(Constants.BLACK_TYPE);
            board[0][i].incrementCheckerCount();
            black.getBoardPositionsOfMyType().add(board[0][i]);

            board[Constants.DIMENSION-1][i].setCondition(Constants.BLACK_TYPE);
            board[Constants.DIMENSION-1][i].incrementCheckerCount();
            black.getBoardPositionsOfMyType().add(board[Constants.DIMENSION-1][i]);

        }
    }

    private void initializeWhite() {
        for (int i = 1; i < Constants.DIMENSION - 1; i++) {

            board[i][0].setCondition(Constants.WHITE_TYPE);
            board[i][0].incrementCheckerCount();
            white.getBoardPositionsOfMyType().add(board[i][0]);

            board[i][Constants.DIMENSION-1].setCondition(Constants.WHITE_TYPE);
            board[i][Constants.DIMENSION-1].incrementCheckerCount();
            white.getBoardPositionsOfMyType().add(board[i][Constants.DIMENSION-1]);

        }
    }

    public Block[][] getBoard() {
        return board;
    }

    private void initializeBoard() {
        for (int i = 0; i < Constants.DIMENSION; i++) {
            for (int j = 0; j < Constants.DIMENSION; j++) {
                board[i][j] = new Block(i,j);
                board[i][j].setCondition(Constants.EMPTY);
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

    public String printBoard() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" ").append(" ").append(" ").append(" ");
        for (int i = 0; i < Constants.DIMENSION; i++) {
            stringBuilder.append(i).append(" ").append(" ").append(" ");
        }
        stringBuilder.append("\n");
        for (int i = 0; i < Constants.DIMENSION; i++) {
            stringBuilder.append(i).append(" ").append("| ");
            for (int j = 0; j < Constants.DIMENSION; j++) {
                if (board[i][j].getCondition().equals(Constants.BLACK_TYPE)) stringBuilder.append(Constants.BLACK_TYPE);
                else if (board[i][j].getCondition().equals(Constants.WHITE_TYPE)) stringBuilder.append(Constants.WHITE_TYPE);
                else stringBuilder.append(Constants.EMPTY);
                stringBuilder.append(" | ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
