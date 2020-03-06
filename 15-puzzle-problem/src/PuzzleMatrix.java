import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PuzzleMatrix {
    private Pair[][] state;
    private Pair[][] goalState;
    private PuzzleMatrix previous;
    private int nodesTraversed;
    private final int n = 16;
    private final int rootN = (int) Math.sqrt(n);


    public PuzzleMatrix(Pair[][] state, Pair[][] goalState) {
        this.state = state;
        this.goalState = goalState;
        nodesTraversed = 0;
    }

    public PuzzleMatrix(Pair[][] state, Pair[][] goalState, PuzzleMatrix previous, int nodesTraversed) {
        this.state = state;
        this.goalState = goalState;
        this.previous = previous;
        this.nodesTraversed = nodesTraversed;
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
                "state=" + Arrays.toString(state) +
                '}';
    }

    public PuzzleMatrix getPrevious() {
        return previous;
    }

    public void setPrevious(PuzzleMatrix previous) {
        this.previous = previous;
    }

    public int getNodesTraversed() {
        return nodesTraversed;
    }

    public void setNodesTraversed(int nodesTraversed) {
        this.nodesTraversed = nodesTraversed;
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


    public boolean isGoalReached(){
        for (int i = 0; i < rootN; i++) {
            for (int j = 0; j < rootN; j++) {
                if (goalState[i][j].getIndex() != this.state[i][j].getIndex()) return false;
            }
        }
        return true;
    }

    private Pair[][] swap(Pair[][] pairs, Pair prev, Pair next){
        Pair[][] ans = new Pair[pairs.length][pairs.length];
        for (int i = 0; i < pairs.length; i++) {
            for (int j = 0; j < pairs.length; j++) {
                if (pairs[i][j].equals(prev))
                    ans[i][j] = next;
                else if (pairs[i][j].equals(next))
                    ans[i][j] = prev;
                else ans[i][j] = pairs[i][j];
            }
        }
        return ans;
    }




    public List<PuzzleMatrix> possibleStates(){
        List<PuzzleMatrix> puzzleMatrixArrayList = new ArrayList<>();
        Pair[][] myPair = Functions.copyPairArray(this.state);
       int size = state.length;
        for (int i = 0; i < rootN; i++) {
            for (int j = 0; j < rootN; j++) {
                if (myPair[i][j].getIndex() == 0){
                    if (i-1>=0){
                        Pair[][] p = this.swap(myPair, myPair[i][j], myPair[i-1][j]);
                        puzzleMatrixArrayList.add(new PuzzleMatrix(p, goalState));
                        myPair = Functions.copyPairArray(this.state);
                    }
                    if (i+1<size){

                        Pair[][] p = this.swap(myPair, myPair[i][j], myPair[i+1][j]);
                        puzzleMatrixArrayList.add(new PuzzleMatrix(p, goalState));
                        myPair = Functions.copyPairArray(this.state);

                    }
                    if (j-1>=0){

                        Pair[][] p = this.swap(myPair, myPair[i][j], myPair[i][j-1]);
                        puzzleMatrixArrayList.add(new PuzzleMatrix(p, goalState));
                        myPair = Functions.copyPairArray(this.state);
                       }
                    if (j+1<size){

                        Pair[][] p = this.swap(myPair, myPair[i][j], myPair[i][j+1]);
                        puzzleMatrixArrayList.add(new PuzzleMatrix(p, goalState));
                        myPair = Functions.copyPairArray(this.state);

                    }
                }
            }
        }
        return puzzleMatrixArrayList;
    }

}
