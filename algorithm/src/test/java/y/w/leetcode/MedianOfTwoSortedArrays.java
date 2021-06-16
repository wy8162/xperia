package y.w.leetcode;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.
 *
 * Find the median of the two sorted arrays. The overall run time complexity
 * should be O(log (m+n)).
 *
 * You may assume nums1 and nums2 cannot be both empty.
 *
 * Example 1:
 * nums1 = [1, 3]
 * nums2 = [2]
 *
 * The median is 2.0
 *
 * Example 2:
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 *
 * The median is (2 + 3)/2 = 2.5
 */
public class MedianOfTwoSortedArrays
{
    /**
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

    public double calculateMedian(int[] nums1, int[] nums2)
    {
        int[] nums = merge(nums1, nums2);
        if (nums.length % 2 == 0)
        {
            // Number of elements is even
            return ((double) nums[nums.length / 2 - 1] + (double) nums[nums.length / 2]) / 2.0;
        }
        return nums[nums.length / 2];
    }

    @Test
    public void testMedianOfTwoSortedArrays()
    {
        assertEquals(2.0, calculateMedian(new int[]{1, 3}, new int[]{2}));
        assertEquals(2.5, calculateMedian(new int[]{1, 2}, new int[]{3,4}));
    }
}
