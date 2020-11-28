import java.io.*;
import java.util.ArrayList;

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
        quasigroup.runCSP(q);
    }

    public static void main(String[] args) {
        String[] dataSets = {"d-10-01.txt.txt", "d-10-06.txt.txt", "d-10-07.txt.txt", "d-10-08.txt.txt", "d-10-09.txt.txt", "d-15-01.txt.txt"};

        for (int j = 0; j < dataSets.length-1;j++) {
            String fileName = dataSets[j];
            System.out.println("\n::::::::"+fileName+":::::::::\n");
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
