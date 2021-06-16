package y.w.study.alg.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class ThreeSum {
    @Test
    public void test() {
        List<List<Integer>> result;

        result = threeSumWithTwoSum(new int[]{}, 0);
        result = threeSumWithTwoSum(new int[]{0}, 0);

        result = threeSumBruteForce(new int[]{-1,0,1,2,-1,-4}, 0);
        result = threeSumWithTwoSum(new int[]{-1,0,1,2,-1,-4}, 0);

        result = threeSumUsingSortedTwoSum(new int[]{-1,0,1,2,-1,-4}, 0);
    }

    public List<List<Integer>> threeSumBruteForce(int[] nums, int target) {
        if (nums == null || nums.length < 3) return new ArrayList<>();

        Arrays.sort(nums);

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            for (int j = i + 1; j < nums.length - 1; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;
                for (int k = j + 1; k < nums.length; k++) {
                    if (k > j + 1 && nums[k] == nums[k - 1]) continue;
                    if (nums[i] + nums[j] + nums[k] == target) {
                        List<Integer> temp = new ArrayList<>();
                        temp.add(nums[i]);
                        temp.add(nums[j]);
                        temp.add(nums[k]);
                        result.add(temp);
                    }
                }
            }
        }

        return result;
    }

    public List<List<Integer>> threeSumUsingSortedTwoSum(int[] nums, int target) {
        if (nums == null || nums.length < 3) return new ArrayList<>();

        Arrays.sort(nums);

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length - 1; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue; //

            int delta = target - nums[i];

            int left = i + 1;
            int right = nums.length - 1;
            while(left < right) {
                if (nums[left] + nums[right] > delta) {
                    right--; // Checking the greater numbers
                } else if (nums[left] + nums[right] < delta) {
                    left++;  // Checking the smaller numbers
                } else {
                    // Found a solution
                    List<Integer> temp = new ArrayList<>();
                    temp.add(nums[i]);
                    temp.add(nums[left]);
                    temp.add(nums[right]);
                    result.add(temp);

                    // Skip the duplicates and continue to find the others if any
                    while(left < nums.length - 1 && nums[left] == nums[left + 1]) left++;
                    while(right > 0 && nums[right] == nums[right -1]) right--;
                    left++;
                    right--;
                }
            }
        }

        return result;
    }

    public List<List<Integer>> threeSumWithTwoSum(int[] nums, int target) {
        if (nums == null || nums.length < 3) return new ArrayList<>();

        Arrays.sort(nums);

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int newTarget = target - nums[i];

            List<List<Integer>> twoSumList = twoSum(newTarget, nums, i + 1);
            for (List<Integer> list : twoSumList) {
                List<Integer> temp = new ArrayList<>();
                temp.add(nums[i]);
                temp.addAll(list);
                Collections.sort(temp);
                result.add(temp);
            }
        }

        return result;
    }


    private List<List<Integer>> twoSum(int target, int[] nums, int start) {
        Map<Integer, Integer> tracking = new HashMap<>();

        List<List<Integer>> results = new ArrayList<>();

        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) continue;
            int delta = target - nums[i];

            if (tracking.containsKey(delta)) {
                List<Integer> temp = Arrays.asList(nums[tracking.get(delta)], nums[i]);
                results.add(temp);
            } else {
                tracking.put(nums[i], i);
            }
        }

        return results;
    }
}
