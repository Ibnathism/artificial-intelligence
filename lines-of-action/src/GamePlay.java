import java.awt.*;
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

    public ArrayList<Move> getPossibleMoves(Block current) {
        ArrayList<Move> moves = new ArrayList<>();


        LineOfAction specific = current.getHorizontal();
        int count = specific.checkerCount;
        String type;
        int i;
        boolean isPossible = true;
        int temp = current.getColumn()-count;
        if (temp >= 0) {
            //left move
            for (i = current.getColumn(); i > temp ; i--) {
                type = board[current.getRow()][i].getCondition();
                if (!type.equals(current.getCondition()) && !type.equals(Constants.EMPTY)) {
                    isPossible = false;
                    break;
                }
            }
            if (isPossible && i==temp) {
                type = board[current.getRow()][i].getCondition();
                if (!type.equals(current.getCondition()) && !type.equals(Constants.EMPTY)) capture();
                moves.add(new Move(current.getCondition(), current, board[current.getRow()][temp]));
            }
        }
        isPossible = true;
        temp = current.getColumn()+count;
        if (temp < Constants.DIMENSION) {
            //right move
            for (i = current.getColumn(); i < temp ; i++) {
                type = board[current.getRow()][i].getCondition();
                if (!type.equals(current.getCondition()) && !type.equals(Constants.EMPTY)) {
                    isPossible = false;
                    break;
                }
            }
            if (isPossible && i==temp) {
                type = board[current.getRow()][i].getCondition();
                if (!type.equals(current.getCondition()) && !type.equals(Constants.EMPTY)) capture();
                moves.add(new Move(current.getCondition(), current, board[current.getRow()][temp]));
            }
        }



        specific = current.getVertical();
        count = specific.checkerCount;
        isPossible = true;
        temp = current.getRow() - count;
        if (temp >= 0) {
            //up move
            for (i = current.getRow(); i > temp ; i--) {
                type = board[i][current.getColumn()].getCondition();
                if (!type.equals(current.getCondition()) && !type.equals(Constants.EMPTY)) {
                    isPossible = false;
                    break;
                }
            }
            if (isPossible && i==temp) {
                type = board[i][current.getColumn()].getCondition();
                if (!type.equals(current.getCondition()) && !type.equals(Constants.EMPTY)) capture();
                moves.add(new Move(current.getCondition(), current, board[temp][current.getColumn()]));
            }
        }
        isPossible = true;
        temp = current.getRow() + count;
        if (temp < Constants.DIMENSION) {
            //down move
            for (i = current.getRow(); i < temp ; i++) {
                type = board[i][current.getColumn()].getCondition();
                if (!type.equals(current.getCondition()) && !type.equals(Constants.EMPTY)) {
                    isPossible = false;
                    break;
                }
            }
            if (isPossible && i==temp) {
                type = board[i][current.getColumn()].getCondition();
                if (!type.equals(current.getCondition()) && !type.equals(Constants.EMPTY)) capture();
                moves.add(new Move(current.getCondition(), current, board[temp][current.getColumn()]));
            }
        }



        specific = current.getLeadingDiagonal();
        Move leftLeadingMove = getOneSideMove(specific, current, true);
        if (leftLeadingMove!=null) moves.add(leftLeadingMove);

        Move rightLeadingMove = getOneSideMove(specific, current, false);
        if (rightLeadingMove!=null) moves.add(rightLeadingMove);

        specific = current.getCounterDiagonal();
        Move leftCounterMove = getOneSideMove(specific, current, true);
        if (leftCounterMove!=null) moves.add(leftCounterMove);

        Move rightCounterMove = getOneSideMove(specific, current, false);
        if (rightCounterMove!=null) moves.add(rightCounterMove);

        for (Move m: moves) {
            System.out.println(m);
        }

        return moves;
    }


    private Move getOneSideMove(LineOfAction specific, Block current, Boolean isLeft) {
        ArrayList<Block> tempBlocks = specific.getBlocks();
        int index = tempBlocks.indexOf(current);
        int count = specific.checkerCount;
        int i = 0;
        while(i<=count) {
            if (isLeft) index--;
            else index++;
            if (index<0 || index>=Constants.DIMENSION) break;
            Block intermediary = tempBlocks.get(index);
            i++;
            if (i==count) {
                if (intermediary.getCondition().equals(current.getCondition())) break; //same color
                else if (intermediary.getCondition().equals(Constants.EMPTY)) return new Move(current.getCondition(), current, intermediary);
                else {
                    capture();
                    return new Move(current.getCondition(), current, intermediary);
                }
            }
            if (!intermediary.getCondition().equals(current.getCondition()) && !intermediary.getCondition().equals(Constants.EMPTY)) break;
        }
        return null;
    }

    private void capture() {

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
