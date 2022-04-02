package Graph;

import java.util.*;

public class Graph {

    public HashMap<Integer, GraphNode> nodes;
    public HashSet<Edge> edges;

    public Graph() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }

}
