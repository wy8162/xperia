package y.w.dynamicprogramming;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Source: https://www.techiedelight.com/calculate-minimum-cost-to-reach-destination-city-from-source-city/
 *
 * Calculate the minimum cost traveling from citiy 0 to n.
 *
 * { 0,   20, 30, 100 },
 * { 20,  0,  15, 75 },
 * { 30,  15, 0,  50 },
 * { 100, 75, 50, 0 }
 *
 * I.e.
 * city 0 -> city 2 (cost = $30)
 * city 0 -> city 3 (cost = $50)
 *
 * T(n) = minimum { T(i) + cost[i][n] }
 *        for all cities i between source city 0 and the destination
 *        city n.
 *
 * i.e. the minumum cost to reach city n from city 0 is
 *
 * T(n) = minimum(cost[0][n],
 *                T(1) + cost[1][n],
 *                T(2) + cost[2][n],
 *                ...
 *                T(n-1) + cost[n-1][n]
 *               )
 *
 *         .3      $85
 *        /
 *       /
 *     .1--.2--.3  $95
 *    /
 * 0 .-.2--.3      $80 - the minimum cost
 *    \
 *     \
 *      .3         $100
 *
 *
 * @author ywang
 * Date: 7/8/2019
 */
public class MinimumCostToReachCityTest
{

    private final int[][] cost =
        {
                { 0,   20, 30, 100 },
                { 20,  0,  15, 75 },
                { 30,  15, 0,  50 },
                { 100, 75, 50, 0 }
        };

    private final int[][] cost1 =
            {
                    { 0,   20, 30, 100, 70 },
                    { 20,  0,  15, 75,  50 },
                    { 30,  15, 0,  50,  45 },
                    { 100, 75, 50, 0,   30 },
                    { 70,  50, 45, 30,  0}
            };

    private final int[][] cost2 =
            {
                    { 0,   20, 40, 100 },
                    { 20,  0,  15, 75 },
                    { 30,  15, 0,  50 },
                    { 100, 75, 50, 0 }
            };

    @Test
    public void testTopDown()
    {
        int[] lookup = new int[5];

        int miniCost;

        miniCost = findMinCostRecurvisely(cost, 3, lookup);
        assertEquals(miniCost, 80);

        Arrays.fill(lookup, 0);
        miniCost = findMinCostRecurvisely(cost1, 4, lookup);
        assertEquals(miniCost, 70);

        Arrays.fill(lookup, 0);
        miniCost = findMinCostRecurvisely(cost2, 3, lookup);
        assertEquals(miniCost, 85);
    }

    @Test
    public void testDP_BottomUp()
    {
        int miniCost;

        miniCost= findMinCost_DP(cost, 4);
        assertEquals(miniCost, 80);

        miniCost= findMinCost_DP(cost1, 5);
        assertEquals(miniCost, 70);

        miniCost= findMinCost_DP(cost2, 4);
        assertEquals(miniCost, 85);;
    }


    /**
     * DP function to calculate the minimum cost to reach the destination city n
     * from the source city 0
     *
     * @param costs matrix of direct flight costs
     * @param N number of cities
     * @return
     */
    public int findMinCost_DP(int[][] costs, int N)
    {
        // lookup[i] stores the minimum cost to reach city i from city 0
        int[] lookup = new int[N];

        // Initialize lookup[] with the direct ticket price from the source city
        for (int i = 0; i < N; i++)
        {
            lookup[i] = costs[0][i];
        }

        // fill lookup[] in bottom-up manner
        for (int i = 2; i < N; i++)
        {
            for (int j = 1; j < i; j++)
            {
                lookup[i] = Math.min(lookup[i], lookup[j] + costs[j][i]);
            }
        }

        // return the minimum cost to reach city N-1 from city 0
        return lookup[N - 1];
    }

    /**
     * T(n) = minimum { T(i) + cost[i][n] }
     *        for all cities i between source city 0 and the destination
     *        city n.
     *
     * Calculate the minimum cost from city 0 to city n (the initial value passed).
     *
     * @param costs matrix of direct flight costs
     * @param n destination city
     * @param lookup memoization
     * @return
     */
    public int findMinCostRecurvisely(int[][] costs, int n, int[] lookup)
    {
        // if the sub-problem is seen before
        if (lookup[n] != 0) {
            return lookup[n];
        }

        // Initialize minimum cost with the price of the non-stop flight ticket
        // from source to destination
        int min_cost = costs[0][n];

        // consider each of the intermediate cities between city 0 and n
        // and take the minimum cost
        for (int i = 1; i < n; i++)
        {
            // The cost of the current route is sum of:
            // 1. minimum cost to reach city i from city 0
            // 2. cost to reach city n from city i using a direct flight
            min_cost = Math.min(min_cost, findMinCostRecurvisely(costs, i, lookup) + costs[i][n]);
        }

        // save sub-problem solution and return the minimum cost
        return (lookup[n] = min_cost);
    }
}
