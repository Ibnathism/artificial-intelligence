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


}
