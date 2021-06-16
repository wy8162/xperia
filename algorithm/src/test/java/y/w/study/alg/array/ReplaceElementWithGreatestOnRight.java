package y.w.study.alg.array;

import org.junit.Test;

public class ReplaceElementWithGreatestOnRight {
    @Test
    public void test() {
        int[] arr;

        arr = replaceElements(new int[]{});
        arr = replaceElements(new int[]{400});
        arr = replaceElements(new int[]{400, 300});
        arr = replaceElements(new int[]{10, 400,300});
        arr = replaceElements(new int[]{17, 18, 5, 4, 6, 1});
    }

    public int[] replaceElements(int[] arr) {
        if (arr == null || arr.length == 0) return arr;

        int max = arr[arr.length - 1];
        int last = arr.length - 1;
        for (int i = arr.length - 1; i > 0; i--) {
            arr[last] = arr[i - 1];
            arr[i - 1] = max;

            max = Math.max(arr[last], max);
        }

        arr[last] = -1;

        return arr;
    }
}
