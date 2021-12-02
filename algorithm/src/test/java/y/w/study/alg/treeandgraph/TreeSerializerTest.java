package y.w.study.alg.treeandgraph;

import org.junit.Test;
import y.w.study.alg.graph.TreeNode;

public class TreeSerializerTest {
    @Test
    public void test() {
        TreeNode root = new TreeNode(3);
        TreeNode n9   = new TreeNode(9);
        TreeNode n20  = new TreeNode(20);
        TreeNode n15  = new TreeNode(15);
        TreeNode n7   = new TreeNode(7);

        root.right = n20;
        root.left  = n9;
        n20.right  = n7;
        n20.left   = n15;

        System.out.println(TreeSerializer.serialize(root));

        TreeNode nroot = TreeSerializer.deserialize("3,9,20,#,#,15,7,#,#,#,#");
    }

}
