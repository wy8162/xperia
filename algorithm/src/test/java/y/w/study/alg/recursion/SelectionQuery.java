package y.w.study.alg.recursion;

import java.util.*;
import org.junit.Test;

public class SelectionQuery {

    /**
     * Only works for 3 elements.
     *
     * @param arr
     * @param withDups - true to select duplicate entries
     * @return
     */
    public static List<String> selection3BruteForce(String[] arr, boolean withDups) {
        List<String> res = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (!withDups && i == j) continue;
                for (int k = 0; k < arr.length; k++) {
                    if(!withDups && (k ==i || k == j)) continue;
                    res.add(String.format("%s,%s,%s", arr[i], arr[j], arr[k]));
                }
            }
        }

        return res;
    }

    /**
     * Works when there are any number of elements.
     * Recursively generate permutations.
     */
    public void selectPermutations(String[] arr, boolean[] selections, Deque<String> permutation, List<String> res) {
        if (permutation.size() == arr.length) {
            res.add(String.join(",", permutation));
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            if (selections[i]) continue; // used already. Remove it to produce permutations with duplicates.
            selections[i] = true;
            permutation.addLast(arr[i]);
            selectPermutations(arr, selections, permutation, res);
            selections[i] = false;
            permutation.removeLast(); // Pop out the element arr[i]
        }
    }

    @Test
    public void test() {
        List<String> res;

        System.out.println("Witout dups:");
        res = selection3BruteForce(new String[]{"a", "b", "c"}, false);
        printAll(res);

        System.out.println("With dups:");
        res = selection3BruteForce(new String[]{"a", "b", "c"}, true);
        printAll(res);
    }

    @Test
    public void test2() {
        List<String> res = new ArrayList<>();

        String[] arr = new String[]{"a", "b", "c"};
        boolean[] selections = new boolean[arr.length];
        Arrays.fill(selections, false);

        selectPermutations(arr, selections, new ArrayDeque<>(), res);

        printAll(res);

        res = new ArrayList<>();
        arr = new String[]{"a", "b", "c", "d"};
        selections = new boolean[arr.length];
        Arrays.fill(selections, false);

        selectPermutations(arr, selections, new ArrayDeque<>(), res);

        printAll(res);
    }

    private static void printAll(List<String> list) {
        for (String s : list) System.out.println(s);
    }

}
