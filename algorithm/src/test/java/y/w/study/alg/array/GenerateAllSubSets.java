package y.w.study.alg.array;

import java.util.*;
import java.util.stream.*;
import java.io.*;

public class GenerateAllSubSets {
    /**
     * Prints all the subsets of an array.
     * I.e., [1,2,3]
     * {}, {1}, {2}, {3}, {1,2}, {1,3}, {2,3}, {1,2,3}
     */
    List<List<Integer>> subsetsOfNumbers(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Deque<Integer> memo = new ArrayDeque<>();

        numHelper(nums, 0, result, memo);    

        return result;
    }

    private void numHelper(int[] nums, int index, List<List<Integer>> result, Deque<Integer> memo) {
        if (index == nums.length) {
            result.add(new ArrayList<>(memo));
            return;
        }

        memo.addLast(nums[index]);
        numHelper(nums, index + 1, result, memo);
        //memo.removeLast();
        numHelper(nums, index + 1, result, memo);
    }

    public static void main(String[] args) {
        GenerateAllSubSets g = new GenerateAllSubSets();

        List<List<Integer>> res = g.subsetsOfNumbers(new int[]{1,2,3});

        for (List<Integer> l : res)
            System.out.println(String.format("{%s}", l.stream().map(i -> i.toString()).collect(Collectors.joining(","))));
    }
}
