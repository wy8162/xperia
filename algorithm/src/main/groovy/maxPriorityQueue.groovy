/**
 * Re-use heap sort to build max priority queue. A priority queue is a data structure
 * for maintaining a set S of elements, each with an associated value called a key. A
 * max-priority queue supports the following operations.
 *
 * Among their other applications, we can use max-priority queues to schedule
 * jobs on a shared computer. The max-priority queue keeps track of the jobs to
 * be performed and their relative priorities. When a job is finished or interrupted,
 * the scheduler selects the highest-priority job from among those pending by calling
 * EXTRACT-MAX. The scheduler can add a new job to the queue at any time by
 * calling INSERT.
 *
 * Similarly, we can build min-priority queue.
 *
 * Alternatively, a min-priority queue supports the operations INSERT, MINIMUM,
 * EXTRACT-MIN, and DECREASE-KEY. A min-priority queue can be used in an
 * event-driven simulator. The items in the queue are events to be simulated, each
 * with an associated time of occurrence that serves as its key. The events must be
 * simulated in order of their time of occurrence, because the simulation of an event
 * can cause other events to be simulated in the future. The simulation program calls
 * EXTRACT-MIN at each step to choose the next event to simulate. As new events are
 * produced, the simulator inserts them into the min-priority queue by calling INSERT.
 */
class MaxPriorityQueue
{
    int heapSize = 0

    private int parent(int i) { return (i - 1) / 2 } // Returns the index number of the parent node
    private int left(int i) { return 2 * i + 1 }    // Return the left child node of node i
    private int right(int i) { return 2 * i + 2 } // Return the right child node of node i
    private void exchange(int[] array, int i, int j)
    {
        int temp = array[i]
        array[i] = array[j]
        array[j] = temp
    }

    /**
     * Insert element key into the set array.
     *
     * TODO: expand the array safely
     *
     * @param set
     * @param x
     */
    void insert(int[] array, int key)
    {
        array[heapSize] = -1
        increaseKey(array, heapSize, key)
        heapSize++
    }

    /**
     * Returns the element of set with the largest key.
     */
    int maximum(int[] array)
    {
        return array[0]
    }

    /**
     * Removes and returns the element of set with the largest key.
     */
    int extractMaximum(int[] array)
    {
        if (heapSize < 1) {
            throw new IOException("Empty max priority queue");
        }
        int max = array[0];
        array[0] = array[heapSize - 1]

        heapSize--
        
        maxHeapify(array, 0)

        return max;
    }

    /**
     * Increases the value of element i to new value key, when key > i
     */
    void increaseKey(int[] array, int i, int key)
    {
        if (key < array[i]) return; // Doing nothing
        array[i] = key
        while (i > 0 && array[parent(i)] < array[i])
        {
            exchange(array, i, parent(i))
            i = parent(i)
        }
    }
    
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
     * Build max heap by repeatedly inserting
     */
    void buildMaxHeap(int[] array)
    {
        heapSize = 1
        for (int i = 1; i < array.length; i++) {
            insert(array, array[i])
        }
    }
}

def maxPriorityQueue = new MaxPriorityQueue();

a1 = [20,30,40,90,91,60,71,80,50,10, 3, 2, 77, 25] as int[]

println(a1)

maxPriorityQueue.buildMaxHeap(a1)

println(a1)

for (int i=0; i<a1.length; i++)
    println(maxPriorityQueue.extractMaximum(a1))
