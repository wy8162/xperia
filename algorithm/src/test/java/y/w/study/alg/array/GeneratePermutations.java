package y.w.stud.alg.array;

import java.util.*;
import java.util.stream.*;
import java.io.*;

/**
 * Generate permutations of a list of numbers.
 */
public class GeneratePermutations {
    public static List<List<Integer>> generate(int[] arr) {
        List<List<Integer>> res = new ArrayList<>();
        Deque<Integer> memo = new ArrayDeque<>();
        boolean[] chosen = new boolean[arr.length + 1];
        Arrays.fill(chosen, false);

        permutate(arr, res, chosen, memo);

        return res;
    }
    
    private static void permutate(int[] arr, List<List<Integer>> res, boolean[] chosen, Deque<Integer> memo) {
        if (memo.size() == arr.length) {
            res.add(new ArrayList<>(memo));
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            if (chosen[i]) continue;
            chosen[i] = true;
            memo.addLast(arr[i]);
            permutate(arr, res, chosen, memo);
            chosen[i] = false;
            memo.removeLast();
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> res = generate(new int[]{1,2,3});

        for (List<Integer> l : res)
            System.out.println(String.format("{%s}", l.stream().map(i -> i.toString()).collect(Collectors.joining(","))));
    }
}

