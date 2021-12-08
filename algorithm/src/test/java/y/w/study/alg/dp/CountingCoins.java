package y.w.study.alg.dp;

/**
 * Given a set of coin values coins=[c1,c2,c3...cn], and a target sum of money m,
 * compute value of m using as few coins as possible.
 *
 * i.e.
 * coins=[1,2,5], m = 12 -> solution is 5+5+2
 * coins=[1,3,4], m = 6  -> solution is 3+3
 *
 * Optimal solution:
 *  if c1 is first chosen, then the next problem is to resolve m - c1 with [c2,c3...]
 *
 *  this forms a recursion approach.
 *
 * Algorithm:
 *  solve(x):
 *      if (x == 0) return 0 // base case
 *      if (x <  0) return INFINITE
 *      if (x >  0) min(solve(x -c) for each c of coins)
 */
public class CountingCoins {
    private static int INFINITE = 0x70000000;

    private static int solveRecursive(int[] coins, int sum) {
        if (sum == 0) return 0;
        if (sum <  0) return INFINITE;

        int best = INFINITE;
        for (int c : coins) {
            best = Math.min(best, solveRecursive(coins, sum - c) + 1);
        }

        return best;
    }

    /**
     * Time: O(|coins| * sum)
     *
     * @param coins
     * @param memo
     * @param sum
     * @return
     */
    private static int solveDPMemo(int[] coins, int[] memo, int sum) {
        if (sum == 0) return 0;
        if (sum <  0) return INFINITE;
        if (memo[sum - 1] > 0) return memo[sum - 1];

        int best = INFINITE;
        for (int c : coins) {
            best = Math.min(best, solveDPMemo(coins, memo, sum - c) + 1);
        }

        memo[sum - 1] = best;

        return best;
    }

    private static int solveDP(int[] coins, int sum) {
        int[] memo = new int[sum];

        for (int i = 0; i < sum; i++) memo[i] = -1;

        return solveDPMemo(coins, memo, sum);
    }

    private static int solveDPTable(int[] coins, int sum) {
        int[] value = new int[sum];

        value[0] = 0; // this is redundant because value is automatically initialized to 0.
        for (int i = 1; i < sum; i++) {
            // solve(i) = min (solve(i - c) for each c in coins)
            value[i] = INFINITE;

            // Calculate the optimal solution for sum i.
            for (int c : coins)
                // If this is the solution for i, then solution of i - c plus 1 is also a solution.
                // We then choose the minimum one.
                if (i - c >= 0) value[i] = Math.min(value[i], value[i - c] + 1);
        }

        return value[sum - 1];
    }

    public static void main(String[] args) {
        System.out.println("Expected 3 and get " + solveRecursive(new int[]{1,2,5}, 12));
        System.out.println("Expected 2 and get " + solveRecursive(new int[]{1,3,4}, 6));

        System.out.println("Expected 3 and get " + solveDP(new int[]{1,2,5}, 12));
        System.out.println("Expected 2 and get " + solveDP(new int[]{1,3,4}, 6));

        System.out.println("Expected 3 and get " + solveDPTable(new int[]{1,2,5}, 12));
        System.out.println("Expected 2 and get " + solveDPTable(new int[]{1,3,4}, 6));
    }
}
