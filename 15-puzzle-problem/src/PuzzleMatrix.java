import java.util.ArrayList;
import java.util.Arrays;

public class PuzzleMatrix {
    private Pair[][] state;
    private Pair[][] goalState;
    private PuzzleMatrix previous;
    private final int n = 16;
    private final int rootN = (int) Math.sqrt(n);

    public PuzzleMatrix(Pair[][] state, Pair[][] goalState) {
        this.state = state;
        this.goalState = goalState;
    }

    public Pair[][] getState() {
        return state;
    }

    public Pair[][] getGoalState() {
        return goalState;
    }

    @Override
    public String toString() {
        return "PuzzleMatrix{" +
                "matrix=" + Arrays.toString(state) +
                '}';
    }

    public int countMisplaced(){
        int count = 0;
        for (int i = 0; i < rootN; i++){
            for (int j = 0; j < rootN; j++) {
                if (this.state[i][j].getIndex() != 0){
                    if (this.state[i][j].getIndex() != goalState[i][j].getIndex()){
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public int manhattanSum(){
        int manSum = 0;
        int myRow, myCol;
        for (int i = 0; i < rootN; i++) {
            for (int j = 0; j < rootN; j++) {
                int myIndex = this.state[i][j].getIndex();
                //System.out.println(myIndex);
                if (myIndex != 0){
                    myRow = i - ((myIndex-1) / rootN);
                    myCol = j - ((myIndex-1) % rootN);
                    manSum = manSum + Math.abs(myRow) + Math.abs(myCol);
                    //System.out.println("Mansum = " + manSum + "    Value "+ myIndex);
                }
            }
        }
        return manSum;
    }

    private Pair[][] moveBlank(Pair[][] temp, int previ, int prevj, int newi, int newj){
        int a = temp[previ][prevj].getIndex();
        temp[previ][prevj].setIndex(temp[newi][newj].getIndex());
        temp[newi][newj].setIndex(a);
        return temp;
    }




    public ArrayList<PuzzleMatrix> changeState(){
        ArrayList<PuzzleMatrix> puzzleMatrixArrayList = new ArrayList<>();
        Pair[][] myPair = Functions.copyPairArray(this.state);
        int size = state.length;
        for (int i = 0; i < rootN; i++) {
            for (int j = 0; j < rootN; j++) {
                if (myPair[i][j].getIndex() == 0){
                    if (i-1>=0){
                        moveBlank(myPair, i, j, i-1, j);
                        puzzleMatrixArrayList.add(new PuzzleMatrix(myPair, goalState));
                        moveBlank(myPair, i, j, i-1, j);
                    }
                    if (i+1<size){
                        moveBlank(myPair, i, j, i+1, j);
                        puzzleMatrixArrayList.add(new PuzzleMatrix(myPair, goalState));
                        moveBlank(myPair, i, j, i+1, j);
                    }
                    if (j-1>=0){
                        moveBlank(myPair, i, j, i, j-1);
                        puzzleMatrixArrayList.add(new PuzzleMatrix(myPair, goalState));
                        moveBlank(myPair, i, j, i, j-1);
                    }
                    if (j+1<size){
                        moveBlank(myPair, i, j, i, j+1);
                        puzzleMatrixArrayList.add(new PuzzleMatrix(myPair, goalState));
                        moveBlank(myPair, i, j, i, j+1);
                    }
                }
            }
        }
        return puzzleMatrixArrayList;
    }

}
