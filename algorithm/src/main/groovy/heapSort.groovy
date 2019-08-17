/**
 * Arrays starts from index 0 and ends at n - 1
 * Heap Sort
 */
class HeapSort
{
    protected int heapSize = 0

    private int left(int i) { return 2 * i + 1 }    // Return the left child node of node i
    private int right(int i) { return 2 * i + 2 } // Return the right child node of node i
    private void exchange(int[] array, int i, int j)
    {
        int temp = array[i]
        array[i] = array[j]
        array[j] = temp
    }

    /**
     * In order to maintain the max-heap property, we call the procedure MAX-HEAPIFY.
     * Its inputs are an array A and an index i into the array. When it is called, MAXHEAPIFY
     * assumes that the binary trees rooted at LEFT.i / and RIGHT.i / are maxheaps,
     * but that AOEi might be smaller than its children, thus violating the max-heap
     * property. MAX-HEAPIFY lets the value at AOEi �float down� in the max-heap so
     * that the subtree rooted at index i obeys the max-heap property.
     *
     * @param array - array of elements of integers
     * @param i - starting from 0
     */
    void maxHeapify(int [] array, int i) {
        int l = left(i)
        int r = right(i)
        int largest = i
        
        if (l < heapSize && array[l] > array[i] )
             largest = l

        if (r < heapSize && array[r] > array[largest])
             largest = r

        if (largest != i) {
            // Exchange the values
            exchange(array, i, largest)
            maxHeapify(array, largest)
        }
    }

    /**
     * A Max Heap is a binary tree with the largest value at the root. And in the subtrees,
     * every node's is larger than the values of the left and right children.
     *
     * It's proved that values starting from array.length/2 are leaf nodes. So we build max
     * heap starting from the last non-leaf node.
     *
     * @param array
     */
    void buildMaxHeap(int[] array) {
        heapSize = array.length
        for (int i=array.length / 2 - 1; i >= 0; i--)
            maxHeapify(array, i)
    }

    /**
     * 1. Starting from the last of the array[0...heapsize]
     * 2. Swap array[last] with the value of array[0]
     * 3. Reduce heapsize value by 1 so that the last value will be left untouched
     * 4. The heap now has a value which is smaller than some values under the trees.
     *    So the heap is no-longer max heap. So we need to max heapify it from node 0.
     * 5. Repeat steps 1, 2, 3, 4
     *
     * @param array
     */
    void sort(int[] array) {
        buildMaxHeap(array)
        for (int i = array.length - 1; i > 0; i--) {
            // Exchange the values
            exchange(array, 0, i)
            heapSize--
            maxHeapify(array, 0)
        }
    }
}

def heapSort = new HeapSort()

a1 = [20,30,40,90,91,60,71,80,50,10, 3, 2, 77, 25] as int[]

heapSort.sort(a1)

println(a1)