package y.w.study.alg.graph;

import java.util.ArrayList;
import java.util.List;

public class GraphNode<T extends Comparable<T>> implements Comparable<T> {
    public final T val;
    public int d; // Distance from source node, or time first visited
    public int f; // Time visited second time
    public GraphNode<T> pi; // Parent node
    public State state;
    public boolean connected = false;

    public final List<GraphNode<T>> neighbors = new ArrayList<>();

    public GraphNode(T val) {
        this.val = val;
    }

    @Override
    public int compareTo(T o) {
        return this.val.compareTo(o);
    }
}
