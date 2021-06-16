package y.w.study.alg.graph;

/**
 * Given the root of a binary tree, flatten the tree into a "linked list":
 * - The "linked list" should use the same TreeNode class where the right child pointer points
 *   to the next node in the list and the left child pointer is always null.
 *
 * - The "linked list" should be in the same order as a pre-order traversal of the binary tree.
 */

import java.util.ArrayList;
import java.util.List;

public class FlattenBinaryTree {
    public void flattenIterative(TreeNode root) {
        List<TreeNode> list = new ArrayList<>();

        walk(root, list);

        if (list.size() == 0) return;

        TreeNode header = new TreeNode();
        TreeNode tail = header;
        for (TreeNode node : list) {
            tail.right = node;
            tail = node;
            node.left = null;
            node.right = null;
        }
    }

    public void flattenRecursive(TreeNode root) {
        flatten(root.left);
    }

    private TreeNode flatten(TreeNode root) {
        if (root == null || root.left == null && root.right == null) return root;

        TreeNode left = flatten(root.left);
        TreeNode right = flatten(root.right);

        if (left != null) {
            left.right = root.right;
            root.right = root.left;
            root.left = null;
        }

        return right == null ? left : right;
    }

    /**
     * chsampath
     * @param root
     */
    public void leetcodeFlatten(TreeNode root) {
        TreeNode temp;
        if(root==null || (root.left==null && root.right==null))
            return ;
        leetcodeFlatten(root.left);
        leetcodeFlatten(root.right);
        temp=root.left;
        while(temp!=null && temp.right!=null)
            temp=temp.right;
        temp.right=root.right;
        root.right=root.left;
        root.left=null;

    }

    private void walk(TreeNode node, List<TreeNode> list) {
        if (node != null) {
            list.add(node);
            walk(node.left, list);
            walk(node.right, list);
        }
    }
}
