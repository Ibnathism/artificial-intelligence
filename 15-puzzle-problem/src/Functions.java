public class Functions {
    private static final int n = 16;
    private static final int rootN = (int) Math.sqrt(n);

    public static boolean isSolvable(Pair[][] state){
        int inversion = 0;
        Pair[] newMatrix = new Pair[n];
        int k = 0;
        for (int i = 0; i < rootN; i++) {
            for (int j = 0; j < rootN; j++) {
                newMatrix[k] = new Pair(state[i][j].getIndex(), state[i][j].getValue());
                k++;
            }
        }
        for (int i = 0; i < n-1; i++) {
            for (int j = i+1; j < n ; j++) {
                if (newMatrix[i].getIndex() != 0 && newMatrix[j].getIndex() !=0 && newMatrix[i].getIndex()>newMatrix[j].getIndex()) {
                    //System.out.println(newMatrix[i] + " and " + newMatrix[j] + " is inversion");
                    inversion++;
                }
            }
        }
        //System.out.println(inversion);
        int blank = Functions.findBlank(state);
        if (inversion%2 == 0)  return blank % 2 != 0;
        else return blank % 2 == 0;

    }

    private static int findBlank(Pair[][] state){
        for (int i = 0; i < rootN; i++) {
            for (int j = 0; j < rootN; j++) {
                if (state[i][j].getIndex() == 0) return (i+1) * (j+1) - 1;
            }
        }
        return 0;
    }

    public static Pair[][] buildGoalMatrix(int[][] matrix){
        Pair[][] ans = new Pair[matrix.length][matrix.length];
        int k = 1;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                Pair temp = new Pair(k, matrix[i][j]);
                k++;
                ans[i][j] = temp;
            }
        }
        return ans;
    }

    public static Pair[][] buildInitialMatrix(int[][] matrix){
        Pair[][] init = new Pair[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                Pair temp = new Pair(matrix[i][j], matrix[i][j]);
                init[i][j] = temp;
            }
        }
        return init;
    }

    public static int[][] buildIntMatrix(Pair[][] pairMatrix){
        int[][] ans = new int[pairMatrix.length][pairMatrix.length];
        for (int i = 0; i < pairMatrix.length; i++) {
            for (int j = 0; j < pairMatrix.length; j++) {
                ans[i][j] = pairMatrix[i][j].getValue();
            }
        }
        return ans;
    }
}
