package y.w.study.alg.array;

import java.util.*;
import java.util.stream.*;
import java.io.*;

public class SubarraySumQuery {
    /**
     * Given an array of integers, arr, calculate sum(a,b) = sum of range arr[a..b]
     * , a and b are inclusive.
     *
     * 1. Construct a prefix sum array in O(n) time:
     *    sum[k] = sum of range arr[0..k-1]
     *
     * 2. Calculate sum(a,b) in O(1)
     *    sum(3,6) = sum(0,6) - sum(0,2)
     *    sum(a,b) = sum[b] - sum[a-1], assuming a(0, -1) = 0
     *
     * i.e.
     *     arr index:    0   1   2   3   4   5   6   7
     *           arr:    1   3   4   8   6   1   4   2
     * prefix index : -1 0   1   2   3   4   5   6   7
     *   prefix sum : 0  1   4   8  16  22  23  27  29  sum[k] = arr[0..k]
     */
    public int calcSumQuery(int[] arr, int a, int b) {
        int[] prefix = new int[arr.length];

        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum = sum + arr[i];
            prefix[i] = sum;
        }

        return prefix[b] - (a > 0 ? prefix[a - 1] : 0);
    }
}
