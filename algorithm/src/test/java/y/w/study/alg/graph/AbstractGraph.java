package y.w.study.alg.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbstractGraph<T extends Comparable<T>> {
    protected final List<GraphNode<T>> vertices = new ArrayList<>();
    protected final List<GraphEdge<T>> edges = new ArrayList<>();
    protected final Map<T, GraphNode<T>> index = new HashMap<>();

    public AbstractGraph() {}

    public GraphNode<T> addNode(T t) {
        return addIfAbsent(t);
    }

    public GraphNode<T> getNode(T t) {
        return index.getOrDefault(t, null);
    }

    public void addEdge(T u, T v) {
        GraphNode<T> un = addIfAbsent(u);
        GraphNode<T> vn = addIfAbsent(v);

        un.neighbors.add(vn);

        edges.add(new GraphEdge<>(un, vn));
    }

    protected GraphNode<T> addIfAbsent(T t) {
        if (index.containsKey(t)) return index.get(t);

        GraphNode<T> u = new GraphNode<>(t);

        vertices.add(u);
        index.put(t, u);

        return u;
    }

    protected void reset(int d, int f, State s) {
        for (GraphNode<T> v : vertices) {
            v.d = d;
            v.f = f;
            v.state = s;
            v.pi = null;
        }
    }

    protected void reset() {
        reset(0, 0, State.WHITE);
    }
}
