package y.w.study.alg.array;

import org.junit.Test;

public class RemoveElements {
    @Test
    public void test() {
        int[] nums1 = new int[]{3,2,2,3};
        assert removeElement(nums1, 3) == 2;

        int[] nums2 = new int[]{0,1,2,2,3,0,4,2};
        assert removeElement(nums2, 2) == 5;

        int[] nums3 = new int[]{3,2,2,3};
        assert removeElement2(nums3, 3) == 2;

        int[] nums4 = new int[]{0,1,2,2,3,0,4,2};
        assert removeElement2(nums4, 2) == 5;
    }

    public int removeElement(int[] nums, int val) {
        int count = nums.length;

        int i = 0;
        int k = 0;
        while (i <= nums.length - 1) {
            if (nums[i] == val) {
                count--;

                int j = i + 1;
                while (j <= nums.length - 1 && val == nums[j]) {
                    count--;
                    j++;
                }
                if (j >= nums.length)
                    break;
                nums[k++] = nums[j];
                i = j + 1;
            } else {
                nums[k++] = nums[i++];
            }
        }

        return count;
    }

    public int removeElement2(int[] nums, int val) {
        int count = 0;
        for (int i=0; i< nums.length-1; i++) {
            if (nums[i] != val)
                nums[count++] = nums[i];
        }

        return count;
    }

    @Test
    public void testRemoveDups() {
        int[] nums1 = new int[]{1,1,2};
        assert removeDuplicate(nums1) == 2;

        int[] nums2 = new int[]{0,0,1,1,1,2,2,3,3,4};
        assert removeDuplicate(nums2) == 5;

    }

    public int removeDuplicate(int[] nums) {
        int count = nums.length;

        int i = 0;
        int k = 0;
        while (i <= nums.length - 1) {
            int j = i + 1;
            while (j <= nums.length - 1 && nums[i] == nums[j]) {
                j++;
                count--;
            }

            nums[k++] = nums[i];

            if (j >= nums.length) break;

            i = j;
        }

        return count;
    }

    public int removeDuplicate2(int[] nums) {
        int i = 1;
        int u = 0;
        while(i<nums.length){
            if(nums[i]!=nums[u]){
                u++;
                nums[u]=nums[i];
            }
            i++;
        }

        return u+1;
    }
}
