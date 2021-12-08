package y.w.study.alg.recursion;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class QuickSort {

    /**
     * Partition the array A[p...r] into A[p...q...r], where x in A[p...q-1] is less than A[q],
     * x in A[q+1..r] is greater than A[q].
     *
     * Recursively sort A[p..q-1] and A[q+1..r]
     *
     * T(n) = 2T(n/2) + f(n)
     * Master Theorem: T(n) = aT(n/b) + f(n^d)
     * a = 2, b = 2, d = 1 = logb(a)
     * => O(nlog(n))
     *
     * Space: O(1), in-place
     *
     * @param arr - numbers to be sorted
     * @param p - start index
     * @param r - end index
     * @return the same array of the numbers
     */
    private static int[] sort(int[] arr, int p, int r) {
        if (p >= r) return arr;

        int q = partition(arr, p, r); // f(n)
        sort(arr, p, q - 1); // T(n/2)
        sort(arr, q + 1, r); // T(n/2)

        return arr;
    }

    /**
     *     0   1   2   3   4   5   6   7
     *     2   8   7   1   3   5   6   4
     *     p                           r
     *     x=4
     *   i pj                          r
     *     2   8   7   1   3   5   6   4
     *     pi  j                       r
     *     2   8   7   1   3   5   6   4
     *     pi  j                       r
     *     2   8   7   1   3   5   6   4
     *     pi      j                   r
     *     2   8   7   1   3   5   6   4
     *     p   i       j               r
     *     2   8   7   1   3   5   6   4 swap
     *     p   i       j               r
     *     2   1   7   8   3   5   6   4
     *     p   i           j           r
     *     2   1   7   8   3   5   6   4
     *     p       i       j           r
     *     2   1   3   8   7   5   6   4
     *     p       i           j       r
     *     2   1   3   8   7   5   6   4
     *     p       i               j   r
     *     2   1   3   8   7   5   6   4
     *     p       i               j   r
     *     2   1   3   4   7   5   6   8 final swap
     *                 |
     *               q=i+1
     * @param arr
     * @param p
     * @param r
     * @return
     */
    private static int partition(int[] arr, int p, int r) {
        int x = arr[p]; // Choose the last element as pivot
        int i = p - 1;  // Points to the last position whose value < x

        // e of A[r+1..j] > x
        for (int j = p; j < r; j++) {
            if (arr[j] < x) {
                i++;
                swap(arr, i, j);
            }
        }
        // Move x to i + 1 so that x is to split the array.
        swap(arr, i + 1, r);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    @Test
    public void all() {
        t1();
        t2();
        t3();
    }

    @Test
    public void t1() {
        assertThat(sort(new int[]{2}, 0, 0))
            .isSorted()
            .containsExactly(2);
    }

    @Test
    public void t2() {
        assertThat(sort(new int[]{2, 1}, 0, 1))
            .isSorted()
            .containsExactly(1, 2);
    }

    @Test
    public void t3() {
        assertThat(sort(new int[]{2,3,1}, 0, 2))
            .isSorted()
            .containsExactly(1, 2, 3);
    }

    public static void main(String[] args) {
        assertThat(sort(new int[]{2}, 0, 0))
            .isSorted()
            .containsExactly(2);
    }
}
