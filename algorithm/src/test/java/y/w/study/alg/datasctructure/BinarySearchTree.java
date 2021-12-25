package y.w.study.alg.datasctructure;

public class BinarySearchTree {

    static class Tree {
        public static Node search(Node node, int value) {
            if (node == null) return null;
            if (node.value == value) return node;
            if (node.value > value)
                return search(node.l, value);
            else
                return search(node.r, value);
        }

        public static Node findMinimum(Node node) {
            if (node == null) return null;
            Node min = node;

            if (min.l != null) return findMinimum(min.l);
            return min;
        }

        public static void traverse(Node node) {
            if (node == null) return;
            traverse(node.l);
            System.out.println(node.value);
            traverse(node.r);
        }
    }

    static class Node {
        int value;
        Node parent;
        Node l;
        Node r;
    }
}
