package y.w.study.alg.linkedlist;

public class CustomDoubleLinkedList<T> {
    private int size = 0;
    private final Node<T> H;
    private final Node<T> T;

    public CustomDoubleLinkedList(int size) {
        this.size = size;
        this.H = new Node<T>(null);
        this.T = new Node<T>(null);
        this.H.right = this.T;
        this.T.left  = this.H;
    }

    public int size() { return size; }

    public void addFirst(Node<T> n) {
        n.right = H.right;
        n.left  = H;
        H.right.left = n;
        H.right = n;

        size++;
    }

    /**
     * Retrieve and remove the first.
     *
     * @return null if none.
     */
    public Node<T> poll() {
        if (size == 0) return null;

        return remove(H.right);
    }

    /**
     * Retrieve and remove the last.
     *
     * @return null if none.
     */
    public Node<T> pollLast() {
        if (size == 0) return null;

        return remove(T.left);
    }

    private Node<T> remove(Node<T> n) {
        if (n == null || n.left == null || n.right == null) return null;

        n.right.left = n.left;
        n.left.right = n.right;

        n.left = null;
        n.right= null;

        size--;

        return n;
    }

    private class Node<T> {
        public final T val;
        public Node<T> left = null, right = null;

        public Node(T val) {
            this.val = val;
        }
    }
}
