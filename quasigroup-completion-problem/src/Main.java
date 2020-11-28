import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    private static void run(int[][] array) {
        ArrayList<ArrayList<Integer>> q = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            q.add(new ArrayList<>());
        }

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                q.get(i).add(array[i][j]);
            }
        }
        QuasiGroup quasigroup = new QuasiGroup();
        //System.out.println(String.format("Start Time " + System.currentTimeMillis()));
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));
        quasigroup.runCSP(q);
        //System.out.println("End Time "+System.currentTimeMillis());
        formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));
        //quasigroup.printQ(q);
    }

    public static void main(String[] args) {
	// write your code here

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

        String[] dataSets = {"d-10-01.txt.txt", "d-10-06.txt.txt", "d-10-07.txt.txt", "d-10-08.txt.txt", "d-10-09.txt.txt", "d-15-01.txt.txt"};

        for (int j = 0; j < dataSets.length-1;j++) {
            String fileName = dataSets[j];
            readFile(fileName);
        }
    }

    private static void readFile(String fileName) {

        BufferedReader bufferedReader;
        File file = new File(fileName);
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String str = bufferedReader.readLine();
            int n = Integer.parseInt(str);
            int[][] array = new int[n][n];
            for (int i = 0; i < n; i++) {
                String[] values = bufferedReader.readLine().trim().split(",");
                for (int j = 0; j < n; j++) {
                    array[i][j] = Integer.parseInt(values[j].trim());
                }
            }
            run(array);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
