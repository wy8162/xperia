package y.w.study.alg.sort;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import org.junit.Test;

/**
 * Sorting by Swapping.
 *
 * In-place sorting. Returns the same array.
 */
public class SelectionSort {
    @Test
    public void test() {
        assertTrue(sort(null) == null);
        assertTrue(Arrays.compare(sort(new int[]{1}), new int[]{1}) == 0);
        assertTrue(Arrays.compare(sort(new int[]{1,3}), new int[]{1,3}) == 0);
        assertTrue(Arrays.compare(sort(new int[]{3,1}), new int[]{1,3}) == 0);
        assertTrue(Arrays.compare(sort(new int[]{1,2,3}), new int[]{1,2,3}) == 0);
        assertTrue(Arrays.compare(sort(new int[]{15,21,20,2,15,24,5,19}), new int[]{2,5,15,15,19,20,21,24}) == 0);
    }

    public int[] sort(int[] nums) {
        if (nums == null || nums.length <= 0)
            return nums;

        for (int i = 0; i < nums.length; i++) {
            int min_index = i;

            // Find the smallest one which is smaller than nums[i]
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[min_index])
                    min_index = j;
            }
            int temp = nums[i];
            nums[i] = nums[min_index];
            nums[min_index] = temp;
        }

        return nums;
    }
}
