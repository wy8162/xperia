package y.w.study.alg.array;

import java.util.Arrays;
import org.junit.Test;

public class SquaresOfASortedArray {
    @Test
    public void test() {
        int[] res1 = sortedSquares(new int[]{-4, -1, 0, 3, 10});
        int[] res2 = sortedSquares(new int[]{-7, -3, 2, 3, 11});
        int[] res3 = sortedSquares2(new int[]{-4, -1, 0, 3, 10});
        int[] res4 = sortedSquares2(new int[]{-7, -3, 2, 3, 11});
        int[] res5 = sortedSquares2(new int[]{-5,-3,-2,-1});
        int[] res6 = sortedSquares2(new int[]{1,2,3,4,5});
        int[] res7 = sortedSquares2(new int[]{-4,-1,0,3,10});
        int[] res10= sortedSquares3(new int[]{-4, -1, 0, 3, 10});
        int[] res11= sortedSquares3(new int[]{-7, -3, 2, 3, 11});
        int[] res12= sortedSquares3(new int[]{-5,-3,-2,-1});
        int[] res13= sortedSquares3(new int[]{1,2,3,4,5});
        int[] res14= sortedSquares3(new int[]{-4,-1,0,3,10});
    }

    public int[] sortedSquares(int[] nums) {
        for (int i=0; i<nums.length; i++)
            nums[i] *= nums[i];

        Arrays.sort(nums);

        return nums;
    }

    public int[] sortedSquares2(int[] nums) {
        int[] snums = new int[nums.length];
        int index = nums.length - 1;

        int i=0, j=nums.length-1;
        while(i <= j) {
            if (Math.abs(nums[i]) <= Math.abs(nums[j])) {
                snums[index--] = nums[j] * nums[j];
                j--;
            } else {
                snums[index--] = nums[i] * nums[i];
                i++;
            }
        }

        return snums;
    }

    public int[] sortedSquares3(int[] nums) {
        return Arrays.stream(nums).map(num -> num * num).sorted().toArray();
    }
}
