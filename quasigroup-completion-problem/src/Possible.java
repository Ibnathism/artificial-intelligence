import java.util.ArrayList;

public class Possible {
    private ArrayList<ArrayList<Integer>> permutations = new ArrayList<>();


    public ArrayList<ArrayList<Integer>> getPermutations() {
        return permutations;
    }

    public void countAllPossible(ArrayList<Integer> list, int l, int r) {
        ArrayList<Integer> temp = list;
        if (l == r)
        {
            permutations.add(temp);
            //System.out.println(str);
        }
        else {
            for (int i = l; i <= r; i++) {
                temp = swap(temp, l, i);
                countAllPossible(temp, l + 1, r);
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


}
