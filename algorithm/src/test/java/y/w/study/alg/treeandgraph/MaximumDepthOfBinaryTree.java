package y.w.study.alg.treeandgraph;

import org.junit.Test;
import y.w.study.alg.graph.TreeNode;

public class MaximumDepthOfBinaryTree {

    @Test
    public void test() {
        TreeNode root = TreeSerializer.deserialize("3,9,20,#,#,15,7,#,#,#,#");

        System.out.println(maxDepth(root));
    }

    public int maxDepth(TreeNode root) {
        if (root == null) return 0;

        int leftHeight = maxDepth(root.left);
        int rightHeight= maxDepth(root.right);

        return Math.max(leftHeight, rightHeight) + 1;
    }
}
