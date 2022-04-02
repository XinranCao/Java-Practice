package Graph;

import java.util.ArrayList;

public class GraphNode {
    public int value;
    public int in;
    public int out;
    public ArrayList<GraphNode> nexts;
    public ArrayList<Edge> edges;

    public GraphNode(int v) {
        value = v;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
