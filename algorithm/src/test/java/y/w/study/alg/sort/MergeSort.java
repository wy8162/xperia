package y.w.study.alg.sort;

import org.junit.Test;

public class MergeSort {
    @Test
    public void test() {
        int[] arr;

        arr = new int[] {};
        sort(arr);

        arr = new int[] {1};
        sort(arr);

        arr = new int[] {2,1};
        sort(arr);

        arr = new int[] {3,5,2,1,4};
        sort(arr);
    }

    public void sort(int[] arr) {
        if (arr == null) return;

        sortHelper(arr, 0, arr.length - 1);
    }

    private void sortHelper(int[] arr, int start, int end) {
        if (end - start + 1 > 1) {
            int m = (end + start) / 2;
            sortHelper(arr, start, m);
            sortHelper(arr, m + 1, end);
            merge(arr, start, m, end);
        }
    }

    /**
     * Merge two sorted subarray arr[start..middle] and arr[middle+1..end].
     *
     * The merged array is sorted.
     *
     * @param arr
     * @param start
     * @param middle
     * @param end
     */
    private void merge(int[] arr, int start, int middle, int end) {
        int[] temp = new int[end - start + 1];

        int i = start, j = middle + 1;

        /**
         * a1 a2 a3 ... a_start, ..., a_middle, a_middle+1 ..., a_end, ...
         *              |_i                     |_j
         */
        for (int k = 0; k < temp.length; k++) {
            if (j > end) { // copy the i_part
                temp[k] = arr[i];
                i++;
            } else if (i > middle) { // copy the j_part
                temp[k] = arr[j];
                j++;
            } else if (arr[i] < arr[j]) { // take arr[i]
                temp[k] = arr[i];
                i++;
            } else { // take arr[j]
                temp[k] = arr[j];
                j++;
            }
        }

        for (int k = 0; k < temp.length; k++)
            arr[start + k] = temp[k];
    }

}
