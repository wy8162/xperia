package y.w.study.alg.linkedlist;

import org.junit.Test;

public class AddTwoNumbers {
    @Test
    public void test() {
        ListNode result;

        result = addTwoNumbers(makeList(new int[]{2,4,3}), makeList(new int[]{5,6,4}));
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;

        ListNode header = new ListNode();
        ListNode tail = header;

        int carry = 0;

        ListNode n1 = l1, n2 = l2;

        while (true) {
            int value = (n1 != null ? n1.val : 0) + carry;
            value += (n2 != null ? n2.val : 0);

            tail = appendNode(tail, value % 10);

            carry = value / 10;

            n1 = n1 != null ? n1.next : null;
            n2 = n2 != null ? n2.next : null;

            if (n1 == null && n2 == null)
                break;
        }

        if (carry > 0) {
            appendNode(tail, carry);
        }

        return header.next;
    }

    private ListNode appendNode(ListNode tail, int value) {
        ListNode node = new ListNode(value, null);
        tail.next = node;
        node.next = null;
        return node;
    }

    private ListNode makeList(int[] nums) {
        ListNode header = new ListNode();
        ListNode tail = header;

        for (int i : nums)
            tail = appendNode(tail, i);

        return header.next;
    }
}
