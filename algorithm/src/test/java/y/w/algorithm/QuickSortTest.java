package y.w.algorithm;

import y.w.utils.TestUtils;
import org.junit.Test;

/**
 * Test QuickTest
 *
 * @author ywang
 */
public class QuickSortTest
{
    @Test
    public void quickSortTest() {
        QuickSort quickSort = new QuickSort<Integer>();

        Integer[] array = new Integer[]{20,30,40,90,91,60,71,80,50,10, 3, 2, 77, 25};

        quickSort.sort(array);

        TestUtils.assertArraySortedAscending(array);
    }
}
