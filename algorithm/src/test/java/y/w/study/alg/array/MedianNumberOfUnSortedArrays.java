package y.w.study.alg.array;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Random;
import org.junit.Test;

public class MedianNumberOfUnSortedArrays {

    @Test
    public void test() {
        assertEquals(7, findMedianByPartition(new int[]{12, 3, 5, 7, 4, 19, 26}));
        assertEquals(3, findMedianByPartition(new int[]{1,2,3,4,5}));
        assertEquals(6, findMedianByPartition(new int[]{5,6,7,12}));
        assertEquals(2, findMedianByPartition(new int[]{1,2,3,4}));

        assertEquals(3, getMedian(new int[]{1,2,3,4,5}));
        assertEquals(6, getMedian(new int[]{5,6,7,12}));
        assertEquals(2, getMedian(new int[]{1,2,3,4}));

    }

    /**
     * Time: O(nlog(n))
     *
     * @param ar1
     * @return
     */
    public long getMedian(int ar1[]) {
        Arrays.sort(ar1); // Time: O(nlog(n))

        int n = ar1.length / 2;

        return (ar1[n + ar1.length % 2 - 1] + ar1[n]) / 2;
    }

    /**
     * Best case analysis: O(1)
     * Average case analysis: O(N)
     * Worst case analysis: O(N2)
     *
     * @param nums
     * @return
     */
    public long findMedianByPartition(int[] nums) {
        if (nums == null) return 0;
        if (nums.length == 1) return nums[0];

        int k = nums.length / 2;

        int q = findMedian(nums, 0, nums.length - 1, k);

        int a = nums[q];
        int b = a;

        if (nums.length % 2 == 0) {
            q = findMedian(nums, 0, nums.length - 1, k - 1);
            b = nums[q];
        }

        return (a + b) / 2;
    }

    private int findMedian(int[] nums, int p, int r, int k) {
        int q = randomizedPartition(nums, p, r);

        if (k < q) {
            return findMedian(nums, p, q, k);
        } else if (k > q) {
            return findMedian(nums, q, r, k);
        }

        return q;
    }

    /**
     * Randomly partition the array so that all elements [0..q-1] are less than
     * [q] and all elements [q+1..n-1] are greater than [q].
     *
     * @param nums arrays of int
     * @param p start index
     * @param r end index
     * @return q the q index which splits the array.
     *
     */
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
