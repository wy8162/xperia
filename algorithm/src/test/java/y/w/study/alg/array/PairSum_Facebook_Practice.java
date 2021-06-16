package y.w.study.alg.array;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.junit.Test;

public class PairSum_Facebook_Practice {

    @Test
    public void test() {

        assertEquals(4, numberOfWays(new int[]{1, 5, 3, 3, 3}, 6));
        assertEquals(2, numberOfWays(new int[]{1, 2, 3, 4, 3}, 6));
    }


    int numberOfWays(int[] arr, int k) {
        // Add any helper functions you may need here

        // Write your code here
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int count = 0;
        Map<Integer, Integer> tracking = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            int delta = k - arr[i];

            if (tracking.containsKey(delta)) {
                count += tracking.get(delta);
            }

            tracking.putIfAbsent(arr[i], 0);
            tracking.put(arr[i], tracking.get(arr[i]) + 1);
        }

        return count;
    }

    @Test
    public void testCandy() {
        int r = maxCandies(new int[]{2, 1, 7, 4, 2}, 3);
    }

    /**
     * Time: O(nlog(n) + klog(k)) => O(nlog(n)) Space: O(k)
     *
     * @param arr
     * @param k
     * @return
     */
    int maxCandies(int[] arr, int k) {
        // Write your code here
        if (arr == null || arr.length == 0 || k <= 0) {
            return 0;
        }

        int sum = 0;

        Arrays.sort(arr); // Time: O(nlog(n))

        TreeMap<Integer, Integer> map = new TreeMap<>(); // Space: O(k)
        // The idea is this:
        // 1. Sort arr, which has Time O(nlog(n))
        // 2. Create a new array, choices, of size k and copy the top k items to choices
        // 3. Then start eating the greatest ones.
        // 4. After eat the top one, calculate y = floor(x/2)
        // 5. Compare y with the smallest one, choice[0], if it is greater, then put it to sorted TreeMap.

        // Time: O(k * log(k))
        // TreeMap: O(log(k)) for all operations - search, insertion, deletion, etc.
        //
        // Space: O(4)
        int eatingIndex = 0;
        for (int i = 0; i < k; i++) {
            int magic;
            int magicValue = i;

            int value = arr[arr.length - 1 - eatingIndex];

            if (map.size() > 0 && map.lastKey() > value) {
                Entry<Integer, Integer> entry = map.pollLastEntry();

                magic = entry.getKey();

                sum += magic;

                magic = Math.floorDiv(magic, 2);
                magicValue = entry.getValue();
            } else {
                sum += value;

                magic = Math.floorDiv(value, 2);

                eatingIndex++;
            }

            if (magic > arr[arr.length - k]) {
                map.put(magic, magicValue);
            }
        }

        return sum;
    }

    @Test
    public void testAnything() {
        int[] result;

        result = findMedian(new int[]{5, 15, 1, 3});
        result = findMedian(new int[]{1, 2});
    }

    // Add any helper functions you may need here
    private void insertAndShift(int[] arr, int start, int end, int insertionPoint, int value) {
        if (insertionPoint < 0) {
            insertionPoint = -insertionPoint - 1;
        }

        int i = end;
        while (i > insertionPoint) {
            arr[i] = arr[i - 1];
            i--;
        }

        arr[i] = value;

    }

    int[] findMedian(int[] arr) {
        // Write your code here
        // Algorithm:
        // 1. the array [0..i] needs to be maintained in sorted order so that it is easy to calculate the median numbers.
        // 2. When move to i + 1
        // 3. Use index = Arrays.binarySearch(arr, arr[i + 1])
        // 4. Insert arr[i+1] to index position, shiftting 1 space to the right
        // 5. Calculate the median value
        if (arr == null || arr.length <= 1) {
            return arr;
        }

        int[] result = new int[arr.length];
        result[0] = arr[0]; // Base case, no need to calculate anything.

        for (int i = 1; i < arr.length; i++) {
            int index = Arrays.binarySearch(arr, 0, i, arr[i]);

            insertAndShift(arr, 0, i, index, arr[i]);

            int n = i + 1;
            result[i] = (arr[n / 2 + n % 2 - 1] + arr[n / 2]) / 2;
        }

        return result;
    }

    @Test
    public void slowSum() {
        int[] nums = new int[]{4, 2, 1, 3};

        int result = getTotalTime(nums);
    }

    // Add any helper functions you may need here
    private int penalty = 0;

    private void calc(int[] arr, int index) {
        if (index <= 0) {
            return;
        }

        int sum = arr[index] + arr[index - 1];
        arr[index - 1] = sum;

        penalty += sum;

        calc(arr, index - 1);
    }

    int getTotalTime(int[] arr) {
        // Write your code here
        // Requiremen: worst penalty means the largest penalty, which includes individual penanlties which is the largest at that operation.
        // Input: array if N numbers. So each sum should be of the sum of the top two largest numbers in the list, which means that one of them
        // will be the penalty.
        // Algorithm:
        // sort arr
        // startSum = arr[arr.length - 1]
        // def calc(arr, index, penalty, startSum)
        //    if (index < 0) return 0;
        //    4 3 2 1 -> 7 + 9 + 10
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0];
        }
        if (arr.length == 2) {
            return arr[0] + arr[1];
        }

        Arrays.sort(arr);

        calc(arr, arr.length - 1);

        return penalty;
    }

    @Test
    public void test4() {
        int[] res;

        // Expect: {2, 4, 4, 5}
        res = getMilestoneDays(new int[]{100, 200, 300, 400, 500}, new int[]{300, 800, 1000, 1400});

        // [4, 6, 10]
        res = getMilestoneDays(new int[]{10,20,30,40,50,60,70,80,90,100}, new int[]{100,200,500});

        // {5, 4, 2, 3, 2}
        res = getMilestoneDays(new int[]{700, 800, 600, 400, 600, 700}, new int[]{3100, 2200, 800, 2100, 1000});

    }

     class Pair implements Comparable<Pair> {
        public final int milestone;
        public final int index;

        public Pair(int milestone, int index) {
            this.milestone = milestone;
            this.index = index;
        }

        @Override
        public int compareTo(Pair o1) {
            return this.milestone - o1.milestone;
        }
    }

    int[] getMilestoneDays(int[] revenues, int[] milestones) {
        // Write your code here
        int[] res = new int[milestones.length];
        Arrays.fill(res, -1);

        Pair[] pairs = new Pair[milestones.length];
        for (int i = 0; i < milestones.length; i++)
            pairs[i] = new Pair(milestones[i], i);

        Arrays.sort(pairs);

        int total = 0;
        for (int i = 0; i < revenues.length; i++) {
            total += revenues[i];

            int index = Arrays.binarySearch(pairs, new Pair(total, 0));

            if (index >= 0) {
                for (int j = index; j >= 0; j--) {
                    int k = pairs[j].index;
                    if (res[k] < 0 && total >= pairs[j].milestone) res[k] = i + 1;
                }
            } else if (index < -1 ){
                index = -index - 1;

                for (int j = index - 1; j >= 0; j--) {
                    int k = pairs[j].index;
                    if (res[k] < 0 && total >= pairs[j].milestone)
                        res[k] = i + 1;
                }
            }
        }

        return res;
    }
}
