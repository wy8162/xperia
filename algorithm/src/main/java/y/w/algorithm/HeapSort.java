package y.w.algorithm;

/**
 * The credit goes to the authors, Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, and Clofford Stein, of the book "Introduction to Algorithms".
 *
 * I studied the book and translated the pseudo codes to Java code.
 *
 * Heap Sort algorithm_saved based on introduction to Algorithms.
 *
 * Arrays starts from index 0 and ends at n - 1
 */
public class HeapSort<T extends Comparable<T>> implements Sort<T>
{
    private int  heapSize  = 0;

    /**
     * A Max Heap is a binary tree with the largest value at the root. And in the subtrees,
     * every node's is larger than the values of the left and right children.
     *
     * It's proved that values starting from array.length/2 are leaf nodes. So we build max
     * heap starting from the last non-leaf node.
     *
     * @param array the list of numbers to be sorted
     */
    private <T extends Comparable<T>> void buildMaxHeap(T[] array) {
        heapSize = array.length;
        for (int i=array.length / 2 - 1; i >= 0; i--)
            SortHelper.maxHeapify(heapSize, array, i);
    }

    /**
     * 1. Starting from the last of the array[0...heapsize]
     * 2. Swap array[last] with the value of array[0]
     * 3. Reduce heapsize value by 1 so that the last value will be left untouched
     * 4. The heap now has a value which is smaller than some values under the trees.
     *    So the heap is no-longer max heap. So we need to max heapify it from node 0.
     * 5. Repeat steps 1, 2, 3, 4
     *
     * @param array the list of numbers to be sorted
     */
    @Override
    public void sort(T[] array) {
        buildMaxHeap(array);
        for (int i = array.length - 1; i > 0; i--) {
            // Exchange the values
            SortHelper.exchange(array, 0, i);
            heapSize--;
            SortHelper.maxHeapify(heapSize, array, 0);
        }
    }
}
