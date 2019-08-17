package y.w.algorithm;

/**
 * Got it from internet
 */
public class HeapSort2<T extends Comparable<T>> implements Sort<T>
{
    @Override
    public void sort(T[] array)
    {
        int n = array.length;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(array, n, i);

        // One by one extract an element from heap
        for (int i = n - 1; i >= 0; i--)
        {
            // Move current root to end
            SortHelper.exchange(array, 0, i);

            // call max heapify on the reduced heap
            heapify(array, i, 0);
        }
    }

    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
    private <T extends Comparable<T>> void heapify(T[] array, int n, int i)
    {
        int largest = i;  // Initialize largest as root
        int l = 2 * i + 1;  // left = 2*i + 1
        int r = 2 * i + 2;  // right = 2*i + 2

        // If left child is larger than root
        if (l < n && array[l].compareTo(array[largest]) > 0)
        {
            largest = l;
        }

        // If right child is larger than largest so far
        if (r < n && array[r].compareTo(array[largest]) > 0)
        {
            largest = r;
        }

        // If largest is not root
        if (largest != i)
        {
            SortHelper.exchange(array, i, largest);

            // Recursively heapify the affected sub-tree
            heapify(array, n, largest);
        }
    }
}
