package y.w.algorithm;

/**
 * The credit goes to the authors, Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, and Clofford Stein, of the book "Introduction to Algorithms".
 *
 * I studied the book and translated the pseudo codes to Java code.
 *
 * Red Black Tree
 * <p>
 * A Red Black Tree is a binary search tree with one extra bit of storage per node: its
 * color, which can be either RED or BLACK. By constraining the node colors on any simple
 * path from the root to a leaf, red-black trees ensure that no such path is more than
 * twice as long as any other, so that the tree is approximately BALANCED.
 * </p>
 *
 * <p>
 * Each node of the tree now contains the attributes color, key, left, right and p.
 * </p>
 * Red-black tree properties:
 * <ul>
 * <li>Every node is either red or black</li>
 * <li>The root is black</li>
 * <li>Every leaf (NIL) is black</li>
 * <li>If a node is red, then both its children are black</li>
 * <li>For each node, all simple paths from the node to the descendant leaves contain the same number
 * of black nodes</li>
 * </ul>
 * <p>
 * Computation time: O(lgn)
 *
 * <p>
 *     Order Statistics is by default supported. All lines related to it has comment "Dynamic Order Statistics"
 * </p>
 *
 * @author ywang
 * Date: 5/7/2019
 * Time: 09:37
 */
@SuppressWarnings({"rawtypes","unchecked"}) // for cast to Comparable
public class RedBlackBST<T extends Comparable<T>> extends BinarySearchTree<T>
{
    public RedBlackBST(Node root)
    {
        super(root);
    }

    public RedBlackBST()
    {
        super();
    }

    private void insertFixup(Node z)
    {
        while (z.p.isRed())
        {
            if (z.p == z.p.p.left)
            {
                Node y = z.p.p.right;
                if (y.isRed())
                {
                    z.p.setBlack(); // Case 1
                    y.setBlack();   // Case 1
                    z.p.p.setRed(); // Case 1
                    z = z.p.p;               // Case 1
                }
                else
                {
                    if (z == z.p.right)
                    {
                        z = z.p;                 // Case 2
                        leftRotate(z);           // Case 2
                    }
                    z.p.setBlack(); // Case 3
                    z.p.p.setRed(); // Case 3
                    rightRotate(z.p.p);      // Case 3
                }
            }
            else
            {
                Node y = z.p.p.left;
                if (y.isRed())
                {
                    z.p.setBlack();
                    y.setBlack();
                    z.p.p.setRed();
                    z = z.p.p;
                }
                else
                {
                    if (z == z.p.left)
                    {
                        z = z.p;
                        rightRotate(z);
                    }
                    z.p.setBlack();
                    z.p.p.setRed();
                    leftRotate(z.p.p);
                }
            }
        }
        root.setBlack();
    }

    private void deleteFixUp(Node x)
    {
        Node w;

        while (x != NIL && x.isBlack())
        {
            if (x == x.p.left)
            {
                w = x.p.right;
                if (w.isRed()) // Case 1
                {
                    w.setBlack();
                    (x.p).setRed();
                    leftRotate(x.p);
                    w = x.p.right;
                }
                if (w.isBlack() && w.right.isBlack()) // Case 2
                {
                    w.setRed();
                    x = x.p;
                }
                else
                {
                    if (w.right.isBlack()) // Case 3
                    {
                        w.left.setBlack();
                        w.setRed();
                        rightRotate(w);
                        w = x.p.right;
                    }
                    // Case 4
                    w.color = x.p.color;
                    x.p.setBlack();
                    w.right.setBlack();
                    leftRotate(x.p);
                    x = this.root;
                }
            }
            else
            {
                w = x.p.left;
                if (w.isRed()) // Case 1
                {
                    w.setBlack();
                    x.p.setRed();
                    rightRotate(x.p);
                    w = x.p.left;
                }
                if (w.isBlack() && w.left.isBlack()) // Case 2
                {
                    w.setRed();
                    x = x.p;
                }
                else
                {
                    if (w.left.isBlack()) // Case 3
                    {
                        w.right.setBlack();
                        w.setRed();
                        leftRotate(w);
                        w = x.p.left;
                    }
                    // Case 4
                    w.color = x.p.color;
                    x.p.setBlack();
                    w.left.setBlack();
                    rightRotate(x.p);
                    x = this.root;
                }
            }
        }
        x.setBlack();
    }

    @Override
    public void insert(Node z)
    {
        super.insert(z);

        z.left = NIL;
        z.right = NIL;
        z.setRed();
        insertFixup(z);
    }

    @Override
    public void sort(T[] array)
    {
        for (T t : array)
        {
            insert(new Node(t));
        }
    }

    @Override
    public void delete(Node z)
    {
        // Dynamic Order Statistics
        decrementNodeSizeUpward(z);

        Node x;
        Node y = z;
        boolean original_y_color = y.color;

        if (z.left == NIL)
        {
            x = z.right;
            transplant(z, z.right);
        }
        else if (z.right == NIL)
        {
            x = z.left;
            transplant(z, z.left);
        }
        else
        {
            y = minimumOf(z.right);
            original_y_color = y.color;
            x = y.right;
            if (y.p == z)
            {
                x.p = y;
            }
            else
            {
                transplant(y, y.right);
                y.right = z.right;
                y.right.p = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.p = y;
            y.color = z.color;
        }

        if (original_y_color == Node.BLACK)
        {
            deleteFixUp(x);
        }

        this.size--;
    }
}
