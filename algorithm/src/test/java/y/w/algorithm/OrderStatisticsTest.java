package y.w.algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;

/**
 * Order Statistics is supported by both BinarySearchTree and RedBlackBST.
 *
 * @author ywang
 * Date: 5/23/2019
 */
public class OrderStatisticsTest
{

    @Test
    public void testOrderStatistics()
    {
        List<Integer> range = IntStream.range(0, 500).boxed()
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(range);

        RedBlackBST<Integer> bst = new RedBlackBST<>();

        for (Integer n : range)
            bst.insert(n);

        for (int i = 1; i<500; i++)
        {
            Node<Integer> x = bst.select(i);
            assertTrue(x.value.compareTo(i - 1) == 0);
        }
    }
}
