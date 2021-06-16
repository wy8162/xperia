package y.w.study.alg.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

public class Permutation {
    @Test
    public void test() {
        // Generate a random permutation
        List<Integer> list = Arrays.asList(1,2,3,4);
        Collections.shuffle(list);
        printArray(list);

        System.out.println("Using Heap Permutation:");
        for (List<Integer> l : heapGenerate(new int[]{1,2,3}))
            printArray(l);

        System.out.println("\nLexicographical order by nextPermutation");
        printArray(List.of(1,2,3));
        int[] nums = new int[]{1,2,3};
        printArray(nextPermutation(nums));
        printArray(nextPermutation(nums));
        printArray(nextPermutation(nums));
        printArray(nextPermutation(nums));

        System.out.println("\nUsing backtrack...");
        nums = new int[]{1,2,3};
        printArrayAll(permute(nums));

        printArrayAll(permute(new int[]{1,1,5}));
    }

    /**
     * Leetcode
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, result, 0);
        return result;
    }

    private void backtrack(int[] nums, List<List<Integer>> result, int index) {
        if (index == nums.length) {
            ArrayList<Integer> list = new ArrayList<>();

            for (int i = 0; i < nums.length; i++) {
                list.add(nums[i]);
            }
            result.add(list);
            return;
        } else {
            for (int i = index; i < nums.length; i++) {
                swap(nums, i, index);
                backtrack(nums, result, index+1);
                swap(nums, i, index);
            }
        }
    }


    /**
     * https://en.wikipedia.org/wiki/Heap%27s_algorithm
     *
     * @return
     */
    public List<List<Integer>> heapGenerate(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();

        if (nums == null || nums.length <= 0) return results;

        int[] indexes = new int[nums.length]; // initialized to zero by default.

        results.add(Arrays.stream(nums).boxed().collect(Collectors.toList()));

        int i = 0;
        while (i < nums.length) {
            if (indexes[i] < i) {
                swap(nums, i % 2 == 0 ? 0 : indexes[i], i);

                results.add(Arrays.stream(nums).boxed().collect(Collectors.toList()));

                indexes[i]++;
                i = 0;
            } else {
                indexes[i] = 0;
                i++;
            }
        }

        return results;
    }


    /**
     * Returns the same array for convenience.
     *
     * The following algorithm generates the next permutation lexicographically after a given permutation.
     * It changes the given permutation in-place.
     *
     * 1. Find the largest index k such that a[k] < a[k + 1]. If no such index exists,
     *    the permutation is the last permutation.
     * 2. Find the largest index l greater than k such that a[k] < a[l].
     * 3. Swap the value of a[k] with that of a[l].
     * 4. Reverse the sequence from a[k + 1] up to and including the final element a[n].
     *
     * @param nums
     * @return
     */
    public List<Integer> nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while (i >= 0 && nums[i + 1] <= nums[i]) {
            i--;
        }
        if (i >= 0) {
            int j = nums.length - 1;
            while (nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i + 1);

        return Arrays.stream(nums).boxed().collect(Collectors.toList());
    }

    private void reverse(int[] nums, int start) {
        int i = start, j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
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
