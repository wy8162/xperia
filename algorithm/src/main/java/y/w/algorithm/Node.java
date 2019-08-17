package y.w.algorithm;

@SuppressWarnings({"rawtypes","unchecked"}) // for cast to Comparable
public class Node<T extends Comparable<T>>
{
    public final static Node NIL = new Node(-1);

    public static final boolean BLACK = false;
    public static final boolean RED = true;

    public T value;
    public Node<T> p, left, right;

    // is only used in RedBlackBST. TRUE=Red, FALSE=Black
    public boolean color;

    // Dynamic Order Statistics
    // Needed to implement Dynamic Order Statistics
    // Variable size is to track the number of nodes in the subtree rooted at this node inclusive.
    protected int size = 0;

    public Node(T value)
    {
        this(value, NIL, NIL, NIL);
    }

    public Node(T value, Node left, Node right, Node p)
    {
        this.value = value;
        this.color = BLACK;
        this.left  = left;
        this.right = right;
        this.p     = p;
    }

    @SuppressWarnings({"rawtypes","unchecked"}) // for cast to Comparable
    public int compareTo(Node<T> node)
    {
        // The following is copied from Java HashMap implementation.
        // It's not necessary for this usage, an overkill.
        //
        //Class<?> kc = SortHelper.comparableClassFor(this.value);

        //
        // This, ((Comparable<T>)this.value).compareTo(value), doesn't work.
        // Adapted the approach from Java 8 implementation of HashMap.
        //
        //return SortHelper.compareComparables(kc, this.value, node.value);
        return ((Comparable) this.value).compareTo(node.value);
    }

    @SuppressWarnings({"rawtypes","unchecked"}) // for cast to Comparable
    public int compareTo(T value)
    {
        // The following is copied from Java HashMap implementation.
        // It's not necessary for this usage, an overkill.
        //
        //Class<?> kc = SortHelper.comparableClassFor(this.value);

        //
        // This, ((Comparable<T>)this.value).compareTo(value), doesn't work.
        // Adapted the approach from Java 8 implementation of HashMap.
        //
        //return SortHelper.compareComparables(kc, this.value, value);
        return ((Comparable) this.value).compareTo(value);
    }

    boolean isRed()
    {
        return this.color;
    }

    boolean isBlack()
    {
        return ! this.color;
    }

    public void setRed()
    {
        this.color = RED;
    }

    void setBlack()
    {
        this.color = BLACK;
    }
}