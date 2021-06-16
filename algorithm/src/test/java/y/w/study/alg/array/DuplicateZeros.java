package y.w.study.alg.array;

import org.junit.Test;

public class DuplicateZeros {
    @Test
    public void test() {
        int[] nums = {1, 0, 2, 3, 0, 4, 5, 0};
        duplicateZeros(nums);

        int[] nums1 = {1, 2, 3};
        duplicateZeros(nums1);

        int[] nums2 = {0,1,7,6,0,2,0,7};
        duplicateZeros(nums2);

        int[] nums3 = {8,4,5,0,0,0,0,7};
        duplicateZeros(nums3);

        int[] nums4 = {1,5,2,0,6,8,0,6,0};
        duplicateZeros(nums4);
    }
    public void duplicateZeros(int[] nums) {
        int right = nums.length - 1;
        for (int i = 0; i <= nums.length - 1 ; i++ ) {
            if (nums[i] == 0) right++;
        }

        if (right == nums.length - 1) return;

        for (int i = nums.length - 1, j = right; i >= 0; i--) {
            if (nums[i] == 0) {
                virtualArrayAdd(nums, j--, 0);
                virtualArrayAdd(nums, j--, 0);
            } else {
                virtualArrayAdd(nums, j--, nums[i]);
            }
        }
    }

    private void virtualArrayAdd(int[] nums, int pos, int num) {
        if (pos <= nums.length - 1) nums[pos] = num;
    }

}
