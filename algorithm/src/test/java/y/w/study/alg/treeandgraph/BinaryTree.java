package y.w.study.alg.treeandgraph;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import y.w.study.alg.graph.TreeNode;

public class BinaryTree {
    // DFS: in-order, pre-order, post-order

    // In-Order
    // Walk tree in sorted ascending order, etc.
    public void inOrderTravesal(TreeNode node) {
        inOrderTravesal(node.left);
        System.out.println(node.val);
        inOrderTravesal(node.right);
    }

    // Pre-Order
    // - Creating a copy of the tree
    // - To get prefix expression of an expression tree...Polish notation
    public void preOrderTravesal(TreeNode node) {
        System.out.println(node.val);
        preOrderTravesal(node.left);
        preOrderTravesal(node.right);
    }

    // Post-Order - the root is always the last one.
    public void postOrderTravesal(TreeNode node) {
        postOrderTravesal(node.left);
        postOrderTravesal(node.right);
        System.out.println(node.val);
    }

    // BFS - Level Order Traversal
    public void levelOrderTravesal(TreeNode node) {
        Queue<TreeNode> Q = new LinkedList<>();

        if (node == null) return;

        Q.add(node);
        while (!Q.isEmpty()) {
            TreeNode n = Q.remove();

            System.out.println(n.val);

            Optional.of(n.left).ifPresent(Q::add);
            Optional.of(n.right).ifPresent(Q::add);
        }
    }
}
