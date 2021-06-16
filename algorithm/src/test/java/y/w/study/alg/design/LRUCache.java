package y.w.study.alg.design;

import java.util.HashMap;

public class LRUCache {
    private int capacity;
    private Node header;
    private Node tail;

    HashMap<Integer, Node> lru = new HashMap<>();

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.header = new Node(0, 0);
        this.tail = new Node(0, 0);
        tail.left = header;
        header.right = tail;
    }

    public int get(int key) {
        if (lru.containsKey(key)) {
            Node node = lru.get(key);

            remove(node);
            addFirst(node);
            return lru.get(key).value;
        } else {
            return -1;
        }
    }

    public void put(int key, int value) {
        Node node;
        if (lru.containsKey(key)) {
            node = lru.get(key);
            node.value = value;
            remove(node);
        } else {
            if (lru.size() == capacity) {
                lru.remove(tail.left.key);
                remove(tail.left);
            }
            node = new Node(key, value);
        }
        addFirst(node);
        lru.put(key, node);
    }

    // Always add at the header
    private void addFirst(Node node) {
        node.left = header;
        node.right = header.right;
        header.right.left = node;
        if (tail.left == header) tail.left = node;
        header.right = node;
    }

    private void remove(Node node) {
        node.left.right = node.right;
        node.right.left = node.left;
    }

    private class Node {
        public int key;
        public int value;
        public Node left;
        public Node right;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }
}
