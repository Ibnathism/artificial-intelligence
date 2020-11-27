import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here
        ArrayList<ArrayList<Integer>> q = new ArrayList<>();
        //int[][] array = {{1,0,0,4},{0,0,2,0},{3,0,1,0},{0,3,0,0}};
        //int[][] array = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
        //int[][] array = {{0,0,0},{0,0,0},{0,0,0}};
        //int[][] array = {{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
        int[][] array = {{0, 0, 6, 0, 0, 3, 4, 0, 10, 0},
                        {2, 6, 4, 0, 0, 0, 0, 0, 9, 0},
                        {0, 2, 10, 0, 0, 0, 0, 0, 5, 9},
                        {10, 1, 5, 4, 2, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 1, 9, 8, 4, 0, 0},
                        {0, 0, 3, 2, 9, 0, 0, 1, 0, 0},
                        {6, 0, 0, 0, 0, 7, 0, 10, 0, 5},
                        {0, 0, 0, 0, 0, 8, 6, 5, 0, 7},
                        {1, 3, 0, 6, 0, 0, 5, 0, 0, 2},
                        {0, 5, 0, 9, 6, 2, 0, 0, 8, 0}};
        //int[][] array = {{1, 0, 3, 0 },{0, 1, 0, 2 },{4, 0, 0, 0 },{0, 0, 0, 1}};
        for (int i = 0; i < array.length; i++) {
            q.add(new ArrayList<>());
        }

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                q.get(i).add(array[i][j]);
            }
        }
        QuasiGroup quasigroup = new QuasiGroup();
        System.out.println(String.format("Start Time " + System.currentTimeMillis()));
        quasigroup.runCSP(q);
        System.out.println("End Time "+System.currentTimeMillis());
        //quasigroup.printQ(q);




        /** Permutation Test
        ArrayList<Integer> list = new ArrayList<>();
        list.add(4);
        list.add(5);
        list.add(3);
        Possible possible = new Possible();
        possible.getPossibles(list);
        System.out.println(possible.getPermutations());*/
    }
}
