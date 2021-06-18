package y.w.study.alg.linkedlist;

/**
 * From Leetcode
 *
 * You are given the head of a singly linked-list. The list can be represented as:
 * l0 -> l1 -L ... ln-1, ln
 * Reorder it to
 * l0 -> ln -> l1 -> ln-1...
 */
public class ReorderLIst {
    public void reorderList(ListNode head) {
        if (head == null)
            return;

        // find the middle of linked list [Problem 876]
        // in 1->2->3->4->5->6 find 4
        // NOTE: this way using slow and fast to find the middle is very good.
        // a1 a2 a3 a4 a5
        //        |-slow - n / 2 + 1
        //               |-fast
        //
        // a1 a2 a3 a4 a5 a6
        //           |-slow - n/2 + 1
        //                 |-fast
        //
        // slow will end up with n/2 + 1
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // reverse the second part of the list [Problem 206]
        // convert 1->2->3->4->5->6 into 1->2->3->4 and 6->5->4
        // reverse the second half in-place
        ListNode prev = null, curr = slow, tmp;
        while (curr != null) {
            tmp = curr.next;

            curr.next = prev;
            prev = curr;
            curr = tmp;
        }

        // merge two sorted linked lists [Problem 21]
        // merge 1->2->3->4 and 6->5->4 into 1->6->2->5->3->4
        ListNode first = head, second = prev;
        while (second.next != null) {
            tmp = first.next;
            first.next = second;
            first = tmp;

            tmp = second.next;
            second.next = first;
            second = tmp;
        }
    }
}
