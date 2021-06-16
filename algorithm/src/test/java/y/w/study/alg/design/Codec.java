package y.w.study.alg.design;

import java.util.LinkedList;
import y.w.study.alg.graph.TreeNode;

public class Codec {
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        return levelOrderTravesal(root);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data == null || data.isEmpty()) return null;

        String[] nodes = data.split(",");
        return null;
    }

    private String levelOrderTravesal(TreeNode node) {
        LinkedList<TreeNode> list = new LinkedList<>();
        StringBuilder sb = new StringBuilder();

        boolean begin = true;

        list.addLast(node);
        while (list.size() > 0) {
            TreeNode n = list.removeFirst();

            if (!begin) sb.append(",");
            else begin = false;

            sb.append(n.val);

            if (n.left != null) list.addLast(n.left);
            else sb.append(",null");

            if (n.right != null) list.addLast(n.right);
            else sb.append(",null");
        }

        return sb.toString();
    }
}
