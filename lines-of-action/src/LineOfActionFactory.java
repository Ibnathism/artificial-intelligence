import java.util.ArrayList;
import java.util.HashMap;

public class LineOfActionFactory {
    private HashMap<String, LineOfAction> loaMap = new HashMap<>();

    public LineOfAction getLoa(TypesOfLoa type, Block position, GamePlay gamePlay) {
        ArrayList<Point> loaPoints = findCoordinates(type, position);
        LineOfAction loa = loaMap.get(loaPoints.toString());

        if (loa == null) {
            //System.out.println(type + ": " + loaPoints.toString());
            LineOfAction myLoa = new LineOfAction(type);
            for (Point point: loaPoints) {
                myLoa.getBoardPositions().add(gamePlay.getBoard()[point.x][point.y]);
            }
            loaMap.put(loaPoints.toString(), myLoa);
            return myLoa;
        }
        else return loa;
    }

    private ArrayList<Point> findCoordinates(TypesOfLoa type, Block position){
        int myRow = position.getRow();
        int myCol = position.getColumn();

        ArrayList<Point> hashKey = new ArrayList<>();

        switch (type){
            case HORIZONTAL:
                for (int i = 0; i < Constants.DIMENSION; i++) {
                    hashKey.add(new Point(myRow, i));
                }
                break;
            case VERTICAL:
                for (int i = 0; i < Constants.DIMENSION; i++) {
                    hashKey.add(new Point(i, myCol));
                }
                break;
            case LEADING_DIAGONAL:
                int tempRow, tempCol;
                if (myRow == myCol) {
                    tempRow = 0;
                    tempCol = 0;
                }
                else if (myRow < myCol){
                    tempRow = 0;
                    tempCol = myCol - myRow;
                }
                else {
                    tempRow = myRow - myCol;
                    tempCol = 0;
                }
                while (tempRow < Constants.DIMENSION && tempCol < Constants.DIMENSION) {
                    hashKey.add(new Point(tempRow, tempCol));
                    tempRow++;
                    tempCol++;
                }
                break;
            case COUNTER_DIAGONAL:
                tempRow = myRow;
                tempCol = myCol;
                while (tempRow<Constants.DIMENSION-1 && tempCol>0) {
                    tempCol--;
                    tempRow++;
                }
                while (tempRow>=0 && tempCol<Constants.DIMENSION) {
                    hashKey.add(new Point(tempRow, tempCol));
                    tempRow--;
                    tempCol++;
                }
                break;
        }
        return hashKey;

    }

}
