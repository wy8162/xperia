package y.w.study.alg.linkedlist;

/**
 * Merge two sorted linked lists and return it as a sorted list. The list should be made by splicing together the nodes of the first two lists.
 */
public class MergeSortedList {

    /**
     * Leetcode
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        else if (l2 == null) {
            return l1;
        }
        else if (l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        }
        else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }

    }

    public ListNode merge1(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode header = new ListNode(0);
        ListNode p = header;

        ListNode t1 = l1, t2 = l2;

        while (t1 != null && t2 != null) {
            if (t1.val >= t2.val) {
                p.next = t2;
                t2 = t2.next;
            } else {
                p.next = t1;
                t1 = t1.next;
            }
            p = p.next;
        }

        p.next = t1 == null ? t2 : t1;

        return header.next;
    }

    public ListNode merge2(ListNode l1, ListNode l2) {
        if (l1 == null) return l1;
        if (l2 == null) return l2;

        ListNode h;
        if (l1.val < l2.val) {
            h = l1;
            l1 = l1.next;
        } else {
            h = l2;
            l2 = l2.next;
        }

        ListNode t = h;

        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                t.next = l1;
                l1 = l1.next;
            } else {
                t.next = l2;
                l2 = l2.next;
            }
            t = t.next;
        }

        t.next = l1 != null ? l1 : l2;

        return h;
    }
}
