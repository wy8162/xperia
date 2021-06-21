package y.w.study.alg.array;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Given an array of integers nums and an integer k, return the total number
 * of continuous subarrays whose sum equals to k.
 */
public class SubarraySumEqualsK2 {
    @Test
    public void test() {
        assertEquals(3, subarraySumUsingCumulativeSum(new int[]{1,-1,0}, 0));
        assertEquals(1, subarraySumUsingCumulativeSum(new int[]{1}, 1));
        assertEquals(4, subarraySumUsingCumulativeSum(new int[]{1,2,1,2,1}, 3));
        assertEquals(2, subarraySumUsingCumulativeSum(new int[]{1,1,1}, 2));
        assertEquals(2, subarraySumUsingCumulativeSum(new int[]{1,2,3}, 3));
    }


    /**
     * Re-do based on my understanding of other people's implementation.
     *
     * Time: O(n^2)
     * Space: O(1)
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySumUsingCumulativeSum(int[] nums, int k) {
        if (nums == null) return 0;

        int[] sums = new int[nums.length + 1];
        sums[0] = 0;

        int sum = 0;
        for (int i = 1; i <= nums.length; i++) {
            sums[i] = sum + nums[i - 1];
            sum = sums[i];
        }

        int count = 0;

        for (int i = 1; i <= nums.length; i++) {
            for (int j = i; j <= nums.length; j++) {
                if (sums[j] - sums[i - 1] == k)
                    count++;
            }
        }

        return count;
    }
}
