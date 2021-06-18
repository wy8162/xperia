package y.w.study.alg.array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

/**
 * Generating all subsets of a set of n elements. For example,
 * the subsets of {1,2,3} includes {}, {1},{2},{3},{1,2},{1,3},
 * {2,3},{1,2,3}
 */
public class AllSubsetsOfSet {
    @Test
    public void testCountingBits() {
        printArrayAll(generateAllSubsetsNew(new int[]{1,2,3}));
        printArrayAll(generateAllSubsetsNew(new int[]{1,2,3,4}));
    }

    /**
     * Based on
     * - http://en.wikipedia.org/wiki/Binary_numeral_system#Counting_in_binary
     * - http://compprog.wordpress.com/2007/10/10/generating-subsets/
     * - https://stackoverflow.com/questions/10869866/generating-all-subsets-from-a-single-set
     *
     * Time: O(2^|nums| * |nums|)
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> generateAllSubsetsNew(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();

        int total = 1 << nums.length; // Total number of subsets: 2^|nums|

        /*
         *  You may or may not take the first element a.
         *  May or may not take the 2nd element b.
         *  Same for c.
         *
         * The mask shows this: 010 -> take second element only; 111 -> take all of the numbers.
         */
        for (int i = 0; i < total; i++) {
            String mask = Integer.toBinaryString(i);

            List<Integer> r = new ArrayList<>();

            for (int k = 0; k < mask.length(); k++) {
                if (mask.charAt(mask.length() - 1 - k) == '1')
                     r.add(nums[k]);
            }

            res.add(r);
        }

        return res;
    }


    @Test
    public void test() {
        printArrayAll(subsets(new int[]{1,2,3,4}));
    }


    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        LinkedList<Integer> cache = new LinkedList<>();

        generateAllSubsets(nums, 0, result, cache);

        return result;
    }

    /**
     * Backtrack
     *
     * Time: O(2^|nums|)
     *
     * @param nums
     * @param start
     * @param result
     * @param cache
     */
    private void generateAllSubsets(int[] nums, int start, List<List<Integer>> result, LinkedList<Integer> cache) {
        if (start >= nums.length) {
            List<Integer> temp = new ArrayList<>();
            temp.addAll(cache);

            result.add(temp);
        } else {
            cache.addLast(nums[start]);
            generateAllSubsets(nums, start + 1, result, cache);

            // Backtrack one - to exclude mums[start]
            cache.removeLast();
            generateAllSubsets(nums, start + 1, result, cache);
        }
    }

    private void printArray(List<Integer> input) {
        System.out.print("{");
        for(Integer i : input) {
            System.out.print(i + " ");
        }
        System.out.print("}\n");
    }

    private void printArrayAll(List<List<Integer>> input) {
        Collections.reverse(input);
        for(List<Integer> i : input) {
            printArray(i);
        }
        System.out.print('\n');
    }
}
