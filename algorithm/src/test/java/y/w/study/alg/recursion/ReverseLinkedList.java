package x.y.study.alg.recursion;

public class ReverseLinkedList {
    public static class ListNode {
        public final int val;
        public ListNode next;

        public ListNode(int val) { this.val = val; }
    }

    /**
     * Iterative manner is simpler.
     *
     * Here is focusing on recursive way.
     */

    /**
     * Recursion Relation:
     *    a1->a2->a3->a4->a5 => a5->a4->a4->a3->a2->a1
     *
     *    Work backward.
     *
     * Base Case:
     *    head.next = null
     *    head = null
     */
    public static ListNode reverseLinkedList(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode n = reverseLinkedList(head.next);
        head.next.next = head;
        head.next = null;

        return n;
    }

    private static ListNode createLinkedList(int[] arr) {
        ListNode h = new ListNode(0);
        ListNode t = h;

        for (int i = 0; i < arr.length; i++) {
            t.next = new ListNode(arr[i]);
            t = t.next;
        }

        return h.next;
    }

    public static void main(String[] args) {
        ListNode root = createLinkedList(new int[]{1,2,3,4,5});
        ListNode n = reverseLinkedList(root);

        System.out.println("reversed:");
        while (n != null) {
            System.out.println(n.val);
            n = n.next;
        }
    }
}
