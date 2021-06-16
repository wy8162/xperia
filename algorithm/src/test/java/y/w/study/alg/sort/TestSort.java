package y.w.study.alg.sort;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.Test;

public class TestSort {
    private int[] nums;

    @Before
    public void setup() {
        List<Integer> numList = IntStream.range(0, 200000)
            .boxed()
            .collect(Collectors.toList());

        Collections.shuffle(numList);

        nums = new int[numList.size()];

        int j = 0;
        for (Integer i : numList)
            nums[j++] = i;
    }

    @Test
    public void test() {
        long start = System.currentTimeMillis();
        new InsertionSort().sort(copyNums());
        System.out.println("Insertion SORT= " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        new SelectionSort().sort(copyNums());
        System.out.println("Selection SORT= " + (System.currentTimeMillis() - start));
    }

    private int[] copyNums() {
        int[] copy = new int[nums.length];

        for (int i=0; i< nums.length; i++)
            copy[i] = nums[i];

        return copy;
    }
}
