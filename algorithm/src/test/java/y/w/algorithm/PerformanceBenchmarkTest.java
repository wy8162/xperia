package y.w.algorithm;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Compare the performance of all these algorithms
 *
 * @author ywang
 * Date: 5/24/2019
 */
public class PerformanceBenchmarkTest
{
    private Integer[] nums = null;

    @Before
    public void setUp()
    {
        List<Integer> range = IntStream.range(0, 1000).boxed()
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(range);

        nums = new Integer[range.size()];
        for (int i = 0; i < nums.length; i++)
            nums[i] = range.get(i);
    }

    @Test
    public void testComparePerformance()
    {
        int i = 0;
        long time;
        Sort<Integer> sort;

        Pair[] results = new Pair[8];

        sort = new HeapSort2<>();
        time = timeIt(sort, nums.clone());
        results[i++] = new Pair(time, "HeapSort2");

        sort = new InsertionSort<Integer>();
        time = timeIt(sort, nums.clone());
        results[i++] = new Pair(time, "InsertionSort");

        sort = new MergeSort<Integer>();
        time = timeIt(sort, nums.clone());
        results[i++] = new Pair(time, "MergeSort");

        sort = new QuickSort<Integer>();
        time = timeIt(sort, nums.clone());
        results[i++] = new Pair(time, "QuickSort");

        sort = new RedBlackBST<>();
        time = timeIt(sort, nums.clone());
        results[i++] = new Pair(time, "RedBlackBST");

        sort = new BinarySearchTree<>();
        time = timeIt(sort, nums.clone());
        results[i++] = new Pair(time, "BinarySearchTree");

        sort = new HeapSort<>();
        time = timeIt(sort, nums.clone());
        results[i++] = new Pair(time, "HeapSort");

        time = timeTreeMap(nums.clone());
        results[i++] = new Pair(time, "Java TreeMap");

        HeapSort<Pair> heapSort = new HeapSort<>();

        heapSort.sort(results);

        for (Pair p : results)
            System.out.println(p.toString());

    }

    private <T extends Comparable<T>> long timeIt(Sort<T> sort, T[] array)
    {
        long startTime = System.nanoTime();
        sort.sort(array);

        return System.nanoTime() - startTime;
    }

    private long timeTreeMap(Integer[] array)
    {
        Map<Integer, Integer> map = new TreeMap<>();

        long startTime = System.nanoTime();
        for (Integer i : array)
            map.put(i, i);

        return System.nanoTime() - startTime;
    }

    private class Pair implements Comparable<Pair>
    {
        Long timeSlapsed;
        String algo;

        public Pair(long timeSlapsed, String algo)
        {
            this.timeSlapsed = timeSlapsed;
            this.algo = algo;
        }

        @Override
        public int compareTo(Pair o)
        {
            return timeSlapsed.compareTo(o.timeSlapsed);
        }

        @Override
        public String toString()
        {
            return String.format("%s -> %d", algo, timeSlapsed);
        }
    }
}
