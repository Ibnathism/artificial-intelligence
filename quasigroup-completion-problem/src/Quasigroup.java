import java.util.ArrayList;
import java.util.HashSet;

public class Quasigroup {
    private ArrayList<HashSet<Integer>> columnData;

    public Quasigroup() {
        columnData = new ArrayList<>();
    }

    public void qGroupCompletion(ArrayList<ArrayList<Integer>> quasigroup) {
        int dimension = quasigroup.size();

        for (int i = 0; i < dimension; i++) {
            columnData.add(new HashSet<>());
        }
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int temp = quasigroup.get(i).get(j);
                if (temp != -1) columnData.get(i).add(temp);
            }
        }

        runCSP(quasigroup);

    }

    private void runCSP(ArrayList<ArrayList<Integer>> quasigroup) {
        ArrayList<Integer> nextRow = null;
        int next = -1;
        for (int i = 0; i < quasigroup.size(); i++) {
            if (quasigroup.get(i).contains(0)) {
                nextRow = quasigroup.get(i);
                next = i;
                break;
            }
        }
        //System.out.println(nextRow);
        if (nextRow!=null) {
            ArrayList<Integer> others = getOthers(next, quasigroup);
            Possible possible = new Possible();
            possible.getPossibles(others);
            ArrayList<String> permutations = possible.getPermutations();
            for (String perm : permutations) {
                ArrayList<ArrayList<Integer>> tempQuasi = getTempQuasi(quasigroup);
                //System.out.println("TempQuasi = ");
                //printQ(tempQuasi);
                if (possiblePerm(next, tempQuasi, perm)) {
                    //System.out.println("TempQuasi = ");
                    //printQ(tempQuasi);
                    runCSP(tempQuasi);
                }
            }

        }
        else {
            System.out.println("Solved");
            //printQ(quasigroup);
        }
    }

    private boolean possiblePerm(int next, ArrayList<ArrayList<Integer>> tempQuasi, String perm) {
        int val = 0;
        System.out.println(perm);
        for (int i = 0; i < perm.length(); i++) {
            if (tempQuasi.get(next).get(i) == 0) {
                int c = Integer.parseInt(String.valueOf(perm.charAt(val)));
                if (columnData.get(i).contains(c)) return false;
                tempQuasi.get(next).add(i, c);
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
        ArrayList<Integer> row = quasigroup.get(next);
        for (int i = 0; i < row.size(); i++) {
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
