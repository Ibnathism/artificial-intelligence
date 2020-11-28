import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;


class SmallestDomainComparator implements Comparator<MatrixRow> {
    @Override
    public int compare(MatrixRow row, MatrixRow t1) {
        return Integer.compare(row.getDomain(), t1.getDomain());
    }
}

class MaxDynamicDegreeComparator implements Comparator<MatrixRow> {
    @Override
    public int compare(MatrixRow matrixRow, MatrixRow t1) {
        return Integer.compare(matrixRow.getDynamicDegree(), t1.getDynamicDegree());
    }
}
class BrelazComparator implements Comparator<MatrixRow> {
    @Override
    public int compare(MatrixRow matrixRow, MatrixRow t1) {
        if (matrixRow.getDomain()!=t1.getDomain()) return Integer.compare(matrixRow.getDomain(), t1.getDomain());
        else return Integer.compare(matrixRow.getDynamicDegree(), t1.getDynamicDegree());
    }
}

public class QuasiGroup {
    private boolean foundSolution = false;
    private int nodeCount = 0;
    private int backTrackCount = 0;

    private int findRowSmallestDomain(ArrayList<ArrayList<Integer>> quasigroup) {
        PriorityQueue<MatrixRow> pq = new PriorityQueue<>(quasigroup.size(), new SmallestDomainComparator());
        for (int i = 0; i < quasigroup.size(); i++) {
            if (quasigroup.get(i).contains(0)) {
                MatrixRow temp = new MatrixRow(i, quasigroup, Functions.getRowData(quasigroup), Functions.getColumnData(quasigroup));
                pq.add(temp);
            }
        }
        if (pq.isEmpty()) return -1;
        MatrixRow nextRow = pq.poll();

        return nextRow.getIndex();
    }

    private int findRowID(ArrayList<ArrayList<Integer>> quasigroup) {
        int next = -1;
        for (int i = 0; i < quasigroup.size(); i++) {
            if (quasigroup.get(i).contains(0)) {
                next = i;
                return next;
            }
        }
        return next;
    }

    private int findRowMaxDynamicDegree(ArrayList<ArrayList<Integer>> quasiGroup) {
        PriorityQueue<MatrixRow> pq = new PriorityQueue<>(quasiGroup.size(), new MaxDynamicDegreeComparator());
        for (int i = 0; i < quasiGroup.size(); i++) {
            if (quasiGroup.get(i).contains(0)){
                MatrixRow temp = new MatrixRow(i, quasiGroup, Functions.getRowData(quasiGroup), Functions.getColumnData(quasiGroup));
                pq.add(temp);
            }
        }
        if (pq.isEmpty()) return -1;
        MatrixRow nextRow = pq.poll();

        return nextRow.getIndex();
    }

    private int findBrelaz(ArrayList<ArrayList<Integer>> quasiGroup) {
        PriorityQueue<MatrixRow> pq = new PriorityQueue<>(quasiGroup.size(), new BrelazComparator());
        for (int i = 0; i < quasiGroup.size(); i++) {
            if (quasiGroup.get(i).contains(0)){
                MatrixRow temp = new MatrixRow(i, quasiGroup, Functions.getRowData(quasiGroup), Functions.getColumnData(quasiGroup));
                pq.add(temp);
            }
        }
        if (pq.isEmpty()) return -1;
        MatrixRow nextRow = pq.poll();

        return nextRow.getIndex();
    }


    void runCSP(ArrayList<ArrayList<Integer>> quasigroup) {
        nodeCount++;
        //int next = findRowID(quasigroup);
        //int next = findRowSmallestDomain(quasigroup);
        //int next = findRowMaxDynamicDegree(quasigroup);
        int next = findBrelaz(quasigroup);
        ArrayList<Integer> nextRow = new ArrayList<>();
        if (next!=-1) nextRow = quasigroup.get(next);
        if (!foundSolution){
            if (nextRow!=null && next!=-1) {
                ArrayList<Integer> others = getOthers(next, quasigroup);
                Functions utility = new Functions();
                assert others!=null;
                utility.setAllRowValues(others, 0, others.size()-1);
                ArrayList<ArrayList<Integer>> permutations = utility.getRowValues();
                for (ArrayList<Integer> perm : permutations) {
                    ArrayList<ArrayList<Integer>> tempQuasi = getTempQuasi(quasigroup);
                    ArrayList<HashSet<Integer>> columnData = Functions.getColumnData(tempQuasi);
                    boolean isValid = tryValues(next, tempQuasi, perm, columnData);
                    if (isValid) {
                        runCSP(tempQuasi);
                    }
                }
            }
            else {
                System.out.println("Solved");
                printQ(quasigroup);
                System.out.println("Number of Nodes: "+nodeCount);
                System.out.println("Number of Backtracks: "+backTrackCount);
                foundSolution = true;
            }
        }

    }

    private boolean tryValues(int next, ArrayList<ArrayList<Integer>> tempQuasi, ArrayList<Integer> perm, ArrayList<HashSet<Integer>> columnData) {
        int val = 0;
        //nodeCount++;
        for (int i = 0; i < tempQuasi.size(); i++) {
            if (tempQuasi.get(next).get(i) == 0) {
                //nodeCount++;
                int c = perm.get(val);
                if (columnData.get(i).contains(c)) {
                    backTrackCount++;
                    return false;
                }
                tempQuasi.get(next).set(i, c);
                val++;
            }
        }
        return true;
    }

    private ArrayList<ArrayList<Integer>> getTempQuasi(ArrayList<ArrayList<Integer>> quasigroup) {
        ArrayList<ArrayList<Integer>> temp = new ArrayList<>();
        for (int i = 0; i < quasigroup.size(); i++) {
            temp.add(new ArrayList<>());
        }
        for (int i = 0; i < quasigroup.size(); i++) {
            for (int j = 0; j < quasigroup.size(); j++) {
                temp.get(i).add(quasigroup.get(i).get(j));
            }
        }
        return temp;
    }

    private ArrayList<Integer> getOthers(int next, ArrayList<ArrayList<Integer>> quasigroup) {
        ArrayList<Integer> temp = new ArrayList<>();
        if (next == -1) return null;
        ArrayList<Integer> row = quasigroup.get(next);
        for (int i = 1; i <= row.size(); i++) {
            if (!row.contains(i)) temp.add(i);
        }
        return temp;
    }

    public void printQ(ArrayList<ArrayList<Integer>> q){
        for (int i = 0; i < q.size(); i++) {
            System.out.println(q.get(i));
        }
    }


}
