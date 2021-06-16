package y.w.study.alg.array;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import org.junit.Test;

/**
 * Given an array of integers nums and an integer k, return the total number
 * of continuous subarrays whose sum equals to k.
 */
public class SubarraySumEqualsK {
    @Test
    public void test() {
        assertEquals(3, subarraySumLeetcode3(new int[]{1,-1,0}, 0));
        assertEquals(1, subarraySumLeetcode3(new int[]{1}, 1));
        assertEquals(4, subarraySumLeetcode3(new int[]{1,2,1,2,1}, 3));
        assertEquals(2, subarraySumLeetcode3(new int[]{1,1,1}, 2));
        assertEquals(2, subarraySumLeetcode3(new int[]{1,2,3}, 3));

        assertEquals(3, subarraySumLeetcode2(new int[]{1,-1,0}, 0));
        assertEquals(1, subarraySumLeetcode2(new int[]{1}, 1));
        assertEquals(4, subarraySumLeetcode2(new int[]{1,2,1,2,1}, 3));
        assertEquals(2, subarraySumLeetcode2(new int[]{1,1,1}, 2));
        assertEquals(2, subarraySumLeetcode2(new int[]{1,2,3}, 3));

        assertEquals(3, subarraySum(new int[]{1,-1,0}, 0));
        assertEquals(1, subarraySum(new int[]{1}, 1));
        assertEquals(4, subarraySum(new int[]{1,2,1,2,1}, 3));
        assertEquals(2, subarraySum(new int[]{1,1,1}, 2));
        assertEquals(2, subarraySum(new int[]{1,2,3}, 3));

        assertEquals(3, subarraySumLeetCode(new int[]{1,-1,0}, 0));
        assertEquals(1, subarraySumLeetCode(new int[]{1}, 1));
        assertEquals(4, subarraySumLeetCode(new int[]{1,2,1,2,1}, 3));
        assertEquals(2, subarraySumLeetCode(new int[]{1,1,1}, 2));
        assertEquals(2, subarraySumLeetCode(new int[]{1,2,3}, 3));
    }

    /**
     * Time: O(n^3)
     * Space: O(1)
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySumLeetCode  (int[] nums, int k) {
        int count = 0;
        for (int start = 0; start < nums.length; start++) {
            for (int end = start + 1; end <= nums.length; end++) {
                int sum = 0;
                for (int i = start; i < end; i++)
                    sum += nums[i];
                if (sum == k)
                    count++;
            }
        }
        return count;
    }

    /**
     * My implementation.
     *
     * Time: O(n^2)
     * Space: O(1)
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum(int[] nums, int k) {
        if (nums == null) return 0;

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

    public int subarraySumLeetcode2(int[] nums, int k) {
        int count = 0;
        int[] sum = new int[nums.length + 1];
        sum[0] = 0;
        for (int i = 1; i <= nums.length; i++)
            sum[i] = sum[i - 1] + nums[i - 1];
        for (int start = 0; start < nums.length; start++) {
            for (int end = start + 1; end <= nums.length; end++) {
                if (sum[end] - sum[start] == k)
                    count++;
            }
        }
        return count;
    }

    /**
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySumLeetcode3(int[] nums, int k) {
        int count = 0, sum = 0;
        HashMap< Integer, Integer > map = new HashMap < > ();
        map.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k))
                count += map.get(sum - k);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }


    private class Tuple {
        public final int i;
        public final int j;

        public Tuple(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }
}
