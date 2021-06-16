package y.w.study.alg.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

/**
 * Given an array of integers nums and an integer target, return indices of the
 * two numbers such that they add up to target. You may assume that each input would
 * have exactly one solution, and you may not use the same element twice.
 *
 * You can return the answer in any order.
 *
 * Example 1:
 *
 * Input: nums = [2,7,11,15], target = 9
 * Output: [0,1]
 * Output: Because nums[0] + nums[1] == 9, we return [0, 1].
 * Example 2:
 *
 * Input: nums = [3,2,4], target = 6
 * Output: [1,2]
 * Example 3:
 *
 * Input: nums = [3,3], target = 6
 * Output: [0,1]
 */
public class TwoSum {
    @Test
    public void bruteTest() {
        Optional<Pair<Integer, Integer>> result;

        result = brute(new int[]{2,7,11,15}, 9);
        assert result.get().compareTo(Pair.of(0, 1)) == 0;

        result = brute(new int[]{3,2,4}, 6);
        assert result.get().compareTo(Pair.of(1, 2)) == 0;

        result = brute(new int[]{3, 3}, 6);
        assert result.get().compareTo(Pair.of(0, 1)) == 0;

        result = brute(new int[]{3, 4}, 6);
        assert result.isEmpty();
    }

    @Test
    public void usingMapTest() {
        Optional<Pair<Integer, Integer>> result;

        result = usingMap(new int[]{2,7,11,15}, 9);
        assert result.get().compareTo(Pair.of(0, 1)) == 0;

        result = usingMap(new int[]{3,2,4}, 6);
        assert result.get().compareTo(Pair.of(1, 2)) == 0;

        result = usingMap(new int[]{3, 3}, 6);
        assert result.get().compareTo(Pair.of(0, 1)) == 0;

        result = usingMap(new int[]{3, 4}, 6);
        assert result.isEmpty();
    }

    /**
     * O(n*n)
     *
     * @param nums
     * @param target
     * @return
     */
    private Optional<Pair<Integer, Integer>> brute(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            int delta = target - nums[i];
            for (int j = i + 1; j < nums.length; j++)
                if (nums[j] == delta) return Optional.of(Pair.of(i, j));
        }

        return Optional.empty();
    }

    /**
     * Trade space for speed.
     *
     * @param nums
     * @param target
     * @return
     */
    private Optional<Pair<Integer, Integer>> usingMap(int[] nums, int target) {
        Map<Integer, Integer> tracking = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int delta = target - nums[i];

            if (tracking.containsKey(delta)) {
                return Optional.of(Pair.of(tracking.get(delta), i));
            } else {
                tracking.put(nums[i], i);
            }
        }

        return Optional.empty();
    }

    @Test
    public void testTwoSum() {
        List<List<Integer>> result;

        result = twoSum(9, new int[]{2,7,11,15});
        result = twoSum(6, new int[]{2,3,4});
        result = twoSum(9, new int[]{2,3,4});
        result = twoSum(6, new int[]{2,3,4,3,1,5});
    }

    /**
     * Time: c1 + c2 + c3 * O(n + c4 + c5) -> O(n)
     * Space: s1 + s2 + s3 + s4 * O(n) -> O(n)
     *
     * @param target
     * @param nums
     * @return
     */
    public List<List<Integer>> twoSum(int target, int[] nums) {
        Map<Integer, Integer> tracking = new HashMap<>(); // Time: c1 = O(1), Space: s1 = O(1)

        List<List<Integer>> results = new ArrayList<>();  // Time: c2 = O(1), Space: s2 = O(1)

        for (int i = 0; i < nums.length; i++) { // Time: c3 * O(n), Space: s3 = O(1)
            int delta = target - nums[i];       // Time: c4 = O(1)

            if (tracking.containsKey(delta)) {  // Time: c5 = O(1), Space: s4 = O(2)
                List<Integer> temp = Arrays.asList(nums[tracking.get(delta)], nums[i]);
                results.add(temp);
            } else {
                tracking.put(nums[i], i);
            }
        }

        return results;
    }
}
