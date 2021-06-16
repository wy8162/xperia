package y.w.study.alg.graph;

public class Node<T> {
    private final T val;
    private boolean visited = false;
    private boolean connected = false;
    private int prev, postv;

    public Node(T value) {
        this.val = value;
    }

    public T getVal() {
        return val;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public int getPrev() {
        return prev;
    }

    public void setPrev(int prev) {
        this.prev = prev;
    }

    public int getPostv() {
        return postv;
    }

    public void setPostv(int postv) {
        this.postv = postv;
    }
}
