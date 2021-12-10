package y.w.study.alg.recursion;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class Heapsort {
    private static void sort(int[] arr) {
        for (int i = arr.length / 2 - 1; i >= 0; i--) // A[n/2..n] are leaves.
            maxHeapify(arr, arr.length, i);

        for (int i = arr.length - 1; i >= 0; i--) {
            swap(arr, 0, i);
            maxHeapify(arr, i, 0);
        }
    }

    private static void maxHeapify(int[] arr, int size, int i) {
        int l = 2 * i + 1; // Left node - i starts from 0
        int r = 2 * i + 2; // Right node

        // Finding the largest of arr[i], arr[l], arr[r]
        int largest = i;
        if (l < size && arr[l] > arr[i])
            largest = l;

        if (r < size && arr[r] > arr[largest])
            largest = r;

        if (largest != i) {
            swap(arr, i, largest);
            maxHeapify(arr, size, largest);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    @Test
    public void t1() {
        int[] arr1 = new int[]{30, 70, 50};
        maxHeapify(arr1, arr1.length, 0);
        assertThat(arr1).containsExactly(70, 30, 50);

        arr1 = new int[]{4,10,3,5,1};
        maxHeapify(arr1, arr1.length, 0);
        assertThat(arr1).containsExactly(10,5,3,4,1);

        arr1 = new int[]{4,10,3,5,1};
        sort(arr1);
        assertThat(arr1).isSorted();
    }
}
