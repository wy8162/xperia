package y.w.study.alg.sort;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class QuickSort {
    @Test
    public void test() {
        int[] arr;

        arr = new int[] {};
        sort(arr);

        arr = new int[] {1};
        sort(arr);

        arr = new int[] {2,1};
        sort(arr);
        assertThat(arr).isSorted();

        arr = new int[] {3,5,2,1,4};
        sort(arr);
        assertThat(arr).isSorted();

        arr = new int[] {10,80,30,90,40,50,70};
        sort(arr);
        assertThat(arr).isSorted();
    }

    public void sort(int[] arr) {
        if (arr == null || arr.length <= 1) return;

        sortHelper(arr, 0, arr.length - 1);
    }

    private void sortHelper(int[] arr, int start, int end) {
        if (end - start <= 0) return;

        int r = partitionFirstAsPivot(arr, start, end); // O(n^1)

        sortHelper(arr, start, r - 1); // T(n/2)
        sortHelper(arr, r + 1, end);  // T(n/2)
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
