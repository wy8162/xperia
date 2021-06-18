package y.w.study.alg.treeandgraph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import y.w.study.alg.graph.TreeNode;

public class BinaryTreeRightSideView {

    public List<Integer> rightSideViewByLevel(TreeNode root) {
        if (root == null) {
            return new ArrayList<Integer>();
        }

        ArrayDeque<TreeNode> nextLevel = new ArrayDeque() {{
            offer(root);
        }};
        ArrayDeque<TreeNode> currLevel = new ArrayDeque();
        List<Integer> rightside = new ArrayList();

        TreeNode node = null;
        while (!nextLevel.isEmpty()) {
            // prepare for the next level
            currLevel = nextLevel.clone();
            nextLevel.clear();

            while (!currLevel.isEmpty()) {
                node = currLevel.poll();

                // add child nodes of the current level
                // in the queue for the next level
                if (node.left != null) {
                    nextLevel.offer(node.left);
                }
                if (node.right != null) {
                    nextLevel.offer(node.right);
                }
            }

            // The current level is finished.
            // Its last element is the rightmost one.
            if (currLevel.isEmpty()) {
                rightside.add(node.val);
            }
        }
        return rightside;
    }

    public List<Integer> rightSideViewSentinel(TreeNode root) {
        if (root == null) {
            return new ArrayList<Integer>();
        }

        Queue<TreeNode> queue = new LinkedList() {{
            offer(root);
            offer(null);
        }};
        TreeNode prev, curr = root;
        List<Integer> rightside = new ArrayList();

        while (!queue.isEmpty()) {
            prev = curr;
            curr = queue.poll();

            while (curr != null) {
                // add child nodes in the queue
                if (curr.left != null) {
                    queue.offer(curr.left);
                }
                if (curr.right != null) {
                    queue.offer(curr.right);
                }

                prev = curr;
                curr = queue.poll();
            }

            // the current level is finished
            // and prev is its rightmost element
            rightside.add(prev.val);
            // add a sentinel to mark the end
            // of the next level
            if (!queue.isEmpty()) {
                queue.offer(null);
            }
        }
        return rightside;
    }
}
