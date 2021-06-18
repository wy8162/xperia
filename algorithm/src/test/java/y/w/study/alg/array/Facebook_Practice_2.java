package y.w.study.alg.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;

public class Facebook_Practice_2 {

    @Test
    public void test1() {
        float result = (float) (Math.log(1000000000.0) / Math.log(1.5));

        result = (float) Math.ceil(result);
    }

    @Test
    public void test2() {
        assert balancedSplitExists(new int[]{1, 5, 7, 1});
        assert !balancedSplitExists(new int[]{12, 7, 6, 7, 6});
        assert balancedSplitExists(new int[]{2, 1, 2, 5});
        assert !balancedSplitExists(new int[]{3, 6, 3, 4, 4});
    }

    boolean balancedSplitExists(int[] arr) {
        // Write your code here
        if (arr == null || arr.length <= 2) {
            return false;
        }

        Arrays.sort(arr);

        int i = 0, j = arr.length - 1;

        int lsum = arr[0], rsum = arr[arr.length - 1];
        while (i < j - 1) {
            if (lsum < rsum) {
                i++;
                lsum += arr[i];
            } else {
                j--;
                rsum += arr[j];
            }
        }

        for (int k = j; j < arr.length - 1; j++) {
            int r = Arrays.binarySearch(arr, 0, i + 1, arr[k]);

            if (r >= 0) {
                return false;
            }
        }

        return lsum == rsum;
    }


    class Sides {

        int a;
        int b;
        int c;

        Sides(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    @Test
    public void test3() {

    }

    // Add any helper functions you may need here
    private String convert(Sides s) {
        int[] sides = new int[3];
        sides[0] = s.a;
        sides[1] = s.b;
        sides[2] = s.c;

        Arrays.sort(sides);

        return Arrays.stream(sides).boxed().map(i -> i.toString()).collect(Collectors.joining("@"));
    }

    int countDistinctTriangles(ArrayList<Sides> arr) {
        // Write your code here
        if (arr == null || arr.size() == 0) {
            return 0;
        }

        Set<String> set = new HashSet<>();

        for (Sides s : arr) {
            set.add(convert(s));
        }

        return set.size();
    }

    @Test
    public void test4() {
        int res;

        res = minOperations(new int[]{3,1,2}); // expected 2
        res = minOperations(new int[]{1, 2, 5, 4, 3}); // expected 1
    }

    int minOperations(int[] arr) {
        // Write your code here
         int count = 0;
         while (prevPermutation(arr)) count++;

         return count;
    }

    private boolean prevPermutation(int[] arr) {
        int i = arr.length - 1;
        while (i > 0 && arr[i - 1] <= arr[i])
            i--;

        if (i <= 0)
            return false;

        int j = arr.length - 1;
        while (arr[j] >= arr[i - 1])
            j--;

        int temp = arr[i - 1];
        arr[i - 1] = arr[j];
        arr[j] = temp;

        j = arr.length - 1;
        while (i < j) {
            temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            i++;
            j--;
        }

        return true;
    }
}
