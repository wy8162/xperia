package y.w.study.alg.array;

import org.junit.Test;

public class ValidMountaiArray {
    @Test
    public void test() {
        assert !validMountainArray(new int[]{2,1});
        assert !validMountainArray(new int[]{3,5,5});
        assert validMountainArray(new int[]{0,3,2,1});
        assert validMountainArray(new int[]{1,3,2});
        assert !validMountainArray(new int[]{2,0,2});
        assert !validMountainArray(new int[]{9,8,7,6,5,4,3,2,1,0});
    }

    public boolean validMountainArray(int[] arr) {
        if (arr == null || arr.length < 3)
            return false;

        boolean reverted = false;

        int i = 0;
        for (; i<arr.length-2; i++) {
            if (arr[i] < arr[i + 1]) continue;

            if (i == 0) return false;

            reverted = true;
            break;
        }

        if (i < arr.length - 1) {
            reverted = true;
            for (i++; i<=arr.length-1; i++) {
                if (arr[i] < arr[i - 1]) continue;
                reverted = false;
            }
        }

        return reverted;
    }
}
