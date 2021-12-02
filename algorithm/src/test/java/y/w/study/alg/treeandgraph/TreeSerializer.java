package y.w.study.alg.treeandgraph;

import java.util.ArrayList;
import java.util.LinkedList;
import y.w.study.alg.graph.TreeNode;

/**
 * Copied from:
 *
 * https://www.programcreek.com/2014/05/leetcode-serialize-and-deserialize-binary-tree-java/
 * <p>
 * Level order
 */
public class TreeSerializer {

    // Encodes a tree to a single string.
    public static String serialize(TreeNode root) {
        ArrayList<String> list = new ArrayList<>();
        LinkedList<TreeNode> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            TreeNode h = q.poll();
            if (h == null) {
                list.add("#");
            } else {
                list.add("" + h.val);
                q.offer(h.left);
                q.offer(h.right);
            }
        }

        return String.join(",", list);
    }

    // Decodes your encoded data to tree.
    public static TreeNode deserialize(String data) {
        String[] arr = data.split(",");
        if (arr[0].equals("#")) {
            return null;
        }

        TreeNode root = new TreeNode(Integer.parseInt(arr[0]));
        LinkedList<TreeNode> q = new LinkedList<>();
        q.offer(root);

        int i = 1;

        while (!q.isEmpty()) {
            TreeNode h = q.poll();
            if (h != null) {
                TreeNode left = null;
                if (!arr[i].equals("#")) {
                    left = new TreeNode(Integer.parseInt(arr[i]));
                }
                h.left = left;
                q.offer(left);
                i++;

                TreeNode right = null;
                if (!arr[i].equals("#")) {
                    right = new TreeNode(Integer.parseInt(arr[i]));
                }
                h.right = right;
                q.offer(right);
                i++;
            }
        }

        return root;
    }
}
