import java.util.ArrayList;

public class Possible {
    private ArrayList<String> permutations = new ArrayList<>();

    public ArrayList<String> getPermutations() {
        return permutations;
    }

    public void getPossibles(ArrayList<Integer> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer i: list) {
            stringBuilder.append(i);
        }
        getPermutation(stringBuilder.toString(), 0, stringBuilder.length()-1);
    }
    private void getPermutation(String str, int l, int r) {
        String temp = str;
        if (l == r)
        {
            permutations.add(temp);
            //System.out.println(str);
        }
        else {
            for (int i = l; i <= r; i++) {
                temp = swap(temp, l, i);
                getPermutation(temp, l + 1, r);
                temp = swap(temp, l, i);
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
