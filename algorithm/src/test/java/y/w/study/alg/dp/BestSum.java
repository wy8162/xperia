package y.w.study.alg.dp;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class BestSum {
    @Test
    public void test() {
        int[] res;
        res = bestSum(7, new int[]{5,3,4,7}, new HashMap<>());
        assertTrue(Arrays.compare(res, new int[]{7}) == 0);
        res = bestSum(8, new int[]{2,3,5}, new HashMap<>());
        Arrays.sort(res);
        assertTrue(Arrays.compare(res, new int[]{3,5}) == 0);
        res = bestSum(8, new int[]{1,4,5}, new HashMap<>());
        assertTrue(Arrays.compare(res, new int[]{4,4}) == 0);
        res = bestSum(100, new int[]{1,2,5,25}, new HashMap<>());
        assertTrue(Arrays.compare(res, new int[]{25,25,25,25}) == 0);
    }

    /**
     * m = target sum, n = array length
     * Frute Force: time O(n^m * m), space O(m^2)
     * Memoized: time O(n*m^2), space O(m^2)
     *
     * @param targetSum
     * @param nums
     * @param memo
     * @return
     */
    public int[] bestSum(int targetSum, int[] nums, Map<Integer, int[]> memo) {
        if (memo.containsKey(targetSum)) return memo.get(targetSum);

        if (nums == null || nums.length == 0) return null;
        if (targetSum == 0) return new int[]{};
        if (targetSum < 0) return null;

        int[] shortest = null;

        for (int n : nums) {
            int[] res = bestSum(targetSum - n, nums, memo);

            if (res != null) {
                int last = res.length;
                res = Arrays.copyOf(res, res.length + 1);
                res[last] = n;

                if (shortest == null || shortest.length > res.length) {
                    shortest = res;
                }
            }
        }

        memo.put(targetSum, shortest);
        return shortest;
    }
}
