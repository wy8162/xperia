package y.w.study.alg.array;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Given an array of n numbers, calculate the maximum subarrays sum,
 * the largest possible sum of a sequence of consecutive values in the array.
 */
public class MaximumSubarraySum {
    @Test
    public void test() {
        assertEquals(7, maxSubarraySumBruteForce(new int[]{-2, -3, 4, -1, -2, 1, 5, -3})); // [4, -1, -2, 1, 5]
        assertEquals(7, maxSubarraySumBruteForce(new int[]{-2,-5,6,-2,-3,1,5,-6})); // [6, -2,-3, `, 5]
        assertEquals(6, maxSubarraySumBruteForce(new int[]{1,2,3})); // [4, -1, -2, 1, 5]

        assertEquals(7, maxSubarraySumLinear(new int[]{-2, -3, 4, -1, -2, 1, 5, -3})); // [4, -1, -2, 1, 5]
        assertEquals(7, maxSubarraySumLinear(new int[]{-2,-5,6,-2,-3,1,5,-6})); // [6, -2,-3, `, 5]
        assertEquals(6, maxSubarraySumLinear(new int[]{1,2,3})); // [4, -1, -2, 1, 5]

        assertEquals(7, maxSubarraySumNSquared(new int[]{-2, -3, 4, -1, -2, 1, 5, -3})); // [4, -1, -2, 1, 5]
        assertEquals(7, maxSubarraySumNSquared(new int[]{-2,-5,6,-2,-3,1,5,-6})); // [6, -2,-3, `, 5]
        assertEquals(6, maxSubarraySumNSquared(new int[]{1,2,3})); // [4, -1, -2, 1, 5]
    }

    /**
     * Time: O(n^3)
     *
     * @param nums
     * @return
     */
    public int maxSubarraySumBruteForce(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                int sum = 0;
                for (int k = i; k <= j; k++)
                    sum += nums[k];

                max = Math.max(max, sum);
            }
        }

        return max;
    }

    /**
     * Time: O(n^2)
     *
     * Calculate the sum at the time when the right end of the subarray moves.
     */
    public int maxSubarraySumNSquared(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                max = Math.max(max, sum);
            }
        }

        return max;
    }

    /**
     * Time: O(n)
     *
     * 1. the subarray only contains the element at position i.
     * 2. The subarray consists of a subarray that ends at position i - 1, followed
     *    by the element at position k.
     */
    public int maxSubarraySumLinear(int[] nums) {
        int max = Integer.MIN_VALUE, sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum = Math.max(nums[i], sum + nums[i]);
            max = Math.max(max, sum);
        }

        return max;
    }
}
