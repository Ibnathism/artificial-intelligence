import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here
        ArrayList<ArrayList<Integer>> q = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            q.add(new ArrayList<>());
        }
        //int[][] array = {{1,0,0,4},{0,0,2,0},{3,0,1,0},{0,3,0,0}};
        int[][] array = {{0,1,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                q.get(i).add(array[i][j]);
            }
        }
        Quasigroup quasigroup = new Quasigroup();
        quasigroup.qGroupCompletion(q);
        //quasigroup.printQ(q);




        /** Permutation Test
        //String str = "435";
        ArrayList<Integer> list = new ArrayList<>();
        list.add(4);
        list.add(5);
        list.add(3);
        Possible possible = new Possible();
        possible.getPossibles(list);
        //System.out.println(possible.getPermutations());*/
    }
}
