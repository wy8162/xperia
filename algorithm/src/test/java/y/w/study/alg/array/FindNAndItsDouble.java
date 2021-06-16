package y.w.study.alg.array;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class FindNAndItsDouble {
    @Test
    public void test() {
        assert checkIfExists(new int[]{10, 2, 5, 3});
        assert checkIfExists(new int[]{7,1,14,11});
        assert !checkIfExists(new int[]{3,1,7,11});
        assert !checkIfExists(new int[]{4,-7,11,4,18});
        assert !checkIfExists(new int[]{-2,0,10,-19,4,6,-8});
    }

    public boolean checkIfExists(int[] nums) {
        if (nums == null || nums.length <= 1)
            return false;

        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i <= nums.length - 1; i++) {
            if (map.containsKey(nums[i]*2) || nums[i] % 2 ==0 && map.containsKey(nums[i]/2))
                return true;

            map.put(nums[i], i);
        }

        return false;
    }
}
