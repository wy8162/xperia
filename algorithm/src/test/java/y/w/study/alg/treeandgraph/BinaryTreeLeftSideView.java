package y.w.study.alg.treeandgraph;

import java.util.LinkedList;
import java.util.Queue;
import y.w.study.alg.graph.TreeNode;

public class BinaryTreeLeftSideView {

    int visibleTreeNodes(TreeNode root) {
        // Write your code here
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> Q = new LinkedList<>();
        Q.offer(root);
        Q.offer(null);

        int count = 0;
        while (!Q.isEmpty()) {
            TreeNode curr = Q.poll();

            while (curr != null) {
                if (curr.right != null) {
                    Q.offer(curr.right);
                }
                if (curr.left != null) {
                    Q.offer(curr.left);
                }

                curr = Q.poll();
            }

            count++;
            if (!Q.isEmpty()) {
                Q.offer(null);
            }
        }

        return count;
    }
}
