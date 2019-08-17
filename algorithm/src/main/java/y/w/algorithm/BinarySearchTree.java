package y.w.algorithm;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The credit goes to the authors, Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, and Clofford Stein, of the book "Introduction to Algorithms".
 *
 * I studied the book and translated the pseudo codes to Java code.
 *
 * Binary search trees: for any node x, the keys in the left subtree of x are
 * at most x.key, and keys in the right subtree of x are at least x.key.
 * <p>
 * Different BST can represent the same set of values.
 * <p>
 * The worst case running time for most BST operations is proportional to the
 * height of the tree.
 * <p>
 * BST property:
 * <p>
 * Let x be a node in BST. If y is a node in the left subtree of x, then y.key <= x.key.
 * if y is a node in the right subtree of x, then y.key >= x.key.
 * </p>
 * <p>
 * Inorder Tree Walk: print out all the keys in BST in sorted order by
 * simple recursive algorithm_saved.
 * <p>
 * Preorder Tree Walk: prints root before the values in either subtree.
 * <p>
 * Postorder Tree Walk: prints root after the values iin either subtree.
 * </p>
 * <p>
 * Theorem: if x is the root of n-node subtree, then the call InOrderTreeWalk
 * takes thetha(n) time.
 *
 * <p>
 *     Order Statistics is by default supported. All lines related to it has comment "Dynamic Order Statistics"
 * </p>
 *
 * @author ywang
 * Date: 4/30/2019
 */
@SuppressWarnings({"rawtypes","unchecked"}) // for cast to Comparable
public class BinarySearchTree<T extends Comparable<T>> implements Sort<T>
{
    public final static Node NIL = Node.NIL;

    Node<T> root = (Node<T>) NIL;
    int  size = 0;

    public BinarySearchTree(Node<T> root)
    {
        this.root = root;
    }

    public BinarySearchTree()
    {
    }

    /**
     * @return an array of all the elements in ascending order.
     */
    public T[] toArray()
    {
        T[] allTs = (T[]) Array.newInstance(Comparable.class, this.size);

        Node<T> x = minimumOf(this.root);
        for (int i = 0; i < this.size; i++)
        {
            allTs[i] = x.value;
            x = successorOf(x);
        }
        return toArray();
    }

    public void inOrderWalk(Node<T> x)
    {
        if (x != NIL)
        {
            inOrderWalk(x.left);
            System.out.println(x.value);
            inOrderWalk(x.right);
        }
    }

    /**
     * Search for key k starting from node x.
     *
     * @param x
     * @param k
     * @return
     */
    public Node<T> search(Node<T> x, T k)
    {
        if (x == NIL || x.compareTo(k) == 0)
        {
            return x;
        }
        if (x.compareTo(k) > 0)
        {
            return search(x.left, k);
        }
        else
        {
            return search(x.right, k);
        }
    }

    /**
     * Search for key k starting from node x.
     *
     * @param x
     * @param k
     * @return
     */
    public Node<T> iterativeSearch(Node<T> x, T k)
    {
        while (x != NIL && x.compareTo(k) != 0)
        {
            if (x.compareTo(k) > 0)
            {
                x = x.left;
            }
            else
            {
                x = x.right;
            }
        }
        return x;
    }

    /**
     * Search for key k starting from root.
     *
     * @param k
     * @return
     */
    public Node<T> search(T k)
    {
        return search((Node<T>)this.root, k);
    }

    public Node<T> minimumOf(Node<T> x)
    {
        while (x.left != NIL)
            x = x.left;
        return x;
    }

    public Node<T> maximumOf(Node<T> x)
    {
        while (x.right != NIL)
            x = x.right;
        return x;
    }

    /**
     * Successor of node x is the node with the smallest key greater than x.key.
     * <p>
     * if x's right subtree is nonempty, then the successor of x is just the leftmost
     * node in x's right tree.
     * <p>
     * if x's right subtree is empty, then the successor y is the lowest ancestor of x
     * whose left child is also an ancestor of x.
     *
     * @param x
     * @return
     */
    public Node<T> successorOf(Node<T> x)
    {
        if (x.right != NIL)
        {
            return minimumOf(x.right);
        }
        Node y = x.p;
        while (y != NIL && x == y.right)
        {
            x = y;
            y = y.p;
        }
        return y;
    }

    public void insert(Node<T> z)
    {
        Node<T> y = NIL;
        Node<T> x = (Node<T>) this.root;

        while (x != NIL)
        {
            // Dynamic Order Statistics
            x.size++;

            y = x;
            if (z.compareTo(x) < 0)
            {
                x = x.left;
            }
            else
            {
                x = x.right;
            }
        }
        z.p = y;
        if (y == NIL)
        {
            this.root = z; // tree is empty
        }
        else if (z.compareTo(y) < 0)
        {
            y.left = z;
        }
        else
        {
            y.right = z;
        }

        this.size++;

        // Dynamic Order Statistics
        z.size = 1;
    }

    /**
     * Insert key k.
     *
     * @param k
     * @return node of the key
     */
    public Node<T> insert(T k)
    {
        Node<T> node = new Node(k);
        insert(node);
        return node;
    }

    /**
     * Called by delete method to decrement size up to te root.
     * @param x
     */
    protected void decrementNodeSizeUpward(Node<T> x)
    {
        Node<T> y = x.p;

        while (y != NIL)
        {
            y.size--;
            y = y.p;
        }
    }

    /**
     * Deleting node z:
     *
     * <p>Case 1: If z has no children, then we simply remove it by modifying its parent to replace
     * z with NIL as its child.
     *
     * <p>Case 2: If z has just one child, then we elevate that child to take z’s position in the tree
     * by modifying z’s parent to replace z by z’s child.
     *
     * <p>If z has two children, then we find z’s successor y—which must be in z’s right
     * subtree—and have y take z’s position in the tree. The rest of z’s original right
     * subtree becomes y’s new right subtree, and z’s left subtree becomes y’s new
     * left subtree. This case is the tricky one because, as we shall see, it matters
     * whether y is z’s right child.
     */
    public void delete(Node<T> z)
    {
        // Dynamic Order Statistics
        decrementNodeSizeUpward(z);

        if (z.left == NIL)
        {
            transplant(z, z.right);
        }
        else if (z.right == NIL)
        {
            transplant(z, z.left);
        }
        else
        {
            Node<T> y = minimumOf(z.right);
            if (y.p != z)
            {
                transplant(y, y.right);
                y.right = z.right;
                y.right.p = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.p = y;
        }
        this.size--;
    }

    protected void transplant(Node<T> u, Node<T> v)
    {
        if (u.p == NIL)
        {
            this.root = v;
        }
        else if (u == u.p.left)
        {
            u.p.left = v;
        }
        else
        {
            u.p.right = v;
        }
        v.p = u.p;
    }

    public int size()
    {
        return size;
    }

    public Node<T> root()
    {
        return root;
    }

    @Override
    public void sort(T[] array)
    {
        for (T t : array)
        {
            insert(t);
        }
    }

    protected void leftRotate(Node<T> x)
    {
        Node<T> y = x.right;
        x.right = y.left;   // turn y’s left subtree into x’s right subtree

        if (y.left != NIL)
        {
            y.left.p = x;
        }

        y.p = x.p;  // link x’s parent to y

        if (x.p == NIL)
        {
            this.root = y;
        }
        else if (x == x.p.left)
        {
            x.p.left = y;
        }
        else
        {
            x.p.right = y;
        }

        y.left = x; // put x on y’s left
        x.p = y;

        // Dynamic Order Statistics
        y.size = x.size;
        x.size = x.left.size + x.right.size + 1;
    }

    protected void rightRotate(Node<T> x)
    {
        Node<T> y = x.left;
        x.left = y.right;   // turn y’s left subtree into x’s right subtree

        if (y.right != NIL)
        {
            y.right.p = x;
        }

        y.p = x.p;  // link x’s parent to y

        if (x.p == NIL)
        {
            this.root = y;
        }
        else if (x == x.p.right)
        {
            x.p.right = y;
        }
        else
        {
            x.p.left = y;
        }

        y.right = x; // put x on y’s right
        x.p = y;

        // Dynamic Order Statistics
        y.size = x.size;
        x.size = x.left.size + x.right.size + 1;
    }

    /**
     * Dynamic Order Statistics
     *
     * @param x Root of the subtree
     * @param i 1<i<n, the ith smallest element in the Order Statistics Tree
     * @return the ith smallest element in the subtree rooted at x
     */
    public Node<T> select(Node<T> x, int i)
    {
        int r = x.left.size + 1;
        if ( i == r )
            return x;
        else if (i < r)
            return select(x.left, i);
        else
            return select(x.right, i - r);
    }

    /**
     * Dynamic Order Statistics
     *
     * @param i The ith smallest element in the Order Statistics Tree
     * @return the ith smallest element in the entire tree
     */
    public Node<T> select(int i)
    {
        return (Node<T>) select(this.root, i);
    }

    /**
     * @param x node
     * @return  the rank of node x
     */
    public int rank(Node<T> x)
    {
        int r = x.left.size + 1;
        Node<T> y = x;
        while (y != this.root)
        {
            if (y == y.p.right)
                r = r + y.p.left.size + 1;
            y = y.p;
        }

        return r;
    }

    /**
     * In Order Iterator with next() returning Node<T> of value of type T.
     *
     * @return
     */
    public InOrderIterator iterator() {
        return new InOrderIterator(this);
    }


    /**
     * Left First Iterator with next() returning the Node<T>
     *
     * @param node starting node
     * @return
     */
    public LeftPathIterator leftPathIterator(Node<T> node) {
        return new LeftPathIterator(this, node);
    }

    /**
     * Right First Iterator with next() returning the Node<T>
     *
     * @param node starting node
     * @return
     */
    public RightPathIterator rightPathIterator(Node<T> node) {
        return new RightPathIterator(this, node);
    }

    private class InOrderIterator implements Iterator<Node<T>>
    {
        protected BinarySearchTree<T> bst = null;
        protected Node<T> currentNode = null;

        protected int i = 0;
        public boolean hasNext()  { return i < bst.size; }
        public void    remove() { throw new UnsupportedOperationException();  }

        public InOrderIterator(BinarySearchTree<T> bst)
        {
            this.bst = bst;

            // Locate the minimum node of the entire tree.
            this.currentNode = bst.minimumOf(bst.root);
        }

        /**
         * @return Node<T> of value of type T
         */
        public Node<T> next() {
            if (!hasNext()) throw new NoSuchElementException();

            Node<T> x = currentNode;
            i++;
            currentNode = successorOf(x);

            return x;
        }
    }

    /**
     * Walk the tree along the left nodes starting from a node.
     */
    private class LeftPathIterator implements Iterator<Node<T>> {
        BinarySearchTree<T> bst = null;
        Node<T> currentNode = null;

        public boolean hasNext() { return currentNode != BinarySearchTree.NIL; }
        public void    remove() { throw new UnsupportedOperationException();  }

        public LeftPathIterator(BinarySearchTree<T> bst, Node<T> startingNode)
        {
            this.bst = bst;

            this.currentNode = startingNode;
        }

        public Node<T> next() {
            if (!hasNext()) throw new NoSuchElementException();

            Node<T> x = currentNode;
            currentNode = x.left;

            return x;
        }
    }

    /**
     * Walk the tree along the right nodes starting from a node.
     */
    private class RightPathIterator implements Iterator<Node<T>> {
        BinarySearchTree<T> bst = null;
        Node<T> currentNode = null;

        public boolean hasNext() { return currentNode != BinarySearchTree.NIL; }
        public void    remove() { throw new UnsupportedOperationException();  }

        public RightPathIterator(BinarySearchTree<T> bst, Node<T> startingNode)
        {
            this.bst = bst;

            this.currentNode = startingNode;
        }

        public Node<T> next() {
            if (!hasNext()) throw new NoSuchElementException();

            Node<T> x = currentNode;
            currentNode = x.right;

            return x;
        }
    }
}
