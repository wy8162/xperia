package y.w.study.alg.array;

import org.junit.Test;

public class NumbersOfEvenDigits {
    @Test
    public void test() {
        assert findSolution(new int[]{12, 345, 2, 6, 7896}) == 2;
        assert findSolution(new int[]{555, 901, 482, 1771}) == 1;
        assert findSolution2(new int[]{12, 345, 2, 6, 7896}) == 2;
        assert findSolution2(new int[]{555, 901, 482, 1771}) == 1;
    }

    public int findSolution(int[] nums) {
        int count = 0;
        for (int i=0; i<nums.length; i++) {
            if (nums[i] <= 9) continue;
            if (nums[i] == 100000) count++;
            if (nums[i] >= 10000) continue;
            if (nums[i] <= 99) count++;
            if (nums[i] > 999 && nums[i] <= 9999) count++;
        }

        return count;
    }

    public int findSolution2(int[] nums) {
        int count = 0;
        for (int i=0; i<nums.length; i++) {
            if (hasEvenDigits(nums[i])) count++;
        }

        return count;
    }

    private boolean hasEvenDigits(int num) {
        boolean even = false;

        num = num / 10;
        while (num > 0) {
           even = !even;
           num /= 10;
        }

        return even;
    }
}
