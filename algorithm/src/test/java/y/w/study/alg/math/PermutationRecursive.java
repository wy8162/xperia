package y.w.study.alg.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

/**
 * https://introcs.cs.princeton.edu/java/23recursion/Permutations.java.html
 */
public class PermutationRecursive {
    @Test
    public void test() {
        System.out.println("Using recursion");
        int[] nums = new int[]{1,2,3};
        printArrayAll(permute(nums));

        printArrayAll(permute(new int[]{1,1,5}));

        System.out.println("Using Heap Generator recursively");
        nums = new int[]{1,2,3};
        printArrayAll(usingHeap(nums));
    }

    public List<List<Integer>> usingHeap(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        heapPermutationRecursive(nums, nums.length, result);

        return result;
    }

    private void heapPermutationRecursive(int[] nums, int size, List<List<Integer>> result) {
        if (size == 1) {
            result.add(Arrays.stream(nums).boxed().collect(Collectors.toList()));
        }

        for (int i = 0; i < size; i++) {
            heapPermutationRecursive(nums, size - 1, result);
            if (size % 2 > 0) swap(nums, 0, size - 1);
            else           swap(nums, i, size - 1);
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        generatePermutation(nums, 0, result);
        return result;
    }

    /**
     * Another way to generate permutations.
     *
     * @param nums
     * @param start
     * @param result
     */
    private void generatePermutation(int[] nums, int start, List<List<Integer>> result) {
        if (start == nums.length - 1) {
            result.add(Arrays.stream(nums).boxed().collect(Collectors.toList()));
            return;
        }

        for (int i = start; i < nums.length; i++) {
            swap(nums, i, start);
            generatePermutation(nums, start + 1, result);
            swap(nums, i, start);
        }
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
            if (selections[i]) continue; // used already
            selections[i] = true;
            permutation.addLast(arr[i]);
            selectPermutations(arr, selections, permutation, res);
            selections[i] = false;
            permutation.removeLast(); // Pop out the element arr[i]
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void printArray(List<Integer> input) {
        for(Integer i : input) {
            System.out.print(i + " ");
        }
        System.out.print('\n');
    }


    private void printArrayAll(List<List<Integer>> input) {
        for(List<Integer> i : input) {
            printArray(i);
        }
        System.out.print('\n');
    }
}
