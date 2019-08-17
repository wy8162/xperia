package y.w.dynamicprogramming;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Introduction to Algorithms, Dynamic Programming.
 *
 * We say that the rod-cutting problem exhibits optimal substructure: optimal
 * solutions to a problem incorporate optimal solutions to related subproblems,
 * which we may solve INDEPENDENTLY.
 *
 * Cutting rod in 2**(n−1) ways since each inch can have a cut or no cut.
 *
 * The maximum revenue to cut a rod of n inches can be fomulated as below:
 *
 * R(n) = max(P(i) + R(n-i)) where 1<=i<=n
 *
 * The problem can be resolved by sub-problems resolved independently.
 *
 * Or, the max value of the following:
 *
 * P(1) + R(n-1)
 * P(2) + R(n-2)
 * P(3) + R(n-3)
 * P(4) + R(n-4)
 * ...
 * P(n) + R(n-n) where R(n-n), or R(0) is 0.
 *
 * For each of the above scenarios, 1, 2, ..., n, we resolve them recursively.
 *
 * I.e., for P(1) + R(n-1)
 *  R(n-1) = max(P(i) + R(n-1-i)) where 1<=i<=n-1
 *
 * I.e., for P(2) + R(n-2)
 *  R(n-2) = max(P(i) + R(n-2-i)) where 1<=i<=n-2
 *
 * Obviously, some values will be calculated repeatedly.
 *
 * @author ywang
 */

public class RodCuttingTest
{
    // Index 0 is not used. For convenience purpose.
    private final int[] prices = {0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30};

    private final int[] prices2 =
            {
                    0,
                    1,  5,  8,  9,  10, 17, 17, 20, 24, 30,
                    31, 35, 38, 39, 40, 47, 47, 52, 54, 60,
                    41, 45, 48, 49, 50, 57, 57, 62, 64, 70,
                    51, 55, 58, 59, 60, 67, 67, 72, 74, 80,
            };

    @Test
    public void calculateOptimalSolutionsTest()
    {
        assertEquals(1,  cutRod(prices2, 1));
        assertEquals(5,  cutRod(prices2, 2));
        assertEquals(8,  cutRod(prices2, 3));
        assertEquals(10, cutRod(prices2, 4));
        assertEquals(13, cutRod(prices2, 5));
        assertEquals(17, cutRod(prices2, 6));
        assertEquals(18, cutRod(prices2, 7));
        assertEquals(22, cutRod(prices2, 8));
        assertEquals(25, cutRod(prices2, 9));
        assertEquals(30, cutRod(prices2, 10));
    }

    @Test
    public void calculateOptimalSolutionsLargePricesMemoizedTest()
    {
        assertEquals(1,  (int)memoizedCutRod(prices, 1).maxRevenue);
        assertEquals(5,  (int)memoizedCutRod(prices, 2).maxRevenue);
        assertEquals(8,  (int)memoizedCutRod(prices, 3).maxRevenue);
        assertEquals(10, (int)memoizedCutRod(prices, 4).maxRevenue);
        assertEquals(13, (int)memoizedCutRod(prices, 5).maxRevenue);
        assertEquals(17, (int)memoizedCutRod(prices, 6).maxRevenue);
        assertEquals(18, (int)memoizedCutRod(prices, 7).maxRevenue);
        assertEquals(22, (int)memoizedCutRod(prices, 8).maxRevenue);
        assertEquals(25, (int)memoizedCutRod(prices, 9).maxRevenue);
        assertEquals(30, (int)memoizedCutRod(prices, 10).maxRevenue);
    }

    @Test
    public void calculateOptimalSolutionsLargePricesBottomUpTest()
    {
        assertEquals(1,  bottomUpCutRod(prices, 1));
        assertEquals(5,  bottomUpCutRod(prices, 2));
        assertEquals(8,  bottomUpCutRod(prices, 3));
        assertEquals(10, bottomUpCutRod(prices, 4));
        assertEquals(13, bottomUpCutRod(prices, 5));
        assertEquals(17, bottomUpCutRod(prices, 6));
        assertEquals(18, bottomUpCutRod(prices, 7));
        assertEquals(22, bottomUpCutRod(prices, 8));
        assertEquals(25, bottomUpCutRod(prices, 9));
        assertEquals(30, bottomUpCutRod(prices, 10));
    }

    @Test
    public void memoizedVsBottomUpCutRodTest()
    {
        long startTime = System.nanoTime();
        Integer price1 = memoizedCutRod(prices2, 40).maxRevenue; // Rod of length 40 inches.
        long time1 = System.nanoTime() - startTime; //  1_000_000_000.0;

        startTime = System.nanoTime();
        Integer price2 = bottomUpCutRod(prices2, 40); // Rod of length 40 inches.
        long time2 = System.nanoTime() - startTime; //  1_000_000_000.0;


        System.out.println(String.format("Time elapsed: memoized=%d bottomUp=%d", time1, time2));
        System.out.println("Best price: " + price1);

        assertEquals(price1, price2);
        assertTrue(time1 > time2);
    }

    /**
     * Scenarios from book Introduction to Algorithms
     */
    @Test
    public void scenariosTest()
    {
        assertThat(extendedBottomUpCutRod(prices, 1).cuts, is(Arrays.asList(1)));
        assertThat(extendedBottomUpCutRod(prices, 2).cuts, is(Arrays.asList(2)));
        assertThat(extendedBottomUpCutRod(prices, 3).cuts, is(Arrays.asList(3)));
        assertThat(extendedBottomUpCutRod(prices, 4).cuts, is(Arrays.asList(2, 2)));
        assertThat(extendedBottomUpCutRod(prices, 5).cuts, is(Arrays.asList(2, 3)));
        assertThat(extendedBottomUpCutRod(prices, 6).cuts, is(Arrays.asList(6)));
        assertThat(extendedBottomUpCutRod(prices, 7).cuts, is(Arrays.asList(1, 6)));
        assertThat(extendedBottomUpCutRod(prices, 8).cuts, is(Arrays.asList(2, 6)));
        assertThat(extendedBottomUpCutRod(prices, 9).cuts, is(Arrays.asList(3, 6)));
        assertThat(extendedBottomUpCutRod(prices, 10).cuts,is(Arrays.asList(10)));
    }

    @Test
    public void detailsTest()
    {
        Result result1 = extendedBottomUpCutRod(prices, 10); // Rod of length 40 inches.
        System.out.println(String.format("Bottom up: Optimal revenue: %d cuts=%s",
                result1.maxRevenue,
                StringUtils.join(result1.cuts.toArray(), ',')));

        Result result2 = memoizedCutRod(prices, 10); // Rod of length 40 inches.
        System.out.println(String.format("Top down:  Optimal revenue: %d cuts=%s",
                result2.maxRevenue,
                StringUtils.join(result2.cuts.toArray(), ',')));

        assertEquals(result1.maxRevenue, result2.maxRevenue);

        result1 = extendedBottomUpCutRod(prices, 7); // Rod of length 40 inches.
        System.out.println(String.format("Bottom up: Optimal revenue: %d cuts=%s",
                result1.maxRevenue,
                StringUtils.join(result1.cuts.toArray(), ',')));

        result2 = memoizedCutRod(prices, 7); // Rod of length 40 inches.
        System.out.println(String.format("Top down:  Optimal revenue: %d cuts=%s",
                result2.maxRevenue,
                StringUtils.join(result2.cuts.toArray(), ',')));

        assertEquals(result1.maxRevenue, result2.maxRevenue);
        assertThat(result1.cuts, is(result2.cuts));
    }

    int INFINITE_VALUE_MIN = 0x80000000; // the minimum negative number for integer.
    /**
     * Naive recursive top-down implementation - solves same problem repeatedly.
     *
     * O(2**n)
     *
     * @param p
     * @param n
     * @return
     */
    public int cutRod(int[] p, int n)
    {

        if (n == 0)
            return 0;

        int q = INFINITE_VALUE_MIN;
        for (int i = 1; i <= n; i++)
        {
            q = Math.max(q, p[i] + cutRod(p, n - i));
        }

        return q;
    }

    /**
     * Memoized implementation.
     *
     * An exponential-time solution may be transformed into a
     * polynomial-time solution. A dynamic-programming approach
     * runs in polynomial time when the number of distinct subproblems
     * involved is polynomial in the input size and we can solve each
     * such subproblem in polynomial time.
     *
     * theta(n**2)
     *
     * @param p
     * @param n
     * @return
     */
    public Result memoizedCutRod(int[] p, int n)
    {

        int[] r = new int[p.length];
        Arrays.fill(r, INFINITE_VALUE_MIN);

        int[] s = new int[p.length];

        int q = memoizedCutRodAux(p, n, r, s);

        List<Integer> cuts = new ArrayList<>();
        while (n > 0)
        {
            cuts.add(s[n]);
            n = n - s[n];
        }
        return new Result(q, cuts);
    }

    private int memoizedCutRodAux(int[] p, int n, int[] r, int[] s)
    {
        if (r[n] >= 0)
        {
            return r[n];
        }

        int q = INFINITE_VALUE_MIN;
        if (n == 0)
            q = 0;
        else
        {
            for (int i = 1; i <= n; i++)
            {
                int t = p[i] + memoizedCutRodAux(p, n - i, r, s);
                if (t > q)
                {
                    q = t;
                    s[n] = i;
                }
            }
        }
        r[n] = q;
        return q;
    }

    /**
     * For the bottom-up dynamic-programming approach, BOTTOM-UP-CUT-ROD
     * uses the natural ordering of the subproblems: a problem of size i is "smaller"
     * than a subproblem of size j if i < j.
     *
     * theta(n**2)
     *
     * Analysis:
     *
     * R(n) = max(P(i) + R(n-i)) where 1<=i<=n
     *
     * So:
     * R(i)     Maximum of
     * ----     ----------
     * R1       P1+R0
     * R2       P1+R1, P2+R0
     * R3       P1+R2, P2+R1, P3+R0
     * R4       P1+R3, P2+R2, P3+R1, P4+R0
     * ...
     *
     * How do we fill in table entry Rk:
     * - For each possible first cut (ie P1..Pk),
     * - Calculate the sum of the value of that cut (ie pi) and the best that could be done with the rest of the rod (ie rk−i).
     * - Choose the largest sum (Pi+Rk−i).
     * - Notice that each value of Ri depends only on values higher in the table
     *
     * @param p
     * @param n
     * @return
     */
    public int bottomUpCutRod(int[] p, int n)
    {

        int[] r = new int[p.length];

        r[0] = 0;

        for (int j = 1; j <= n; j++)
        {
            int q = INFINITE_VALUE_MIN;
            for (int i = 1; i <= j; i++)
            {
                q = Math.max(q, p[i] + r[j - i]);
            }
            r[j] = q;
        }

        return r[n];
    }

    /**
     * Returns more details.
     *
     * @param p list of prices
     * @param n length of the rod
     * @return elements of the cuts with the last element being the maximum cost.
     */
    public Result extendedBottomUpCutRod(int[] p, int n)
    {

        int[] r = new int[p.length];
        int[] s = new int[p.length];

        r[0] = 0;

        for (int j = 1; j <= n; j++)
        {
            int q = INFINITE_VALUE_MIN;
            for (int i = 1; i <= j; i++)
            {
                if (q < p[i] + r[j-i])
                {
                    q = p[i] + r[j-i];
                    s[j] = i;
                }
            }
            r[j] = q;
        }

        List<Integer> cuts = new ArrayList<>();

        int j = n;
        while (j > 0)
        {
            cuts.add(s[j]);
            j = j - s[j];
        }
        return new Result(r[n], cuts);
    }

    public class Result
    {
        public final Integer maxRevenue;
        public final List<Integer> cuts;

        public Result(Integer maxRevenue, List<Integer> cuts)
        {
            this.maxRevenue = maxRevenue;
            this.cuts = cuts;
        }
    }
}
