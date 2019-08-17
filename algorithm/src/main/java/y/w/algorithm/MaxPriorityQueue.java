package y.w.algorithm;

/**
 * The credit goes to the authors, Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, and Clofford Stein, of the book "Introduction to Algorithms".
 *
 * I studied the book and translated the pseudo codes to Java code.
 *
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
public class MaxPriorityQueue
{
    int[] queue = null;
    int queueCapacity = 0; // the maximumOf size of the queue
    int heapSize = 0;      // the number of elements in the queue currently

    public MaxPriorityQueue(int queueSize)
    {
        this.queueCapacity = queueSize;
        queue = new int[this.queueCapacity];
    }

    /**
     * Insert element key into the set array.
     *
     * @param key
     */
    public void insert(int key) throws Exception
    {
        if (heapSize >= queueCapacity)
            throw new Exception("The number of elements is too large");

        queue[heapSize] = -1;
        increaseKey(heapSize, key);
        heapSize++;
    }

    /**
     * @return true if there is elements in the queue
     */
    public boolean hasElements()
    {
        return heapSize > 0;
    }

    /**
     * @return the capacity of the queue
     */
    public int capacity()
    {
        return queueCapacity;
    }

    /**
     * @return the size of the queue
     */
    public int size()
    {
        return heapSize;
    }

    /**
     * Returns the element of set with the largest key.
     */
    public int maximum() throws Exception
    {
        if (heapSize >= 1)
            return queue[0];
        else
            throw new Exception("Priority queue is empty");
    }

    /**
     * Removes and returns the element of set with the largest key.
     */
    public int extractMaximum() throws Exception
    {
        if (heapSize < 1) {
            throw new Exception("Empty max priority queue");
        }
        int max = queue[0];
        queue[0] = queue[heapSize - 1];

        heapSize--;

        heapify(heapSize, queue, 0);

        return max;
    }

    /**
     * Increases the value of element i to new value key, when key > i
     */
    public void increaseKey(int i, int key)
    {
        if (key < queue[i]) return; // Doing nothing

        queue[i] = key;

        while (i > 0 && queue[SortHelper.p(i)] < queue[i])
        {
            exchange(queue, i, SortHelper.p(i));
            i = SortHelper.p(i);
        }
    }

    /**
     * Build max heap by repeatedly inserting elements from array
     */
    public void buildMaxHeap(int[] array) throws Exception
    {
        for (int i = 0; i < array.length; i++) {
            insert(array[i]);
        }
    }

    private void exchange(int [] array, int i, int j)
    {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private void heapify(int heapSize, int[] array, int i) {
        int l = SortHelper.left(i);
        int r = SortHelper.right(i);
        int largest = i;

        if (l < heapSize && array[l] > array[i])
            largest = l;

        if (r < heapSize && array[r] > array[largest])
            largest = r;

        if (largest != i) {
            // Exchange the values
            exchange(array, i, largest);
            heapify(heapSize, array, largest);
        }
    }
}

