package y.w.algorithm;

import java.util.Random;

public class SelectionSort {
    public int sort(int[] nums) {
        return selection(nums, 0, nums.length - 1, 0);
    }

    private int selection(int[] nums, int start, int end, int ith) {
        if (start == end) return nums[start];

        int q = randomizedPartition(nums, start, end);

        int k = q - start + 1;
        if (ith == k)
            return nums[q];
        else if (ith < k) {
            return selection(nums, start, q - 1, ith);
        } else {
            return selection(nums, q + 1, end, ith - k);
        }
    }

    private int randomizedPartition(int[] nums, int p, int r) {
        int i = p + Math.abs(new Random().nextInt(r - p + 1));
        swap(nums, i, r);
        return partition(nums, p, r);
    }

    private int partition(int[] nums, int p, int r) {
        int x = nums[r];
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (nums[j] <= x) {
                i++;
                swap(nums, i, j);
            }
        }
        swap(nums, i + 1, r);

        return i + 1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
