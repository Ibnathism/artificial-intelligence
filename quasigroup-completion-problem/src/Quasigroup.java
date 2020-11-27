import java.util.ArrayList;
import java.util.HashSet;

public class Quasigroup {
    private int count = 0;

    private ArrayList<HashSet<Integer>> getColumnData(ArrayList<ArrayList<Integer>> quasigroup) {
        int dimension = quasigroup.size();
        ArrayList<HashSet<Integer>> columnData = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            columnData.add(new HashSet<>());
        }
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int temp = quasigroup.get(j).get(i);
                if (temp != 0) columnData.get(i).add(temp);
            }
        }
        /*for (int i = 0; i < columnData.size(); i++) {
            System.out.println(columnData.get(i));
        }*/
        return columnData;
    }

    public void qGroupCompletion(ArrayList<ArrayList<Integer>> quasigroup) {
        ArrayList<HashSet<Integer>> columnData = getColumnData(quasigroup);
        runCSP(quasigroup);

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

    private void runCSP(ArrayList<ArrayList<Integer>> quasigroup) {
        //System.out.println("::::runCSP::::");
        //printQ(quasigroup);
        int next = findRowID(quasigroup);
        ArrayList<Integer> nextRow = new ArrayList<>();
        if (next!=-1) nextRow = quasigroup.get(next);

        //setColumns(quasigroup);

        //System.out.println("Next Row "+nextRow);
        if (nextRow!=null && next!=-1) {
            ArrayList<Integer> others = getOthers(next, quasigroup);
            //System.out.println("Others "+others);
            Possible possible = new Possible();
            assert others!=null;
            possible.countAllPossible(others, 0, others.size()-1);
            ArrayList<ArrayList<Integer>> permutations = possible.getPermutations();
            //System.out.println("Permutations "+permutations);
            for (ArrayList<Integer> perm : permutations) {
                ArrayList<ArrayList<Integer>> tempQuasi = getTempQuasi(quasigroup);
                ArrayList<HashSet<Integer>> columnData = getColumnData(tempQuasi);
                //System.out.println("TempQuasi = ");
                //printQ(tempQuasi);
                if (possiblePerm(next, tempQuasi, perm, columnData)) {
                    //System.out.println("TempQuasi After Changing  = ");
                    //printQ(tempQuasi);
                    runCSP(tempQuasi);
                }
            }

        }
        else {
            System.out.println("Solved");
            count++;
            printQ(quasigroup);
            System.out.println("Count "+count);
        }
    }

    private boolean possiblePerm(int next, ArrayList<ArrayList<Integer>> tempQuasi, ArrayList<Integer> perm, ArrayList<HashSet<Integer>> columnData) {
        int val = 0;
        //getColumnData(tempQuasi);
        for (int i = 0; i < tempQuasi.size(); i++) {
            if (tempQuasi.get(next).get(i) == 0) {
                //int c = Integer.parseInt(String.valueOf(perm.charAt(val)));
                int c = perm.get(val);
                if (columnData.get(i).contains(c)) return false;
                tempQuasi.get(next).set(i, c);
                //printQ(tempQuasi);
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
