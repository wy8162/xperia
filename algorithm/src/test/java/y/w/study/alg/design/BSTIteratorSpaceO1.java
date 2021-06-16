package y.w.study.alg.design;

import java.util.Stack;
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
public class BSTIteratorSpaceO1 {
    private Stack<TreeNode> nodes = new Stack<>();

    public BSTIteratorSpaceO1(TreeNode root) {
        inorderWalkLeft(root);
    }

    public int next() {
        TreeNode node = nodes.pop();

        int val = node.val;

        if (node.right != null) inorderWalkLeft(node.right);

        return val;
    }

    public boolean hasNext() {
        return nodes.size() > 0;
    }

    private void inorderWalkLeft(TreeNode node) {
        while(node != null) {
            nodes.push(node);
            node = node.left;
        }
    }
}
