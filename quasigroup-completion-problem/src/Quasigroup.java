import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Quasigroup {
    private ArrayList<HashSet<Integer>> columnData;

    public Quasigroup(ArrayList<ArrayList<Integer>> array) {
        columnData = new ArrayList<>();
    }

    public void qGroupCompletion(ArrayList<ArrayList<Integer>> quasigroup) {
        int dimention = quasigroup.size();

        for (int i = 0; i < dimention; i++) {
            columnData.add(new HashSet<>());
        }
        for (int i = 0; i < dimention; i++) {
            for (int j = 0; j < dimention; j++) {
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
            if (quasigroup.get(i).contains(-1)) {
                nextRow = quasigroup.get(i);
                next = i;
                break;
            }
        }
        if (nextRow!=null) {
            ArrayList<Integer> others = getOthers(next, quasigroup);
            Possible possible = new Possible();
            ArrayList<String> permutations = possible.getPossibles(others);
            for (String perm : permutations) {
                ArrayList<ArrayList<Integer>> tempQuasi = getTempQuasi(quasigroup);
                //if (possiblePerm(next, tempQuasi, perm))
            }

        }
    }

    private boolean possiblePerm(int next, ArrayList<ArrayList<Integer>> tempQuasi, String perm) {
        /*int val = 0;
        StringBuilder sb = new StringBuilder(perm);

        for (int i = 0; i < tempQuasi.size(); i++) {
            if (tempQuasi.get(next).get(i) == 0) {
                //int c = Integer.parseInt()
            }
        }*/
        return false;
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



}
