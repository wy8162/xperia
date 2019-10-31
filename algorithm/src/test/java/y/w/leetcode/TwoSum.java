package y.w.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of integers, return indices of the two numbers such
 * that they add up to a specific target.
 *
 * You may assume that each input would have exactly one solution, and
 * you may not use the same element twice.
 *
 * Given nums = [2, 7, 11, 15], target = 9,
 *
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 *
 */
public class TwoSum
{
    static int[] twoSum(int [] nums, int target)
    {
        int[] result = new int[2];

        Map<Integer, Integer> map = new HashMap<>();

        for ( int i = 0; i < nums.length; i++)
        {
            int delta = target - nums[i];
            if (map.containsKey(delta))
            {
                result[1] = i;
                result[0] = map.get(delta);
                break;
            }
            map.put(nums[i], i);
        }
        return result;
    }

    public static void main(String[] args)
    {
        int result[] = twoSum(new int[]{2, 7, 11, 15}, 9);
        System.out.println(Arrays.toString(result));
    }
}
