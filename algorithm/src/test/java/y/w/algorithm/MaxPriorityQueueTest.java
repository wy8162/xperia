package y.w.algorithm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MaxPriorityQueueTest
{
    private final int capacity = 5;
    private final int[] nums = new int[] {4,3,5,2};

    @Test
    public void maxPriorityQueueTest() throws Exception
    {

        MaxPriorityQueue priorityQueue = new MaxPriorityQueue(capacity);

        assertEquals(priorityQueue.capacity(), capacity);
        assertTrue(!priorityQueue.hasElements());

        priorityQueue.insert(6);
        assertTrue(priorityQueue.hasElements());
        assertTrue(priorityQueue.size() == 1);

        priorityQueue.buildMaxHeap(nums);
        assertTrue(priorityQueue.hasElements());
        assertTrue(priorityQueue.size() == 5);
        assertTrue(priorityQueue.maximum() == 6);

        assertTrue(priorityQueue.extractMaximum() == 6);
        assertTrue(priorityQueue.hasElements());
        assertTrue(priorityQueue.size() == 4);

        assertTrue(priorityQueue.extractMaximum() == 5);
        assertTrue(priorityQueue.extractMaximum() == 4);
        assertTrue(priorityQueue.extractMaximum() == 3);
        assertTrue(priorityQueue.extractMaximum() == 2);

        assertTrue(!priorityQueue.hasElements());
        assertTrue(priorityQueue.size() == 0);
    }
}
