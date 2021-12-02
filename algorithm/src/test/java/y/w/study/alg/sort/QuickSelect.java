package y.w.study.alg.sort;

import org.junit.Test;

public class QuickSelect {
    @Test
    public void test() {
        int[] arr;
        int r;

        arr = new int[] {3,5,2,1,4};
        r = select(arr, 3);

        arr = new int[] {10,80,30,90,40,50,70};
        r = select(arr, 4);
    }

    /**
     * Select the kth element from an unselected array.
     *
     * This method can be used to find median value of a series of numbers.
     *
     * @param arr
     * @param k
     * @return
     */
    public int select(int[] arr, int k) {
        if (arr == null || arr.length == 0) return 0;
        if (arr.length == 1) return arr[0];

        return quickSelect(arr, 0, arr.length - 1, k);
    }

    private int quickSelect(int[] arr, int start, int end, int k) {
        int r = partitionFirstAsPivot(arr, start, end); // O(n^1)

        if (k < r) return quickSelect(arr, start, r - 1, k);
        else if (k > r) return quickSelect(arr, r + 1, end, k);
        else return arr[r];
    }

    /**
     * Partitions a subarray by Hoare's algorithm, using first element as a pivot.
     *        0 1 2 3 4
     *        6 4 7 5 2
     *  p = 6
     *        6 4 7 5 2
     *        i->    <-j
     *
     *  Master Theorem: T(n) = 2T(n/2) + O(n)
     *  a = 2, b = 2, d = 1, a = b^d
     *  T(n) = O(nlog(n))
     */
    private int partitionFirstAsPivot(int[] arr, int start, int end) {
        int p = arr[start];

        int i = start, j = end;

        do {
            while (i < end && arr[i] <= p) i++;
            while (j > start && arr[j] >= p) j--;
            swap(arr, i, j);
        } while (i < j);

        swap(arr, i, j); // undo the last swap - two cases : 1) i > j; 2) i = j.
        swap(arr, start, j);

        return j;
    }

    private int partitionLastAsPivot(int[] nums, int p, int r) {
        int x = nums[r];
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (nums[j] <= x) {
                i++;
                swap(nums, i, j);
            }
        }
        swap(nums, i + 1, r);

        return i + 1;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
