package y.w.study.alg.array;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * You are given two non-empty linked lists representing two non-negative integers. The
 * digits are stored in reverse order, and each of their nodes contains a single digit.
 * Add the two numbers and return the sum as a linked list. You may assume the two numbers
 * do not contain any leading zero, except the number 0 itself.
 *
 * Example 1:
 * Input: l1 = [2,4,3], l2 = [5,6,4]
 * Output: [7,0,8]
 * Explanation: 342 + 465 = 807.
 *
 * Example 2:
 * Input: l1 = [0], l2 = [0]
 * Output: [0]
 *
 * Example 3:
 * Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 * Output: [8,9,9,9,0,0,0,1]
 */
public class AddTwoNumber {

    @Test
    public void test() {
        List<Integer> result;

        result = addTwoNumbers(Arrays.asList(2,4,3), Arrays.asList(5,6,4));
        Assertions.assertThat(result).isEqualTo(Arrays.asList(7,0,8));

        result = addTwoNumbers(Arrays.asList(0), Arrays.asList(0));
        Assertions.assertThat(result).isEqualTo(Arrays.asList(0));

        result = addTwoNumbers(Arrays.asList(9,9,9,9,9,9,9), Arrays.asList(9,9,9,9));
        Assertions.assertThat(result).isEqualTo(Arrays.asList(8,9,9,9,0,0,0,1));
    }

    private LinkedList<Integer> addTwoNumbers(List<Integer> ns1, List<Integer> ns2) {
        LinkedList<Integer> nums = new LinkedList<>();

        Iterator<Integer> it1 = ns1.iterator();
        Iterator<Integer> it2 = ns2.iterator();

        int carry = 0;
        while (it1.hasNext() && it2.hasNext()) {
            Integer sum = it1.next() + it2.next() + carry;

            Integer mod = sum % 10;
            carry = sum / 10;

            nums.addLast(mod);
        }

        it1 = it1.hasNext() ? it1 : it2;
        while(it1.hasNext()) {
            Integer sum = it1.next() + carry;

            Integer mod = sum % 10;
            carry = sum / 10;

            nums.addLast(mod);
        }

        if (carry > 0)
            nums.addLast(carry);

        return nums;
    }

}
