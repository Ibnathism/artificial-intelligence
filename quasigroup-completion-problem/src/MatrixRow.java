import java.util.ArrayList;
import java.util.HashSet;

public class MatrixRow {
    private int index;
    private ArrayList<ArrayList<Integer>> myQuasiGroup;
    private ArrayList<HashSet<Integer>> rowData;
    private ArrayList<HashSet<Integer>> columnData;

    public MatrixRow(int index, ArrayList<ArrayList<Integer>> myQuasiGroup, ArrayList<HashSet<Integer>> rowData, ArrayList<HashSet<Integer>> columnData) {
        this.index = index;
        this.myQuasiGroup = myQuasiGroup;
        this.rowData = rowData;
        this.columnData = columnData;
    }

    public int getIndex() {
        return index;
    }

    public int getDomain(){
        int domain = 0;
        ArrayList<Integer> row = myQuasiGroup.get(index);
        for (int col = 0; col < row.size(); col++) {
            int temp = myQuasiGroup.get(index).get(col);
            if (temp != 0) continue;
            HashSet<Integer> tempSet = new HashSet<>();
            tempSet.addAll(rowData.get(index));
            tempSet.addAll(columnData.get(col));
            domain = domain + myQuasiGroup.size()-tempSet.size();
        }
        return domain;
    }

    public int getDynamicDegree() {
        int dynamicDeg = 0;
        ArrayList<Integer> row = myQuasiGroup.get(index);
        for (int col = 0; col < row.size(); col++) {
            int temp = myQuasiGroup.get(index).get(col);
            if (temp != 0) continue;
            dynamicDeg = dynamicDeg + 2*row.size() - rowData.get(index).size() - columnData.get(col).size();
        }
        return dynamicDeg;
    }



}
