package y.w.study.alg.sort;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import org.junit.Test;

/**
 * Sorting by Swapping.
 *
 * In-place sorting. Returns the same array.
 */
public class InsertionSort {
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

        // Starting with assuming that nums[0] is in it's proper location.
        // Walk through the remaining elements one by one, inserting them
        // into the left most sorted part-array.
        for (int i = 1; i < nums.length; i++) {
            // nums[0..i-1] are already sorted.
            for (int j = i; j > 0; j--) {
                if (nums[j - 1] < nums[j])
                    break; // all [0..j-1] < [j]

                // Swap
                int temp = nums[j - 1];
                nums[j - 1] = nums[j];
                nums[j] = temp;
            }
        }

        return nums;
    }
}
