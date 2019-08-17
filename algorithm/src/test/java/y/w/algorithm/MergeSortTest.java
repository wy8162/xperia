package y.w.algorithm;

import y.w.utils.TestUtils;
import org.junit.Test;

/**
 * Test QuickTest
 *
 * @author ywang
 */
public class MergeSortTest
{
    @Test
    public void mergeSortTest() {
        MergeSort mergeSort = new MergeSort<Integer>();

        Integer[] array = new Integer[]{20,30,40,90,91,60,71,80,50,10, 3, 2, 77, 25};

        mergeSort.sort(array);

        TestUtils.assertArraySortedAscending(array);
    }
}
