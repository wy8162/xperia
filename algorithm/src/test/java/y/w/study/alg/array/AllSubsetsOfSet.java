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
    public void test() {
        printArrayAll(subsets(new int[]{1,2,3, 4}));
    }

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        LinkedList<Integer> cache = new LinkedList<>();

        generateAllSubsets(nums, 0, result, cache);

        return result;
    }

    private void generateAllSubsets(int[] nums, int start, List<List<Integer>> result, LinkedList<Integer> cache) {
        if (start >= nums.length) {
            List<Integer> temp = new ArrayList<>();
            temp.addAll(cache);

            result.add(temp);
        } else {
            cache.addLast(nums[start]);
            generateAllSubsets(nums, start + 1, result, cache);
            cache.removeLast();
            // Don't include nums[start]
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
