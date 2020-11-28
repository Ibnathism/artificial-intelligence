import java.util.ArrayList;
import java.util.HashSet;

public class Functions {
    private ArrayList<ArrayList<Integer>> rowValues = new ArrayList<>();


    public ArrayList<ArrayList<Integer>> getRowValues() {
        return rowValues;
    }

    public void setAllRowValues(ArrayList<Integer> list, int l, int r) {
        ArrayList<Integer> temp = list;
        if (l == r)
        {
            rowValues.add(temp);
            //System.out.println(str);
        }
        else {
            for (int i = l; i <= r; i++) {
                temp = swap(temp, l, i);
                setAllRowValues(temp, l + 1, r);
                temp = swap(temp, l, i);
            }
        }
    }

    private ArrayList<Integer> swap(ArrayList<Integer> temp, int i, int j) {
        ArrayList<Integer> newArr = new ArrayList<>(temp);
        int a1 = temp.get(i);
        int a2 = temp.get(j);
        newArr.set(i, a2);
        newArr.set(j, a1);
        return newArr;
    }

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
