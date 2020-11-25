import java.util.ArrayList;

public class Possible {
    private ArrayList<String> permutations = new ArrayList<>();

    public ArrayList<String> getPermutations() {
        return permutations;
    }

    public ArrayList<String> getPossibles(ArrayList<Integer> list) {
        String str = list.toString();
        getPermutation(str, 0, str.length()-1);
        return permutations;
    }
    private void getPermutation(String str, int l, int r) {
        if (l == r)
        {
            permutations.add(str);
            System.out.println(str);
        }
        else {
            for (int i = l; i <= r; i++) {
                str = swap(str, l, i);
                getPermutation(str, l + 1, r);
                str = swap(str, l, i);
            }
        }
    }
    private String swap(String a, int i, int j) {
        char temp;
        char[] charArray = a.toCharArray();
        temp = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = temp;
        return String.valueOf(charArray);
    }
}
