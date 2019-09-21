package y.w.effectivejava.skeletal.generics;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * GenericsTest
 *
 * @author ywang
 * @date 9/19/2019
 */
public class GenericsTest
{
    @Test
    public void testMax()
    {
        List<Integer> nums = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

        assertTrue(Max.max(nums).equals(10));
    }

    @Test
    public void testStack()
    {
        Stack<Integer> stack = new Stack<>();

        stack.push(1);

        assertTrue(stack.pop().equals(1));
    }

    @Test
    public void testStack2()
    {
        Stack<String> stack = new Stack<>();

        stack.push("1");

        assertTrue(stack.pop().equals("1"));
    }
}
