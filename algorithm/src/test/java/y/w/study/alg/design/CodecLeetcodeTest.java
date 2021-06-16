package y.w.study.alg.design;

import org.junit.Test;
import y.w.study.alg.graph.TreeNode;

public class CodecLeetcodeTest {
    @Test
    public void test() {
        TreeNode n4 = new TreeNode(4, null, null);
        TreeNode n5 = new TreeNode(5, null, null);
        TreeNode n2 = new TreeNode(2, null, null);
        TreeNode n3 = new TreeNode(3, n4, n5);
        TreeNode root = new TreeNode(1, n2, n3);

        CodecLeetcode codec = new CodecLeetcode();

        String res = codec.serialize(root);

        System.out.println(res);
    }

    @Test
    public void test2() {
        TreeNode n4 = new TreeNode(4, null, null);
        TreeNode n5 = new TreeNode(5, null, null);
        TreeNode n3 = new TreeNode(3, null, null);
        TreeNode n2 = new TreeNode(2, n3, n4);
        TreeNode root = new TreeNode(1, n2, n5);

        CodecLeetcode codec = new CodecLeetcode();

        String res = codec.serialize(root);

        System.out.println(res);
    }

}
