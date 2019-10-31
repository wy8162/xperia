package y.w.leetcode;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * You are given two non-empty linked lists representing two non-negative integers.
 * The digits are stored in reverse order and each of their nodes contain a single
 * digit. Add the two numbers and return it as a linked list.
 *
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 *
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * Explanation: 342 + 465 = 807.
 *
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 *
 * @author ywang
 * @date 10/30/2019
 */
public class AddingTwoNumbers
{
    private static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val)
        {
            this.val = val;
            this.next = null;
        }

        /**
         * Append val to the end of the linked list.
         *
         * @param val
         * @return the last ListNode so that append method can be chained.
         */
        public ListNode append(int val)
        {
            // Find the last of the number chain
            ListNode n = this;
            while (n.next != null)
                n = n.next;
            n.next = new ListNode(val);
            return n;
        }

        public ListNode(int ...nums)
        {
            if (nums == null) return;
            this.val = nums[0];
            this.next = null;

            ListNode current = this;
            for (int i = 1; i < nums.length; i++)
                current.append(nums[i]);
        }
    }

    private static class NumberIterator implements Iterator<ListNode>
    {
        ListNode n1, n2;

        // Always point to the current node
        ListNode current1, current2;

        public NumberIterator(ListNode n1, ListNode n2)
        {
            this.n1 = n1;
            this.n2 = n2;
            this.current1 = n1;
            this.current2 = n2;
        }

        @Override
        public boolean hasNext()
        {
            return current1 != null || current2 != null;
        }

        @Override
        public ListNode next()
        {
            int v1 = current1 != null ? current1.val : 0;
            int v2 = current2 != null ? current2.val : 0;

            current1 = current1.next;
            current2 = current2.next;

            return new ListNode(v1 + v2);
        }
    }

    public static ListNode addTwoNumbers(ListNode n1, ListNode n2)
    {
        if (n1 == null && n2 == null)
            return null;

        NumberIterator ni = new NumberIterator(n1, n2);

        ListNode result = ni.next();
        int carry = result.val / 10;
        result.val = result.val % 10;
        while (ni.hasNext())
        {
            ListNode valueNode = ni.next();
            result.append((valueNode.val + carry) % 10);
            carry = (valueNode.val + carry) / 10;
        }
        if (carry > 0)
            result.append(carry);

        return result;
    }

    public static void main(String[] args)
    {
        ListNode num1 = new ListNode(2);
        num1.append(4).append(3);
        ListNode num2 = new ListNode(5, 6, 4);

        // Expect 7, 0, 8
        ListNode result = addTwoNumbers(num1, num2);

        // Expect 7, 0, 3, 1
        result = addTwoNumbers(new ListNode(2,4,3), new ListNode(5,6,9));
    }
}
