package y.w.study.alg.array;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import org.junit.Test;

/**
 * Given an array of integers nums and an integer k, return the total number of continuous subarrays
 * whose sum equals to k.
 */
public class SubarraySumEqualsK {

    @Test
    public void test() {
        assertEquals(3, subarraySumLeetcode3(new int[]{1, -1, 0}, 0));
        assertEquals(1, subarraySumLeetcode3(new int[]{1}, 1));
        assertEquals(4, subarraySumLeetcode3(new int[]{1, 2, 1, 2, 1}, 3));
        assertEquals(2, subarraySumLeetcode3(new int[]{1, 1, 1}, 2));
        assertEquals(2, subarraySumLeetcode3(new int[]{1, 2, 3}, 3));

        assertEquals(3, subarraySumLeetcode2(new int[]{1, -1, 0}, 0));
        assertEquals(1, subarraySumLeetcode2(new int[]{1}, 1));
        assertEquals(4, subarraySumLeetcode2(new int[]{1, 2, 1, 2, 1}, 3));
        assertEquals(2, subarraySumLeetcode2(new int[]{1, 1, 1}, 2));
        assertEquals(2, subarraySumLeetcode2(new int[]{1, 2, 3}, 3));

        assertEquals(3, subarraySum(new int[]{1, -1, 0}, 0));
        assertEquals(1, subarraySum(new int[]{1}, 1));
        assertEquals(4, subarraySum(new int[]{1, 2, 1, 2, 1}, 3));
        assertEquals(2, subarraySum(new int[]{1, 1, 1}, 2));
        assertEquals(2, subarraySum(new int[]{1, 2, 3}, 3));

        assertEquals(3, subarraySumLeetCode(new int[]{1, -1, 0}, 0));
        assertEquals(1, subarraySumLeetCode(new int[]{1}, 1));
        assertEquals(4, subarraySumLeetCode(new int[]{1, 2, 1, 2, 1}, 3));
        assertEquals(2, subarraySumLeetCode(new int[]{1, 1, 1}, 2));
        assertEquals(2, subarraySumLeetCode(new int[]{1, 2, 3}, 3));
    }

    /**
     * Time: O(n^3) Space: O(1)
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySumLeetCode(int[] nums, int k) {
        int count = 0;
        for (int start = 0; start < nums.length; start++) {
            for (int end = start + 1; end <= nums.length; end++) {
                int sum = 0;
                for (int i = start; i < end; i++) {
                    sum += nums[i];
                }
                if (sum == k) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * My implementation.
     * <p>
     * Time: O(n^2) Space: O(1)
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum(int[] nums, int k) {
        if (nums == null) {
            return 0;
        }

        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            int delta = k;

            int j = i;
            while (j < nums.length) {
                delta = delta - nums[j];
                if (delta == 0) {
                    count++;
                }
                j++;
            }
        }

        return count;
    }

    /**
     * From Leetcode.
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySumLeetcode2(int[] nums, int k) {
        int count = 0;
        int[] sums = new int[nums.length + 1];
        sums[0] = 0;

        /*
          Explanations:

          Accumulated sums:

          nums   0 1 2 3 4 5
          sums 0 1 2 3 4 5 6
          sums[0] = 0
          sums[1] = n0
          sums[2] = n0 + n1
          sums[3] = n0 + n1 + n2
          sums[4] = n0 + n1 + n2 + n3

          So:
          sums[1] - sums[0] = nums[0]
          sums[2] - sums[0] = nums[0..1]
          sums[3] - sums[0] = nums[0..2]

          sums[4] - sums[1] = nums[1..3]
          sums[n] - sums[m] = nums[m..n-1]
         */
        for (int i = 1; i <= nums.length; i++) {
            sums[i] = sums[i - 1] + nums[i - 1];
        }

        for (int start = 0; start < nums.length; start++) {
            for (int end = start + 1; end <= nums.length; end++) {
                if (sums[end] - sums[start] == k) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * From a book. Still couldn't understand why this works.
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySumLeetcode3(int[] nums, int k) {
        int count = 0, sum = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k)) {
                count += map.get(sum - k);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
}
