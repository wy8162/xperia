package y.w.study.alg.array;

import java.util.Arrays;
import org.junit.Test;

public class ProductOfArrayExceptSelf {

    @Test
    public void test() {
        int[] ans = null;

        ans = productExceptSelfBruteForce(new int[]{1,2,3,4});
        ans = productExceptSelfBruteForce(new int[]{-1,1,0,-3,3});

        ans = productExceptSelf(new int[]{1,2,3,4});
        ans = productExceptSelf(new int[]{-1,1,0,-3,3});
    }

    /**
     * Time: O(n^2)
     * Space: O(1)
     * @param nums
     * @return
     */
    public int[] productExceptSelfBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) return new int[]{};

        int[] answer = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            answer[i] = 1;
            for (int j = i - 1; j >= 0; j--)
                answer[i] = answer[i] * nums[j];

            for (int j = i + 1; j < nums.length; j++)
                answer[i] = answer[i] * nums[j];
        }

        return answer;
    }

    /**
     * Time: O(2n) -> O(n)
     * Space: O(1) - constant space.
     *
     * @param nums
     * @return
     */
    public int[] productExceptSelf(int[] nums) {
       if (nums == null || nums.length == 0) return new int[]{};

       int[] answer = new int[nums.length + 1];

       answer[nums.length] = 1;

       for (int i = nums.length - 1; i >= 0; i--) {
           answer[i] = nums[i] * answer[i + 1];
       }

       answer[0] = answer[1];

       int pl = 1;
       for (int i = 0; i < nums.length; i++) {
           answer[i] = pl * answer[i + 1];

           pl = pl * nums[i];
       }

       return Arrays.copyOf(answer, nums.length);
    }
}
