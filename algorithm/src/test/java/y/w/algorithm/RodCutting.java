package y.w.algorithm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Rn = max(P[i] + R[n - i]) where i = 1..n
 *
 * cutting(p, n)
 *  if n == 0 return 0
 *
 *  q = 0
 *  for i = 0 to n - 1
 *      q = max(q, p[i] + cutting(p, n - 1)
 *
 *  return q;
 */
public class RodCutting {
    @Test
    public void test() {
        System.out.println("For Size 8");
        assertEquals(22, cutting(new int[]{1,5,8,9,10,17,17,20,24,30}, 8));
        System.out.println("For Size 4");
        assertEquals(10, cutting(new int[]{1,5,8,9,10,17,17,20,24,30}, 4));

        System.out.println("Memoized - bottom up");
        assertEquals(22, cuttingMemoized(new int[]{1,5,8,9,10,17,17,20,24,30}, 8));
        assertEquals(10, cuttingMemoized(new int[]{1,5,8,9,10,17,17,20,24,30}, 4));
    }

    public int cutting(int[] p, int size) {
        if (size == 0) return 0;

        int q = 0;
        for (int i = 0; i < size; i++) {
            q = Math.max(q, p[i] + cutting(p, size - i - 1));

            System.out.println(String.format("size = %d max(%d) %d", size, i, q));
        }

        return q;
    }


    public int cuttingMemoized(int[] p, int size) {
        if (size == 0) return 0;

        int[] DP = new int[size + 1];
        DP[0] = 0; // Base case

        for (int n = 1; n <= size; n++) { // Rn = max(P[i] + R[n - i]) where i = 0..n
            int Rn = Integer.MIN_VALUE;
            for (int j = 0; j < n; j++) {
                // I.e., for R3 is the maximum of the following
                // P[0] + R[2]
                // P[1] + R[1]
                // P[2] + R[0]
                Rn = Math.max(Rn, p[j] + DP[n - j - 1]);
                DP[n] = Rn;
            }
        }

        return DP[size];
    }
}
