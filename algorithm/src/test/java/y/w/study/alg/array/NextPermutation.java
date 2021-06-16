package y.w.study.alg.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

public class NextPermutation {

    /**
     * About permutations
     */
    @Test
    public void test() {
        System.out.println("\nPrint just the previous largest one but smaller than this one:");

        printArray(prevPermutation(new int[]{5, 3, 1}));

        System.out.println("\nThe ones immediately before 5 3 1\n");
        int[] r = prevPermutation(new int[]{5, 3, 1});
        printArray(r);
        r = prevPermutation(r);
        printArray(r);

        System.out.println("\n\n1,3,5-series...\n");
        int[] nums = new int[]{1,3,5};
        printArray(nums);
        printArray(nextPermutation(nums));
        printArray(nextPermutation(nums));
        printArray(nextPermutation(nums));
        printArray(nextPermutation(nums));
        printArray(nextPermutation(nums));

        System.out.println("\nStart the others...");
        // Generate a random permutation
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        Collections.shuffle(list);
        printArray(list);

        /**
         * 1 2 3
         * 2 1 3
         * 3 1 2
         * 1 3 2
         * 2 3 1
         * 3 2 1
         */
        for (List<Integer> l : heapGenerate(new int[]{1, 2, 3})) {
            printArray(l);
        }

        System.out.println("\n\n1,3,5-series...\n");
        for (List<Integer> l : heapGenerate(new int[]{1, 3, 5})) {
            printArray(l);
        }

        System.out.println("\nLexicographical order");

        nums = new int[]{1, 2, 3};
        printArray(nums);
        printArray(nextPermutation(nums));
        printArray(nextPermutation(nums));
        printArray(nextPermutation(nums));
        printArray(nextPermutation(nums));
        printArray(nextPermutation(nums));
        printArray(nextPermutation(nums));

        System.out.println("\nAnother one: 1 1 5 -> 1 5 1:");
        printArray(nextPermutation(new int[]{1, 1, 5}));

    }

    /**
     * https://en.wikipedia.org/wiki/Heap%27s_algorithm
     *
     * @return
     */
    private List<List<Integer>> heapGenerate(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();

        if (nums == null || nums.length <= 0) {
            return results;
        }

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
     * https://www.nayuki.io/page/next-lexicographical-permutation-algorithm
     *
     * Returns the same array for convenience.
     * <p>
     * The following algorithm generates the next permutation lexicographically after a given
     * permutation. It changes the given permutation in-place.
     * <p>
     * 1. Find the largest index k such that a[k] < a[k + 1]. If no such index exists, the
     * permutation is the last permutation. 2. Find the largest index l greater than k such that
     * a[k] < a[l]. 3. Swap the value of a[k] with that of a[l]. 4. Reverse the sequence from a[k +
     * 1] up to and including the final element a[n].
     *
     * @param array
     * @return
     */
    public int[] nextPermutation(int[] array) {
        // Find longest non-increasing suffix
        int i = array.length - 1;
        while (i > 0 && array[i - 1] >= array[i])
            i--;
        // Now i is the head index of the suffix

        // Are we at the last permutation already?
        if (i <= 0)
            return array;

        // Let array[i - 1] be the pivot
        // Find rightmost element greater than the pivot
        int j = array.length - 1;
        while (array[j] <= array[i - 1])
            j--;
        // Now the value array[j] will become the new pivot
        // Assertion: j >= i

        // Swap the pivot with j
        int temp = array[i - 1];
        array[i - 1] = array[j];
        array[j] = temp;

        // Reverse the suffix
        j = array.length - 1;
        while (i < j) {
            temp = array[i];
            array[i] = array[j];
            array[j] = temp;
            i++;
            j--;
        }

        // Successfully computed the next permutation
        return array;
    }

    /**
     * Find the largest permutation smaller than given array.
     * @param array
     * @return
     */
    public int[] prevPermutation(int[] array) {
        // Find longest non-decreasing suffix
        int i = array.length - 1;
        while (i > 0 && array[i - 1] <= array[i])
            i--;
        // Now i is the head index of the suffix

        // Are we at the first permutation already?
        if (i <= 0)
            return array;

        // Let array[i - 1] be the pivot
        // Find rightmost element less than the pivot
        int j = array.length - 1;
        while (array[j] >= array[i - 1])
            j--;
        // Now the value array[j] will become the new pivot
        // Assertion: j >= i

        // Swap the pivot with j
        int temp = array[i - 1];
        array[i - 1] = array[j];
        array[j] = temp;

        // Reverse the suffix
        j = array.length - 1;
        while (i < j) {
            temp = array[i];
            array[i] = array[j];
            array[j] = temp;
            i++;
            j--;
        }

        // Successfully computed the previous permutation
        return array;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void printArray(List<Integer> input) {
        System.out.print('\n');
        for (Integer i : input) {
            System.out.print(i + " ");
        }
    }

    private void printArray(int[] input) {
        System.out.print('\n');
        for (int i : input) {
            System.out.print(i + " ");
        }
    }
}
