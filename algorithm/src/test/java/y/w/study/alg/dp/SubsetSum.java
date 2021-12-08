package y.w.study.alg.dp;

/**
 * Given a set of X of positive integers and target integer T, is there a subset of elements
 * in X that add up to T? Notice that there can be more than one such subset.
 *
 * For an element of x in X: if and only if the one of the following is true
 * - there is a subset of X that includes x and whose sum is T
 * - there is a subset of X that excludes x and whose sum is T
 */
public class SubsetSum {

    /**
     * Backtracking recursion.
     *
     * index starts from 0 to X.length - 1
     *
     * Time: T(n) = 2T(n-1) + O(1) => O(2^n)
     *
     * @param X
     * @param T
     * @param index
     * @return
     */
    private static boolean subsetSumRecursive(int[] X, int T, int index) {
        if (T == 0) return true;
        if (X.length == 0 || T < 0 || index > X.length - 1) return false;

        boolean withCurrent = subsetSumRecursive(X, T - X[index], index + 1);
        boolean withoutCurrent = subsetSumRecursive(X, T, index + 1);

        return withCurrent || withoutCurrent;
    }

    public static void main(String[] argv) {
        System.out.println(subsetSumRecursive(new int[]{3,7,8}, 15, 0));
        System.out.println(subsetSumRecursive(new int[]{8,6,7,53,10,9}, 15, 0));
        System.out.println(subsetSumRecursive(new int[]{11,6,5,1,7,13,12}, 15, 0));
    }
}
