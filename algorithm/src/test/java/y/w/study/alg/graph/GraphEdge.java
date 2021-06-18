package y.w.study.alg.graph;

public class GraphEdge<T extends Comparable<T>> {
    public final GraphNode<T> u;
    public final GraphNode<T> v;

    public GraphEdge(GraphNode<T> u, GraphNode<T> v) {
        this.u = u;
        this.v = v;
    }
}
