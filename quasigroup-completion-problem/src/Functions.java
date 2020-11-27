import java.util.ArrayList;
import java.util.HashSet;

public class Functions {
    public static ArrayList<HashSet<Integer>> getColumnData(ArrayList<ArrayList<Integer>> quasiGroup) {
        int dimension = quasiGroup.size();
        ArrayList<HashSet<Integer>> columnData = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            columnData.add(new HashSet<>());
        }
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int temp = quasiGroup.get(j).get(i);
                if (temp != 0) columnData.get(i).add(temp);
            }
        }
        /*for (int i = 0; i < columnData.size(); i++) {
            System.out.println(columnData.get(i));
        }*/
        return columnData;
    }

    public static ArrayList<HashSet<Integer>> getRowData(ArrayList<ArrayList<Integer>> quasiGroup) {
        ArrayList<HashSet<Integer>> rowData = new ArrayList<>();
        for (int i = 0; i < quasiGroup.size(); i++) {
            rowData.add(new HashSet<>());
        }
        for (int i = 0; i < quasiGroup.size(); i++) {
            for (int j = 0; j < quasiGroup.size(); j++) {
                int temp = quasiGroup.get(i).get(j);
                if (temp != 0) rowData.get(i).add(temp);
            }
        }
        return rowData;
    }
}
