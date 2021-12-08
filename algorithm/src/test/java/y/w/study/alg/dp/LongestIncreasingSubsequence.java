package y.w.study.alg.dp;

public class LongestIncreasingSubsequence {

    /**
     * Time: T(n) = 2T(n-1) + O(1) => O(2^n)
     *
     * @param A
     * @param prev
     * @param j
     * @return
     */
    private static int lisRecursive(int[] A, int prev, int j) {
        if (j >= A.length) return 0;

        if (prev >= A[j]) return lisRecursive(A, prev, j+1); // Skip

        int skip = lisRecursive(A, prev, j + 1);     // T(n-1)
        int take = lisRecursive(A, A[j], j + 1) + 1; // T(n-1)

        return Math.max(skip, take);                   // O(1)
    }

    /**
     * A[0] is sentinel. The actual array numbers are A[1..n]
     *
     * lisSentinel(i, j):
     * - 0                                                      if j > n
     * - lisSentinel(i, j + 1)                                  if A[i] >= A[j]
     * - max(lisSentinel(i, j + 1), 1 + lisSentinel(j, j + 1))  otherwise
     *
     * Recursive sub-problem is identified by two indices (i, j)
     *
     * @param A
     * @param i
     * @param j
     * @return
     */
    private static int lisSentinel(int[] A, int i, int j) {
        if (j > A.length) return 0;

        if (A[i] >= A[j]) return lisSentinel(A, i, j + 1);

        int skip = lisSentinel(A, i, j + 1);
        int take = lisSentinel(A, j, j + 1) + 1;

        return Math.max(skip, take);
    }

    /**
     * A[0] is sentinel. The actual array numbers are A[1..n]
     *
     * lisSentinel(i, j):
     * - 0                                                      if j > n
     * - lisSentinel(i, j + 1)                                  if A[i] >= A[j]
     * - max(lisSentinel(i, j + 1), 1 + lisSentinel(j, j + 1))  otherwise
     *
     * Recursive sub-problem is identified by two indices (i, j), which is filled when
     * (i, j+1) and (j, j+1) are computed.
     *
     * Time: O(n^2)
     *
     * @param A
     * @return
     */
    private static int lisDP(int[] A) {
        int[][]LIS = new int[A.length + 1][A.length + 2]; // All automatically initialized to 0.

        for (int j = A.length - 1; j >= 1; j--) {
            for (int i = 0; i <= j - 1; i++) {
                int keep = 1 + LIS[j][j + 1];
                int skip = LIS[i][j + 1];

                if (A[i] >= A[j])
                    LIS[i][j] = skip;
                else
                    LIS[i][j] = Math.max(keep, skip);
            }
        }

        return LIS[0][1];
    }

    public static void main(String[] argv) {
        System.out.println(lisRecursive(new int[]{3,10,2,1,20}, Integer.MIN_VALUE, 0)); // 3
        System.out.println(lisRecursive(new int[]{3,2}, Integer.MIN_VALUE, 0)); // 1
        System.out.println(lisRecursive(new int[]{50,3,10,7,40,80}, Integer.MIN_VALUE, 0)); // 4

        System.out.println(lisRecursive(new int[]{Integer.MIN_VALUE,3,10,2,1,20}, 0, 1)); // 3
        System.out.println(lisRecursive(new int[]{Integer.MIN_VALUE,3,2}, 0, 1)); // 1
        System.out.println(lisRecursive(new int[]{Integer.MIN_VALUE, 50,3,10,7,40,80}, 0, 1)); // 4

        System.out.println(lisDP(new int[]{Integer.MIN_VALUE,3,10,2,1,20})); // 3
        System.out.println(lisDP(new int[]{Integer.MIN_VALUE,3,2})); // 1
        System.out.println(lisDP(new int[]{Integer.MIN_VALUE, 50,3,10,7,40,80})); // 4
    }
}
