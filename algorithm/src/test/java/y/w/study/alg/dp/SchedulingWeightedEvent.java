package y.w.study.alg.dp;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Given N jobs (start time, end time, and profit >= 0), find the maximum
 * profit subset of jobs such that no two jobs in the subset overlap.
 *
 * Algorithm:
 * 1. Sort the jobs according to the finish time.
 * 2. Apply the following algorithm
 *    findMaximumProfit(Job[] arr, n): // arr of n jobs
 *      a) if (n == 1) return arr[0]
 *      b) Return the maximum of following two profits
 *          i) maximum profit by excluding current job
 *             findMaximumProfit(arr, n-1)
 *          2) maximum profit bu including the current job
 *
 */
public class SchedulingWeightedEvent {
    private static int latestNonConflict(Job[] arr, int i) {
        for (int j = i - 1; j >= 0; j--) {
            if(arr[j].end <= arr[i - 1].start) return j;
        }
        return -1; // Non found
    }

    /**
     * @param arr of jobs, sorted according the end time
     * @param n of jobs to be processed starting from 0
     * @return profit
     */
    private static int findMaximumProfit(Job[] arr, int n) {
        if (n == 1) return arr[n - 1].profit;

        // Find profit when the current job is included
        int profitInclude = arr[n - 1].profit;
        int i = latestNonConflict(arr, n);
        if (i != -1) profitInclude += findMaximumProfit(arr, i + 1);

        // Find profit when the current job is excluded
        int profitExclude = findMaximumProfit(arr, n - 1);

        return Math.max(profitExclude, profitInclude);
    }

    /**
     * @param arr of jobs, sorted according to end time
     * @return profit
     */
    private static int findMaximumProfitDP(Job[] arr) {
        int[] profits = new int[arr.length];
        profits[0] = arr[0].profit;

        for (int i = 1; i < arr.length; i++) {
            // Include the current job
            int profitInclude = arr[i].profit;
            int j = latestNonConflict(arr, i + 1);
            if (j != -1) profitInclude += profits[j];

            profits[i] = profitInclude > profits[i - 1] ? profitInclude : profits[i -1];
        }

        return profits[arr.length - 1];
    }

    private static int computeMaximumProfit(Job[] arr, String version) {
        Arrays.sort(arr, Comparator.comparing(e -> e.end));

        return version.equals("recursive") ?
               findMaximumProfit(arr, arr.length) :
               findMaximumProfitDP(arr);
    }


    static class Job {
        int start, end, profit;

        public Job(int start, int end, int profit) {
            this.start = start;
            this.end = end;
            this.profit = profit;
        }
    }

    public static void main(String args[]) {
        Job[] jobs = new Job[]{
            new Job(3,10,20),
            new Job(1,2,50),
            new Job(6,19,100),
            new Job(2,100,200),
        };

        System.out.println("The maximum profit (recursive): " + computeMaximumProfit(jobs, "recursive"));
        System.out.println("The maximum profit (DP)       : " + computeMaximumProfit(jobs, "dp"));
    }
}
