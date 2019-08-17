package y.w.algorithm;

import y.w.utils.TestUtils;
import org.junit.Test;

public class InsertionSortTest
{
    @Test
    public void insertionSortTest() {
        InsertionSort insertionSort = new InsertionSort<Integer>();

        Integer[] array = new Integer[]{20,30,40,90,91,60,71,80,50,10, 3, 2, 77, 25};

        insertionSort.sort(array);

        TestUtils.assertArraySortedAscending(array);
    }
}
