package y.w.study.alg.dp;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class HowSum {
    @Test
    public void test() {
        assertTrue(Arrays.compare(howSum(7, new int[]{2, 3}, new HashMap<>()), new int[]{3,2,2}) == 0);
        assertTrue(Arrays.compare(howSum(7, new int[]{5,3,4,7}, new HashMap<>()), new int[]{4,3}) == 0);
        assertNull(howSum(7, new int[]{2,4}, new HashMap<>()));
        assertTrue(Arrays.compare(howSum(8, new int[]{2,3,5}, new HashMap<>()), new int[]{2,2,2,2}) == 0);
        assertNull(howSum(300, new int[]{7, 14}, new HashMap<>()));
    }

    /**
     * m = target sum, n = array length
     * Frute Force: time O(n^m * m), space O(m)
     * Memoized: time O(n*m^2), space O(m^2)
     *
     * @param targetSum
     * @param nums
     * @param memo
     * @return
     */
    public int[] howSum(int targetSum, int[] nums, Map<Integer, int[]> memo) {
        if (memo.containsKey(targetSum)) return memo.get(targetSum);

        if (nums == null || nums.length == 0) return null;
        if (targetSum == 0) return new int[]{};
        if (targetSum < 0) return null;

        for (int n : nums) {
            int newSum = targetSum - n;
            int[] res = howSum(newSum, nums, memo);

            if (res != null) {
                int last = res.length;
                res = Arrays.copyOf(res, res.length + 1);
                res[last] = n;
            }

            memo.put(newSum, res);

            if (res != null) return res; // Stop early
        }

        return null;
    }
}
