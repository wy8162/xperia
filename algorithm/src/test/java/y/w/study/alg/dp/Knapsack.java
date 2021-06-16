package y.w.study.alg.dp;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Knapsack {
    @Test
    public void test() {
        assertTrue(solveKnapsack(new int[]{1,6,10,16}, new int[]{1,2,3,5}, 7, 0) == 22);
    }

    public int solveKnapsack(int[] profits, int[] weights, int capacity, int index) {
        // Edge checks
        if (capacity <= 0 || index >= profits.length)
            return 0;

        // Option 1: include profits[index]
        int profit1 = 0;
        if (weights[index] <= capacity)
            profit1 = profits[index] + solveKnapsack(profits, weights, capacity - weights[index], index + 1);

        // Option 2: exclude profits[index]
        int profit2 = solveKnapsack(profits, weights, capacity, index + 1);

        return Math.max(profit1, profit2);
    }
}
