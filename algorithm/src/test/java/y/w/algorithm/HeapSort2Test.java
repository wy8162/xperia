package y.w.algorithm;

import y.w.utils.TestUtils;
import org.junit.Test;

public class HeapSort2Test
{
    @Test
    public void heapSortTest() {
        HeapSort2 heapSort = new HeapSort2<Integer>();

        Integer[] array = new Integer[]{20,30,40,90,91,60,71,80,50,10, 3, 2, 77, 25};

        heapSort.sort(array);

        TestUtils.assertArraySortedAscending(array);
    }
}
