package y.w.study.alg.dp;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GridTraveler {
    @Test
    public void test() {
        assertTrue(travel(0,0, new long[20][20]) == 0);
        assertTrue(travel(0,1, new long[20][20]) == 0);
        assertTrue(travel(1,0, new long[20][20]) == 0);
        assertTrue(travel(2,3, new long[20][20]) == 3);
        assertTrue(travel(3,2, new long[20][20]) == 3);
        assertTrue(travel(3,3, new long[20][20]) == 6);
        assertTrue(travel(4,3, new long[20][20]) == 10);
        assertTrue(travel(18,18, new long[20][20]) == 2333606220l);
    }

    /**
     * Time complexity: O(n*m)
     * Space complexity: O(n+m)
     * @param n
     * @param m
     * @param tracking
     * @return
     */
    public long travel(int n, int m, long[][] tracking) {
        if (tracking[n][m] > 0) return tracking[n][m];

        if (n == 0 || m == 0) return 0;
        if (n == 1 && m == 1) return 1;

        tracking[n][m] = travel(n - 1, m, tracking) + travel(n, m - 1, tracking);

        return tracking[n][m];
    }


    @Test
    public void testTabular() {
        assertTrue(travelTabular(0, 0) == 0);
        assertTrue(travelTabular(0, 1) == 0);
        assertTrue(travelTabular(1, 0) == 0);
        assertTrue(travelTabular(1, 1) == 1);
        assertTrue(travelTabular(2, 3) == 3);
        assertTrue(travelTabular(3, 2) == 3);
        assertTrue(travelTabular(3, 3) == 6);
        assertTrue(travelTabular(4, 3) == 10);
        assertTrue(travelTabular(18, 18) == 2333606220l);
    }

    /**
     * Time complexity:
     *  c1 + O(n) + O(m) + c2 + O(m * n) * c3 + c4 => O(m * n)
     *
     * Space complexity: O(m * n)
     *
     * @param m
     * @param n
     * @return
     */
    public long travelTabular(int m, int n) {
        if (m == 0 || n == 0) return 0l; // Time: O(1), c1

        long[][] matrix = new long[m + 1][n + 1]; // Space: O((m+1) * (n+1)

        // Base cases
        // matrix[0][0] = 0l; initialized by default
        for (int i = 1; i <= n; i++) matrix[1][i] = 1; // Time: O(n)
        for (int i = 1; i <= m; i++) matrix[i][1] = 1; // Time: O(m)
        matrix[1][1] = 1l; // Time: O(1), c2

        for (int i =1; i <= m; i++) { // Time: O(m)
            for (int j = 2; j <= n; j++) // Time: O(n)
                matrix[i][j] = matrix[i][j - 1] + matrix[i - 1][j]; // Time: O(1), c3
        }

        return matrix[m][n]; // Time: O(1), c4
    }
}
