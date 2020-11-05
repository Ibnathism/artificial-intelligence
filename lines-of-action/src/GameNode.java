import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameNode {
    public static GamePlay copyGame(GamePlay gamePlay) {
        GamePlay newGameNode = new GamePlay();
        Block[][] newGameNodeBlocks = new Block[Constants.DIMENSION][Constants.DIMENSION];
        for (int i = 0; i < Constants.DIMENSION; i++) {
            for (int j = 0; j < Constants.DIMENSION; j++) {
                Block tempBlock = gamePlay.getBoard()[i][j];
                newGameNodeBlocks[i][j] = new Block(tempBlock.getRow(),tempBlock.getColumn());
                newGameNodeBlocks[i][j].setCondition(tempBlock.getCondition());
            }
        }
        newGameNode.setBoard(newGameNodeBlocks);
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
}
