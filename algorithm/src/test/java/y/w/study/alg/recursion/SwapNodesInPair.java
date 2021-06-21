package x.y.study.alg.recursion;

public class SwapNodesInPair {
    public static class Node {
        public final int val;
        public Node next;

        public Node(int val) { this.val = val; }
    }

    public static Node swap(Node head) {
        return helper(head, null);
    }

    /**
     * e1->e2->e3->e4->e5->e6
     * ==>
     * e2->e1->e4->e3->e6->e5
     */
    private static Node helper(Node node, Node prev) {
        if (node == null || node.next == null) return null;

        Node next = node.next;
        if (prev != null) prev.next = next;
        node.next = next.next;
        next.next = node;

        helper(node.next, node);

        return next;
    }

    /**
     * Better amd simpler.
     */
    private static Node helperBackward(Node head) {
        if (head == null) return null;
        if (head.next == null) return head;

        Node first = head;
        Node second= head.next;

        first.next = helperBackward(second.next);

        second.next = first;

        return second;
    }

    private static Node createLinkedList(int[] arr) {
        Node h = new Node(0);
        Node t = h;

        for (int i = 0; i < arr.length; i++) {
            t.next = new Node(arr[i]);
            t = t.next;
        }

        return h.next;
    }

    public static void main(String[] args) {
        Node root = createLinkedList(new int[]{1,2,3,4});

        Node n = root;
        
        while (n != null) {
            System.out.println(n.val);
            n = n.next;
        }

        System.out.println("swapped:");

        n = swap(root);

        while (n != null) {
            System.out.println(n.val);
            n = n.next;
        }

        root = createLinkedList(new int[]{1,2,3,4,5});
        n = helperBackward(root);

        System.out.println("swapped:");
        while (n != null) {
            System.out.println(n.val);
            n = n.next;
        }
    }
}
