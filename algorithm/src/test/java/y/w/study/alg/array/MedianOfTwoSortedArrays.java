package y.w.study.alg.array;

import org.junit.Test;

/**
 * Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median
 * of the two sorted arrays.
 * <p>
 * Example 1:
 * <p>
 * Input: nums1 = [1,3], nums2 = [2] Output: 2.00000 Explanation: merged array = [1,2,3] and median
 * is 2. Example 2:
 * <p>
 * Input: nums1 = [1,2], nums2 = [3,4] Output: 2.50000 Explanation: merged array = [1,2,3,4] and
 * median is (2 + 3) / 2 = 2.5. Example 3:
 * <p>
 * Input: nums1 = [0,0], nums2 = [0,0] Output: 0.00000 Example 4:
 * <p>
 * Input: nums1 = [], nums2 = [1] Output: 1.00000 Example 5:
 * <p>
 * Input: nums1 = [2], nums2 = [] Output: 2.00000
 */
public class MedianOfTwoSortedArrays {
    @Test
    public void test() {
        assert getMedian(new int[]{1,3}, new int[]{2}) == 2;
        assert getMedian(new int[]{1,2}, new int[]{3,6}) == 3;
        assert getMedian(new int[]{0,0}, new int[]{0,0}) == 0;
        assert getMedian(new int[]{}, new int[]{1}) == 1;
        assert getMedian(new int[]{2}, new int[]{}) == 2;
    }

    // Function to calculate median
    public long getMedian(int ar1[], int ar2[]) {
        int[] sorted = merge(ar1, ar2);

        int n = sorted.length / 2;

        double median = (sorted[n + sorted.length % 2 - 1] + sorted[n]) / 2.0;

        return Math.round(median);
    }

    /**
     * Time:  O(max(m, n))
     *
     * Space: O(m + n)
     *
     * Returns an array of integers of all the elements of two arrays.
     *
     * @param L sorted array of integers
     * @param R sorted array of integers
     * @return
     */
    private int[] merge(int[] L, int[] R)
    {
        int i, j, k;
        int n1 = L.length;
        int n2 = R.length;

        int[] nums = new int[n1 + n2];

        i = 0;
        j = 0;
        k = 0;
        while (i < n1 && j < n2)
        {
            if (L[i] < R[j])
            {
                nums[k] = L[i];
                i++;
            }
            else if (j < n2)
            {
                nums[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1)
        {
            nums[k] = L[i];
            i++;
            k++;
        }
        while (j < n2)
        {
            nums[k] = R[j];
            j++;
            k++;
        }

        return nums;
    }
}
