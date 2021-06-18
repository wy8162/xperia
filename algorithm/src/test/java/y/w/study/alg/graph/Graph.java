package y.w.study.alg.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public class Graph<T extends Comparable<T>> extends AbstractGraph<T> {

    List<GraphNode<T>> bfs(T t) {
        GraphNode<T> s = getNode(t);

        if (t == null || s == null) return null;

        List<GraphNode<T>> visited = new ArrayList<>();
        Deque<GraphNode<T>> Q = new ArrayDeque<>();

        reset();

        s.d = 0;
        s.pi = null;
        s.state = State.GRAY; // Discovered

        Q.push(s);

        while (!Q.isEmpty()) {
            GraphNode<T> u = Q.pop();

            // Process node u
            for (GraphNode<T> v : u.neighbors) {
                if (v.state == State.WHITE) {
                    // Process edge (u, v)
                    v.state = State.GRAY;
                    v.d = u.d + 1;
                    v.pi = u;
                    Q.push(v);
                }
            }
            u.state = State.BLACK;
            visited.add(u);
        }

        return visited;
    }

    /**
     * Search deeper whenever possible. The subgraph produced by DFS may be of
     * several trees. Each vertex ends up in exactly one DFS tree, so that these trees
     * are disjoint.
     *
     * When dfs() finishes, every node has discovery time u.d and finishing time u.f.
     *
     * Properties:
     * For any twi vertuces u and v, exactly one of the three conditions holds:
     * 1. the intervals [u.d, u.f] and [v.d, v.f] are entirely disjoint and neither
     *    u nore v is a descendant of the other in DFS forest.
     *
     * 2. the interval of [u.d, u.f] is contained entirely within the interval of
     *    [v.d, v.f], u is a descendant of v in the DFS tree.
     *
     * 3. the interval of [v.d, v.f] is contained entirely within the interval of
     *    [u.d, u.f], v is a descendant of u in the DFS tree.
     */
    private int time;
    public void dfs() {
        reset();

        time = 0; // Start

        for (GraphNode<T> u : vertices) { // Check each vertex
            if (u.state == State.WHITE)   // Process it if it is WHITE
                dfs_visit(u);
        }
    }

    public void dfs_visit(GraphNode<T> u) {
        time++; // White vertex i has just been discovered
        u.d = time;
        u.state = State.GRAY;

        for (GraphNode<T> v : u.neighbors) {
            // Process edge [u, v]
            // v.state = WHITE - tree edge
            // v.GRAY  = back edge, u back to v
            // v.BLACK = forward, u forward to v.
            if (v.state == State.WHITE) { // Process it if it is WHITE
                v.pi = u;
                dfs_visit(v);
            }
        }
        u.state = State.BLACK; // u is now finished.
        time++;
        u.f = time; // Time finishing
    }

    /**
     * Shortest Path:
     *
     * Bellman-Ford: O(|V| * |E|)
     *
     * Lemma: sub-paths of shortest paths are shortest paths.
     */
    public boolean bellmanFord(GraphNode<T> s, BiFunction<GraphNode<T>, GraphNode<T>, Integer> wf) {
        initSingleSource(s);

        for (int i = 1; i < vertices.size() - 1; i++) { // makes |V| - 1 passes
            for (GraphEdge<T> e : edges) {
                relax(e.u, e.v, wf);
            }
        }

        for (GraphEdge<T> e : edges) {
            if (e.v.d > e.u.d + wf.apply(e.u, e.v))
                return false;
        }

        return true;
    }

    /**
     * Dijkstra's Algorithm.
     *
     * @param s starting vertex
     * @param wf
     */
    public void dijkstra(GraphNode<T> s, BiFunction<GraphNode<T>, GraphNode<T>, Integer> wf) {
        initSingleSource(s);

        Set<GraphNode<T>> S = new HashSet<>();
        Set<GraphNode<T>> Q = new HashSet<>(vertices);

        while (!Q.isEmpty()) {
            GraphNode<T> u = extractMin(Q);
            S.add(u);

            for (GraphNode<T> v : u.neighbors) {
                relax(u, v, wf);
            }
        }
    }

    private GraphNode<T> extractMin(Set<GraphNode<T>> Q) {
        return null;
    }

    private void relax(GraphNode<T> u, GraphNode<T> v, BiFunction<GraphNode<T>, GraphNode<T>, Integer> wf) {
        int w = wf.apply(u, v);
        if (v.d > u.d + w) {
            v.d = u.d + w;
            v.pi = u;
        }
    }

    private void initSingleSource(GraphNode<T> s) {
        reset(Integer.MAX_VALUE, 0, State.WHITE);
        s.d = 0;
    }

    /**
     *
     * @param s
     * @param v
     * @return path from s to v
     */
    List<GraphNode<T>> getPath(T s, T v) {
       return getPath(getNode(s), getNode(v), null);
    }

    /**
     *
     * @param s
     * @param v
     * @param path pass null as initial value.
     * @return path from s to v
     */
    List<GraphNode<T>> getPath(GraphNode<T> s, GraphNode<T> v, List<GraphNode<T>> path) {
        if (path == null) {
            path = new ArrayList<>();
        }

        if (s == null || v == null) return path;

        if (s == v) path.add(s);
        else if (v.pi == null) return path; // No path from s to v
        else {
            getPath(s, v.pi, path);
            path.add(v);
        }

        return path;
    }
}
