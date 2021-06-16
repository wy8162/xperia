package y.w.study.alg.design;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import y.w.study.alg.graph.TreeNode;

/**
 * Implement the BSTIterator class that represents an iterator over the
 * in-order traversal of a binary search tree (BST):
 *
 * - BSTIterator(TreeNode root) Initializes an object of the BSTIterator class.
 *   The root of the BST is given as part of the constructor. The pointer should
 *   be initialized to a non-existent number smaller than any element in the BST.
 *
 * - boolean hasNext() Returns true if there exists a number in the traversal to
 *   the right of the pointer, otherwise returns false.
 *
 * - int next() Moves the pointer to the right, then returns the number at the pointer.
 *
 * My Implementation:
 *
 * Time: O(1)
 * Space: O(n), n is the size of the tree.
 *
 */
public class BSTIterator {
    private Deque<TreeNode> nodes = new ArrayDeque<>();
    Iterator<TreeNode> it;

    public BSTIterator(TreeNode root) {
        inorderWalk(root);
        it = nodes.iterator();
    }

    public int next() {
        TreeNode node = it.next();
        return node.val;
    }

    public boolean hasNext() {
        return it.hasNext();
    }

    private void inorderWalk(TreeNode node) {
        if (node == null) return;
        if (node.left == null && node.right == null) {
            nodes.addLast(node);
            return;
        }

        inorderWalk(node.left);
        nodes.addLast(node);
        inorderWalk(node.right);
    }
}
