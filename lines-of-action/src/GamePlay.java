import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GamePlay {
    private GamePlayUtil gamePlayUtil;
    private Block[][] blocks;
    private Player black;
    private Player white;

    public GamePlay() {
        gamePlayUtil = new GamePlayUtil();
        blocks = new Block[Constants.DIMENSION][Constants.DIMENSION];
        black = new Player(Constants.BLACK_TYPE);
        white = new Player(Constants.WHITE_TYPE);
    }

    public void setBlocks(Block[][] blocks) {
        this.blocks = blocks;
    }

    public void setBlack(Player black) {
        this.black = black;
    }

    public void setWhite(Player white) {
        this.white = white;
    }

    public Block[][] getBlocks() {
        return blocks;
    }

    public GamePlayUtil getGamePlayUtil() {
        return gamePlayUtil;
    }

    public boolean gameMove(Move move, ArrayList<Move> possibleMoves) {

        if (!possibleMoves.contains(move)) {
            System.out.println("Doesn't contain in Possible Moves");
            return false;
        }

        Block prev = blocks[move.initPosition.getRow()][move.initPosition.getColumn()];
        String typeOfPlayer = move.getPlayerType();
        String typeOfOtherPlayer;
        if (typeOfPlayer.equals(Constants.WHITE_TYPE)) typeOfOtherPlayer = Constants.BLACK_TYPE;
        else typeOfOtherPlayer = Constants.WHITE_TYPE;
        Block next = blocks[move.finalPosition.getRow()][move.finalPosition.getColumn()];


        if (prev.getCondition().equals(next.getCondition())) {
            //System.out.println("You can't land on your own checker");
            return false;
        }


        LineOfAction specific = prev.findSpecificLoa(next);
        if (specific == null) {
            System.out.println("This is not a line of action");
            return false;
        }

        ArrayList<LineOfAction> othersOfPrev = prev.findEffectedLoaList(specific);
        for (LineOfAction loa: othersOfPrev) {
            loa.checkerCount--;
        }

        ArrayList<LineOfAction> othersOfNext = next.findEffectedLoaList(specific);
        if (next.getCondition().equals(typeOfOtherPlayer)) {
            specific.checkerCount--;
            if (typeOfOtherPlayer.equals(Constants.WHITE_TYPE)) white.getBlockList().remove(next);
            else black.getBlockList().remove(next);
        }
        else {
            for (LineOfAction loa: othersOfNext) {
                loa.checkerCount++;
            }
        }
        prev.setCondition(Constants.EMPTY);
        next.setCondition(move.playerType);
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
                type = blocks[current.getRow()][i].getCondition();
                if (!type.equals(current.getCondition()) && !type.equals(Constants.EMPTY)) {
                    isPossible = false;
                    break;
                }
            }
            if (isPossible && i==temp) {
                type = blocks[current.getRow()][i].getCondition();
                moves.add(new Move(current.getCondition(), current, blocks[current.getRow()][temp]));
            }
        }
        isPossible = true;
        temp = current.getColumn()+count;
        if (temp < Constants.DIMENSION) {
            //right move
            for (i = current.getColumn(); i < temp ; i++) {
                type = blocks[current.getRow()][i].getCondition();
                if (!type.equals(current.getCondition()) && !type.equals(Constants.EMPTY)) {
                    isPossible = false;
                    break;
                }
            }
            if (isPossible && i==temp) {
                type = blocks[current.getRow()][i].getCondition();
                moves.add(new Move(current.getCondition(), current, blocks[current.getRow()][temp]));
            }
        }



        specific = current.getVertical();
        count = specific.checkerCount;
        isPossible = true;
        temp = current.getRow() - count;
        if (temp >= 0) {
            //up move
            for (i = current.getRow(); i > temp ; i--) {
                type = blocks[i][current.getColumn()].getCondition();
                if (!type.equals(current.getCondition()) && !type.equals(Constants.EMPTY)) {
                    isPossible = false;
                    break;
                }
            }
            if (isPossible && i==temp) {
                type = blocks[i][current.getColumn()].getCondition();
                moves.add(new Move(current.getCondition(), current, blocks[temp][current.getColumn()]));
            }
        }
        isPossible = true;
        temp = current.getRow() + count;
        if (temp < Constants.DIMENSION) {
            //down move
            for (i = current.getRow(); i < temp ; i++) {
                type = blocks[i][current.getColumn()].getCondition();
                if (!type.equals(current.getCondition()) && !type.equals(Constants.EMPTY)) {
                    isPossible = false;
                    break;
                }
            }
            if (isPossible && i==temp) {
                type = blocks[i][current.getColumn()].getCondition();
                moves.add(new Move(current.getCondition(), current, blocks[temp][current.getColumn()]));
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

        /*for (Move m: moves) {
            System.out.println(m);
        }*/

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
            if (index<0 || index>=Constants.DIMENSION || index>=tempBlocks.size()) break;
            Block intermediary = tempBlocks.get(index);
            i++;
            if (i==count) {
                if (intermediary.getCondition().equals(current.getCondition())) break; //same color
                else return new Move(current.getCondition(), current, intermediary);

            }
            else {
                if (!intermediary.getCondition().equals(current.getCondition()) && !intermediary.getCondition().equals(Constants.EMPTY)) break;
            }

        }
        return null;
    }

    public int runMinimaxAlgorithm(GamePlay gamePlay, int depth, int alpha, int beta, boolean isMaximizingPlayer) {
        if (depth == 0 || isGameOver(gamePlay)) {
            //System.out.println("Depth reached");
            return getStaticEvaluation(gamePlay, white, black);
        }
        int eval;
        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            ArrayList<GamePlay> childrenOfMax = gamePlay.getChildren();
            for(GamePlay gp: childrenOfMax) {
                eval = runMinimaxAlgorithm(gp, depth-1, alpha, beta, false);
                maxEval = Integer.max(maxEval, eval);
                alpha = Integer.max(alpha, eval);
                if (beta <= alpha) break;
            }
            return maxEval;
        }
        else {
            int minEval = Integer.MAX_VALUE;
            ArrayList<GamePlay> childrenOfMin = gamePlay.getChildren();
            for (GamePlay gp: childrenOfMin) {
                eval = runMinimaxAlgorithm(gp, depth-1, alpha, beta, true);
                minEval = Integer.min(minEval, eval);
                beta = Integer.min(beta, eval);
                if (beta <= alpha) break;
            }
            return minEval;
        }

    }

    public void startPlaying() {
        boolean isWhitesTurn = false;
        for (int i = 0; i < 10; i++) {
            if (!isWhitesTurn) {
                System.out.println("Black's turn");
                ArrayList<GamePlay> possibleMoves = getChildren();
                int min = Integer.MAX_VALUE;
                GamePlay minGamePlay = this;
                for (GamePlay gamePlay: possibleMoves) {
                    int miniMax = runMinimaxAlgorithm(gamePlay, Constants.DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                    //System.out.println("  "+ miniMax);
                    if (miniMax<min){
                        min = miniMax;
                        minGamePlay = gamePlay;
                    }
                }
                System.out.println(minGamePlay);

                isWhitesTurn = true;
            }
            else {
                System.out.println("White's turn");
                Scanner scanner = new Scanner(System.in);
                String input;
                System.out.println("Format : (W/B):row,col:final_row,final_col");
                input = scanner.next();
                String playerType = input.split(":")[0];
                Block init = this.getBlocks()[Integer.parseInt(input.split(":")[1].split(",")[0])][Integer.parseInt(input.split(":")[1].split(",")[1])];
                Block next = this.getBlocks()[Integer.parseInt(input.split(":")[2].split(",")[0])][Integer.parseInt(input.split(":")[2].split(",")[1])];
                Move move = new Move(playerType, init, next);
                ArrayList<Move> possibleMoves = this.getPossibleMoves(init);
                boolean canMove = this.gameMove(move, possibleMoves);
                if (canMove) System.out.println("Move Successful");
                else System.out.println("Invalid Move");
                isWhitesTurn = false;
            }
        }
    }

    private ArrayList<GamePlay> getChildren() {
        ArrayList<GamePlay> possibleMoves = new ArrayList<>();
        for (Block piece:black.getBlockList()) {
            GamePlay tempGameNode = GameNode.copyGame(this);
            //System.out.println(tempGameNode);
            ArrayList<Move> myPossibles = tempGameNode.getPossibleMoves(piece);
            for (Move move: myPossibles) {
                GamePlay tempTempGameNode = GameNode.copyGame(tempGameNode);
                boolean isPos = tempTempGameNode.gameMove(move, myPossibles);
                //System.out.println("Move "+move + "\n" + tempTempGameNode);
                if (isPos) possibleMoves.add(tempTempGameNode);
            }
        }
        return possibleMoves;
    }

    private boolean isGameOver(GamePlay gamePlay) {
        //TODO
        return false;
    }

    public int getStaticEvaluation(GamePlay gamePlay, Player white, Player black) {
        //TODO improvement
        int[][] vals = {{-80, -25, -20, -20, -20, -20, -25, -80},
                {-25,  10,  10,  10,  10,  10,  10,  -25},
                {-20,  10,  25,  25,  25,  25,  10,  -20},
                {-20,  10,  25,  50,  50,  25,  10,  -20},
                {-20,  10,  25,  50,  50,  25,  10,  -20},
                {-20,  10,  25,  25,  25,  25,  10,  -20},
                {-25,  10,  10,  10,  10,  10,  10,  -25},
                {-80, -25, -20, -20, -20, -20, -25, -80}};
        int se = 0;
        int whiteSum = 0;
        int blackSum = 0;
        for (Block block:black.getBlockList()) {
            blackSum = blackSum + vals[block.getRow()][block.getColumn()];
        }
        for (Block block:white.getBlockList()) {
            whiteSum = whiteSum + vals[block.getRow()][block.getColumn()];
        }
        return whiteSum - blackSum;
    }


    private void initializeBlack() {
        for (int i = 1; i < Constants.DIMENSION-1; i++) {

            blocks[0][i].setCondition(Constants.BLACK_TYPE);
            blocks[0][i].incrementCheckerCount();
            black.getBlockList().add(blocks[0][i]);

            blocks[Constants.DIMENSION-1][i].setCondition(Constants.BLACK_TYPE);
            blocks[Constants.DIMENSION-1][i].incrementCheckerCount();
            black.getBlockList().add(blocks[Constants.DIMENSION-1][i]);

        }
    }

    private void initializeWhite() {
        for (int i = 1; i < Constants.DIMENSION - 1; i++) {

            blocks[i][0].setCondition(Constants.WHITE_TYPE);
            blocks[i][0].incrementCheckerCount();
            white.getBlockList().add(blocks[i][0]);

            blocks[i][Constants.DIMENSION-1].setCondition(Constants.WHITE_TYPE);
            blocks[i][Constants.DIMENSION-1].incrementCheckerCount();
            white.getBlockList().add(blocks[i][Constants.DIMENSION-1]);

        }
    }

    public void initializeBoard() {
        for (int i = 0; i < Constants.DIMENSION; i++) {
            for (int j = 0; j < Constants.DIMENSION; j++) {
                blocks[i][j] = new Block(i,j);
                blocks[i][j].setCondition(Constants.EMPTY);
            }
        }
        initializeLineOfAction();
        initializeWhite();
        initializeBlack();
    }

    public void initializeLineOfAction() {
        for (int i = 0; i < Constants.DIMENSION; i++) {
            for (int j = 0; j < Constants.DIMENSION; j++) {
                blocks[i][j].setHorizontal(gamePlayUtil.getLoa(TypesOfLoa.HORIZONTAL, blocks[i][j], this));
                blocks[i][j].setVertical(gamePlayUtil.getLoa(TypesOfLoa.VERTICAL, blocks[i][j], this));
                blocks[i][j].setLeadingDiagonal(gamePlayUtil.getLoa(TypesOfLoa.LEADING_DIAGONAL, blocks[i][j], this));
                blocks[i][j].setCounterDiagonal(gamePlayUtil.getLoa(TypesOfLoa.COUNTER_DIAGONAL, blocks[i][j], this));
            }
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GamePlay gamePlay = (GamePlay) o;
        return Arrays.deepEquals(blocks, ((GamePlay) o).blocks);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(blocks);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" ").append(" ").append(" ").append(" ");
        for (int i = 0; i < Constants.DIMENSION; i++) {
            stringBuilder.append(i).append(" ").append(" ").append(" ");
        }
        stringBuilder.append("\n");
        for (int i = 0; i < Constants.DIMENSION; i++) {
            stringBuilder.append(i).append(" ").append("| ");
            for (int j = 0; j < Constants.DIMENSION; j++) {
                if (blocks[i][j].getCondition().equals(Constants.BLACK_TYPE)) stringBuilder.append(Constants.BLACK_TYPE);
                else if (blocks[i][j].getCondition().equals(Constants.WHITE_TYPE)) stringBuilder.append(Constants.WHITE_TYPE);
                else stringBuilder.append(Constants.EMPTY);
                stringBuilder.append(" | ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
