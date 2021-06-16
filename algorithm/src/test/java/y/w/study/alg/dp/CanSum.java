package y.w.study.alg.dp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class CanSum {
    @Test
    public void test() {
        assertTrue(canSum(7, new int[]{2, 3}, new HashMap<>()));
        assertTrue(canSum(7, new int[]{5,3,4,7}, new HashMap<>()));
        assertFalse(canSum(7, new int[]{2,4}, new HashMap<>()));
        assertTrue(canSum(8, new int[]{2,3,5}, new HashMap<>()));
        assertFalse(canSum(300, new int[]{7, 14}, new HashMap<>()));
    }

    /**
     * Frute Force: time O(n**m), space O(m)
     * Memoized: time O(n*m), space O(m)
     *
     * @param targetSum
     * @param nums
     * @param memo
     * @return
     */
    public boolean canSum(int targetSum, int[] nums, Map<Integer, Boolean> memo) {
        if (memo.containsKey(targetSum)) return memo.get(targetSum);

        if (nums == null || nums.length == 0) return false;
        if (targetSum == 0) return true;
        if (targetSum < 0) return false;

        for (int n : nums) {
            int newSum = targetSum - n;
            boolean res = canSum(newSum, nums, memo);

            memo.put(newSum, res);

            if (res) return true; // stop early
        }

        return false;
    }
}
