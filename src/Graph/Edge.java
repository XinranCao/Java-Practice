package Graph;

public class Edge {
    public int weight;
    public GraphNode from;
    public GraphNode to;

    public Edge(int w, GraphNode f, GraphNode t) {
        weight = w;
        from = f;
        to = t;
    }
}
