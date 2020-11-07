import java.util.*;

public class GameNode {
    public static GamePlay copyGame(GamePlay gamePlay) {
        GamePlay newGameNode = new GamePlay();
        Block[][] newGameNodeBlocks = new Block[Constants.DIMENSION][Constants.DIMENSION];
        for (int i = 0; i < Constants.DIMENSION; i++) {
            for (int j = 0; j < Constants.DIMENSION; j++) {
                Block tempBlock = gamePlay.getBlocks()[i][j];
                newGameNodeBlocks[i][j] = new Block(tempBlock.getRow(),tempBlock.getColumn());
                newGameNodeBlocks[i][j].setCondition(tempBlock.getCondition());
            }
        }
        newGameNode.setBlocks(newGameNodeBlocks);
        Player white = new Player(Constants.WHITE_TYPE);
        Player black = new Player(Constants.BLACK_TYPE);
        ArrayList<Block> blocksOfWhite = new ArrayList<>();
        ArrayList<Block> blocksOfBlack = new ArrayList<>();

        for (int i = 0; i < Constants.DIMENSION; i++) {
            for (int j = 0; j < Constants.DIMENSION; j++) {
                if (newGameNodeBlocks[i][j].getCondition().equals(Constants.WHITE_TYPE)) blocksOfWhite.add(newGameNodeBlocks[i][j]);
                if (newGameNodeBlocks[i][j].getCondition().equals(Constants.BLACK_TYPE)) blocksOfBlack.add(newGameNodeBlocks[i][j]);
            }
        }
        white.setBlockList(blocksOfWhite);
        black.setBlockList(blocksOfBlack);
        newGameNode.setWhite(white);
        newGameNode.setBlack(black);

        newGameNode.initializeLineOfAction();


        HashMap<String, LineOfAction> map = newGameNode.getGamePlayUtil().getLoaMap();
        HashMap<String, LineOfAction> oldMAp = gamePlay.getGamePlayUtil().getLoaMap();
        for (Map.Entry entry: oldMAp.entrySet()){
            String str = (String) entry.getKey();
            LineOfAction newLoa = map.get(str);
            newLoa.checkerCount = ((LineOfAction)entry.getValue()).checkerCount;
        }
        return newGameNode;
    }

    public static  GamePlay startPlaying(boolean isWhitesTurn, GamePlay game) {
        if (!isWhitesTurn) {
            System.out.println("Black's turn");
            ArrayList<GamePlay> possibleMoves = game.getChildren();
            int min = Integer.MAX_VALUE;
            GamePlay minGamePlay = null;
            for (GamePlay gamePlay: possibleMoves) {
                int miniMax = runMinimaxAlgorithm(gamePlay, getDepth(gamePlay.getBlack()), Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                //System.out.println("  "+ miniMax);
                if (miniMax<min){
                    min = miniMax;
                    minGamePlay = gamePlay;
                }
            }
            System.out.println(minGamePlay);
            //this.blocks = minGamePlay.blocks;
            return minGamePlay;

        }

        /*System.out.println("White's turn");
        ArrayList<GamePlay> possibleMoves = game.getChildren();
        int min = Integer.MAX_VALUE;
        GamePlay minGamePlay = null;
        for (GamePlay gamePlay: possibleMoves) {
            int miniMax = runMinimaxAlgorithm(gamePlay, Constants.DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
            //System.out.println("  "+ miniMax);
            if (miniMax<min){
                min = miniMax;
                minGamePlay = gamePlay;
            }
        }
        System.out.println(minGamePlay);
        //this.blocks = minGamePlay.blocks;
        return minGamePlay;*/
        System.out.println("White's turn");
        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.println("Format : row,col:final_row,final_col");
        input = scanner.next();
        Block init = game.getBlocks()[Integer.parseInt(input.split(":")[0].split(",")[0])][Integer.parseInt(input.split(":")[0].split(",")[1])];
        Block next = game.getBlocks()[Integer.parseInt(input.split(":")[1].split(",")[0])][Integer.parseInt(input.split(":")[1].split(",")[1])];
        Move move = new Move(Constants.WHITE_TYPE, init, next);
        ArrayList<Move> possibleMoves = game.getPossibleMoves(init);
        for (Move m: possibleMoves) {
            System.out.println(m);
        }
        boolean canMove = game.gameMove(move, possibleMoves);
        if (canMove) {
            System.out.println("Move Successful");
            System.out.println(game);
        }
        else System.out.println("Invalid Move");
        return game;
    }

    private static boolean isWon(Player player) {
        int count = 1;
        if (player.getBlockList().size()==1) return true;
        HashSet<Point> connected = new HashSet<>();
        connected.add(new Point(player.getBlockList().get(0).getRow(), player.getBlockList().get(0).getColumn()));
        HashSet<Point> attached = GameNode.getAttached(player.getBlockList().get(0));
        ArrayList<Block> temp;

        while (count < player.getBlockList().size()) {
            temp = getConnectedCheckers(player, connected, attached);
            if (temp.size()==0) return false;
            count = count + temp.size();

            for (Block block: temp) {
                Point p = new Point(block.getRow(), block.getColumn());
                attached.remove(p);
                connected.add(p);
                attached.addAll(getAttached(block));
            }
        }
        return true;
    }

    private static ArrayList<Block> getConnectedCheckers(Player player, HashSet<Point> connected, HashSet<Point> attached) {
        ArrayList<Block> blocks = new ArrayList<>();
        for (Block block: player.getBlockList()) {
            if (!connected.contains(new Point(block.getRow(), block.getColumn())) && attached.contains(new Point(block.getRow(), block.getColumn())))
                blocks.add(block);
        }
        return blocks;
    }

    private static HashSet<Point> getAttached(Block block) {
        int row = block.getRow();
        int col = block.getColumn();
        boolean condition1 = col-1>=0;
        boolean condition2 = col+1<Constants.DIMENSION;
        boolean condition3 = row-1>=0;
        boolean condition4 = row+1 < Constants.DIMENSION;
        HashSet<Point> attached = new HashSet<>();
        if (condition1) attached.add(new Point(row, col-1));
        if (condition2) attached.add(new Point(row, col+1));
        if (condition3) attached.add(new Point(row-1, col));
        if (condition4) attached.add(new Point(row+1, col));
        if (condition1 && condition3) attached.add(new Point(row-1, col-1));
        if (condition1 && condition4) attached.add(new Point(row+1, col-1));
        if (condition2 && condition3) attached.add(new Point(row-1, col+1));
        if (condition2 && condition4) attached.add(new Point(row+1, col+1));
        return attached;
    }


    public static boolean checkEndGame(GamePlay game){
        return isWon(game.getBlack()) || isWon(game.getWhite());
    }

    public static int getDepth(Player player) {
        int d = (int) Math.ceil(Constants.DEPTH - (Math.log(player.getBlockList().size())/Math.log(2)));

        if (d == 0) return 1;
        return d;
    }

    public static int getStaticEvaluation(GamePlay gamePlay) {
        return 4*getPieceSquareTableHue(gamePlay)+getAreaHeuristic(gamePlay)+2*getMobilityHeu(gamePlay);
    }


    public static int getAreaHeuristic(GamePlay gamePlay) {
        return getArea(gamePlay.getWhite()) - getArea(gamePlay.getBlack());
    }
    public static int getArea(Player player) {
        int rowX = Integer.MIN_VALUE;
        int colMax = Integer.MIN_VALUE;
        int rowN = Integer.MAX_VALUE;
        int colN = Integer.MAX_VALUE;
        Point vector1, vector2;

        for (Block block: player.getBlockList()) {
            if (block.getRow()>rowX) rowX = block.getRow();
            if (block.getColumn()>colMax) colMax = block.getColumn();
            if (block.getRow()<rowN) rowN = block.getRow();
            if (block.getColumn()<colN) colN = block.getColumn();
        }
        Point A = new Point(rowN, colN);
        Point B = new Point(rowX, colN);
        Point C = new Point(rowX, colMax);
        vector1 = new Point(C.x-B.x, C.y-B.y);
        vector2 = new Point(A.x-B.x, A.y-B.y);
        return (vector1.x * vector2.y) - (vector1.y * vector2.x);
    }



    public static int getPieceSquareTableHue(GamePlay gamePlay) {
        int[][] val = {{-80, -25, -20, -20, -20, -20, -25, -80},
                {-25,  10,  10,  10,  10,  10,  10,  -25},
                {-20,  10,  25,  25,  25,  25,  10,  -20},
                {-20,  10,  25,  50,  50,  25,  10,  -20},
                {-20,  10,  25,  50,  50,  25,  10,  -20},
                {-20,  10,  25,  25,  25,  25,  10,  -20},
                {-25,  10,  10,  10,  10,  10,  10,  -25},
                {-80, -25, -20, -20, -20, -20, -25, -80}};
        int whiteSum = 0;
        int blackSum = 0;
        for (Block block:gamePlay.getBlack().getBlockList()) {
            blackSum = blackSum + val[block.getRow()][block.getColumn()];
        }
        for (Block block:gamePlay.getWhite().getBlockList()) {
            whiteSum = whiteSum + val[block.getRow()][block.getColumn()];
        }
        return whiteSum - blackSum;
    }

    public static int getMobilityHeu(GamePlay gamePlay) {
        int whiteSum = 0;
        int blackSum = 0;
        for (Block block: gamePlay.getBlack().getBlockList()) {
            ArrayList<Move> possMoves = gamePlay.getPossibleMoves(block);
            blackSum = blackSum + possMoves.size();
        }
        for (Block block: gamePlay.getWhite().getBlockList()) {
            ArrayList<Move> possMoves = gamePlay.getPossibleMoves(block);
            whiteSum = whiteSum + possMoves.size();
        }
        return whiteSum - blackSum;
    }


    public static int runMinimaxAlgorithm(GamePlay gamePlay, int depth, int alpha, int beta, boolean isMaximizingPlayer) {
        if (depth == 0 || checkEndGame(gamePlay)) {
            //System.out.println("Depth reached");
            return getStaticEvaluation(gamePlay);
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
}
