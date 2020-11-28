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
        return Integer.compare(t1.getDynamicDegree(), matrixRow.getDynamicDegree());
    }
}

public class QuasiGroup {
    //private int count = 0;
    private boolean foundSolution = false;

    private int findRowSmallestDomain(ArrayList<ArrayList<Integer>> quasigroup) {
        PriorityQueue<MatrixRow> pq = new PriorityQueue<>(quasigroup.size(), new SmallestDomainComparator());
        for (int i = 0; i < quasigroup.size(); i++) {
            if (quasigroup.get(i).contains(0)) {
                MatrixRow temp = new MatrixRow(i, quasigroup, Functions.getRowData(quasigroup), Functions.getColumnData(quasigroup));
                pq.add(temp);
                //next = i;
                //return next;
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


    void runCSP(ArrayList<ArrayList<Integer>> quasigroup) {
        //int next = findRowID(quasigroup);
        int next = findRowSmallestDomain(quasigroup);
        //int next = findRowMaxDynamicDegree(quasigroup);
        ArrayList<Integer> nextRow = new ArrayList<>();
        if (next!=-1) nextRow = quasigroup.get(next);
        if (!foundSolution){
            if (nextRow!=null && next!=-1) {
                ArrayList<Integer> others = getOthers(next, quasigroup);
                Possible possible = new Possible();
                assert others!=null;
                possible.countAllPossible(others, 0, others.size()-1);
                ArrayList<ArrayList<Integer>> permutations = possible.getPermutations();
                for (ArrayList<Integer> perm : permutations) {
                    ArrayList<ArrayList<Integer>> tempQuasi = getTempQuasi(quasigroup);
                    ArrayList<HashSet<Integer>> columnData = Functions.getColumnData(tempQuasi);
                    if (possiblePerm(next, tempQuasi, perm, columnData)) {
                        runCSP(tempQuasi);
                    }
                }
            }
            else {
                System.out.println("Solved");
                //count++;
                printQ(quasigroup);
                //System.out.println("Count " + count);
                foundSolution = true;
            }
        }

    }

    private boolean possiblePerm(int next, ArrayList<ArrayList<Integer>> tempQuasi, ArrayList<Integer> perm, ArrayList<HashSet<Integer>> columnData) {
        int val = 0;
        for (int i = 0; i < tempQuasi.size(); i++) {
            if (tempQuasi.get(next).get(i) == 0) {
                int c = perm.get(val);
                if (columnData.get(i).contains(c)) return false;
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
