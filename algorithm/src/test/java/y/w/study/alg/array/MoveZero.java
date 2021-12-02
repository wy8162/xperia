package y.w.study.alg.array;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import org.junit.Test;

/**
 * Given an integer array nums, move all 0's to the end of it while maintaining
 * the relative order of the non-zero elements.
 *
 * Note that you must do this in-place without making a copy of the array.
 */
public class MoveZero {

    @Test
    public void test() {
        int[] nums = null;

        nums = new int[]{0,1,0,3,12}; moveZeros(nums);
        assertTrue(Arrays.compare(nums, new int[]{1,3,12,0,0}) == 0);

        nums = new int[]{0}; moveZeros(nums);
        assertTrue(Arrays.compare(nums, new int[]{0}) == 0);
    }

    public void moveZeros(int[] nums) {
        if (nums == null || nums.length <= 1) return;

        int i = 0, j = 0;
        while (i <= nums.length - 1 && j <= nums.length - 1) {
            if (nums[j] != 0) {
                j++;
                i = Math.max(i, j);

                continue;
            }

            if (nums[i] != 0) {
                // Swap
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;

                i++; j++;
            } else {
                i++;
            }
        }
    }

    public void moveZeros2(int[] nums) {
        if (nums == null || nums.length <=1 ) return;

        int i = 0, j, len = nums.length;

        while (i < len && nums[i] != 0) i++;

        j = i + 1;
        while (j < len && nums[j] == 0) j++;

        while (j < len) {
            nums[i++] = nums[j];
            nums[j++] = 0;

            while (j < len && nums[j] == 0) j++;
        }
    }
}
