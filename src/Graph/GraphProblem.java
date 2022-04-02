package Graph;

import java.util.*;
import java.util.Map.*;

public class GraphProblem {

    /**
     * dijstra算法的辅助函数，用于找出：在目前仍未处理完毕的节点中，head到哪一节点距离最短。
     * 
     * @param distanceMap // 记录从head出发到各个节点的最短距离
     * @param doneSet     // 记录已经处理完毕的节点
     * @return
     */
    public static GraphNode getCLosestNode(HashMap<GraphNode, Integer> distanceMap, HashSet<GraphNode> doneSet) {

        GraphNode minNode = null;
        int minValue = Integer.MAX_VALUE;
        for (Entry<GraphNode, Integer> entry : distanceMap.entrySet()) {
            // 如果这一节点的距离小于目前找到的最短距离，并且该节点仍未处理完毕
            // 将最短距离更新，并将找出的节点更新
            if (minValue > entry.getValue() && !doneSet.contains(entry.getKey())) {
                minValue = entry.getValue();
                minNode = entry.getKey();
            }
        }
        return minNode;
    }

    /**
     * dijstra算法，计算从head节点出发，到达图中所有节点的最短路径（有向图，可以有loop）
     * 
     * @param head
     * @return
     */
    public static HashMap<GraphNode, Integer> dijstra(GraphNode head) {
        if (head == null) {
            return null;
        }

        // 最终返回的map，记录从head节点到所有节点的最短路径
        HashMap<GraphNode, Integer> distanceMap = new HashMap<>();
        // 记录已经处理完毕的node
        HashSet<GraphNode> doneSet = new HashSet<>();
        // 从head到head的距离为0
        distanceMap.put(head, 0);

        // 从distanceMap中找出：在还未处理过的node中，从head到哪一节点路径最短
        GraphNode minNode = getCLosestNode(distanceMap, doneSet);
        while (minNode != null) {
            // 找出这个节点的所有edge
            for (Edge edge : minNode.edges) {
                if (!distanceMap.containsKey(edge.to)) {
                    // 如果edge指向的下一节点还没有信息，将最短路径更新
                    distanceMap.put(edge.to, distanceMap.get(minNode) + edge.weight);
                } else {
                    // 如果已经有信息，对比已记录的最短路径和目前新找到的路径，更新为更短的路径
                    distanceMap.put(edge.to,
                            Math.min(distanceMap.get(edge.to), distanceMap.get(minNode) + edge.weight));
                }
            }
            // 将刚才处理完的node加入到处理完毕set中
            doneSet.add(minNode);
            // 找出下一个该处理的节点
            minNode = getCLosestNode(distanceMap, doneSet);
        }

        return distanceMap;
    }

    /**
     * edge weight的比较器，从小到大排列
     */
    public static class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    /**
     * prim算法，返回最小生成树中的所有edge（无向图，edge只有weight）
     * 
     * @param graph
     * @return
     */
    public static Set<Edge> prim(Graph graph) {

        // 最终输出的edge集合
        Set<Edge> res = new HashSet<>();
        // 记录已经处理过的节点
        HashSet<GraphNode> set = new HashSet<>();
        // 从小到大排列所有edge
        PriorityQueue<Edge> queue = new PriorityQueue<>(new EdgeComparator());

        // 此循环是为了避免图中有完全割裂的节点集合。
        // 比如图中共用1 2 3 4 5，5个节点，其中 1-3-5 三个节点互相连接，2-4 两个节点与他们不连接。
        for (GraphNode node : graph.nodes.values()) {
            // 如果还没处理过这个节点
            if (!set.contains(node)) {
                set.add(node); // 标记为已处理
                for (Edge edge : node.edges) {
                    // 将这个节点所有的edge放入queue中
                    queue.add(edge);
                }
                // 每次取出最小的edge处理
                while (!queue.isEmpty()) {
                    Edge currEdge = queue.poll();
                    // 如果这个edge相连的另一个节点还没有处理过
                    if (!set.contains(currEdge.to)) {
                        // 将另一个节点所有的edge放入queue中
                        for (Edge newEdge : currEdge.to.edges) {
                            queue.add(newEdge);
                        }
                        // 将另一个节点更新为已处理
                        set.add(currEdge.to);
                        // 将这个edge放入最小生成树的edge集合中
                        res.add(currEdge);
                    }
                }
            }
        }
        return res;
    }

    /**
     * kruskal算法，返回最小生成树中的所有edge（无向图，edge只有weight）
     * 
     * @param graph
     * @return
     */
    public static Set<Edge> kruskal(Graph graph) {

        // 最终输出的edge集合
        Set<Edge> res = new HashSet<>();
        // 并查集的替代方法，用于追踪某个节点所在的集合（所有能与此节点连接在一起的节点的集合）
        MySets myset = new MySets(graph.nodes.values());

        // 从小到大排列所有edge
        PriorityQueue<Edge> queue = new PriorityQueue<>(new EdgeComparator());
        for (Edge curr : graph.edges) {
            queue.add(curr);
        }

        Edge curr = null;
        // 每次取出最小的edge
        while (!queue.isEmpty()) {
            curr = queue.poll();
            // 如果这个edge的两端不在同一个集合中
            if (!myset.isSameSet(curr.from, curr.to)) {
                res.add(curr); // 将这个edge放入到最小生成树的edge集合中
                myset.union(curr.from, curr.to); // 将edge两端的节点所在的集合融合成一个大集合
            }
        }
        return res;
    }

    /**
     * 拓扑遍历（要求图无loop）
     * 例：编译的时候需要找到所有依赖的包，以及这些包依赖的包，先编译dependancies。
     * 拓扑遍历就是这个顺序的逆序
     * 
     * @param graph
     * @return
     */
    public static List<GraphNode> topologySort(Graph graph) {
        // 用来记录每个节点有多少条入路
        HashMap<GraphNode, Integer> inMap = new HashMap<>();
        // 先进先出，用来处理所有入路为0的节点
        Queue<GraphNode> zeroInQueue = new LinkedList<>();

        // 先将所有节点的入路信息填写，并找出最初一批入路为0的节点
        for (GraphNode curr : graph.nodes.values()) {
            inMap.put(curr, curr.in);
            if (curr.in == 0) {
                zeroInQueue.add(curr);
            }
        }

        // 最终输出的拓扑遍历顺序
        List<GraphNode> res = new ArrayList<>();
        GraphNode curr = null;
        while (!zeroInQueue.isEmpty()) {
            curr = zeroInQueue.poll(); // 从入路为0的节点queue中取出最旧的一个
            res.add(curr); // 加到最终顺序中
            for (GraphNode next : curr.nexts) {
                // 更新入路map中这个节点的所有子节点的入路信息（记录 - 1）
                inMap.put(next, inMap.get(next) - 1);
                // 如果这些子节点入路为0，放入queue中等待处理
                if (inMap.get(next) == 0) {
                    zeroInQueue.add(next);
                }
            }
        }
        return res;
    }

    /**
     * 从node开始深度优先遍历
     * 
     * @param node
     */
    public static void DFS(GraphNode node) {
        System.out.print("DFS: [ ");
        if (node == null) {
            return;
        }

        // 先进后出，用栈实现宽度优先
        Stack<GraphNode> stack = new Stack<>();
        // 用来记录已经遍历过的node，防止循环（图中可能有loop）
        HashSet<GraphNode> set = new HashSet<>();

        stack.add(node);
        set.add(node);
        System.out.print(node.value + " ");

        GraphNode curr = null;
        while (!stack.isEmpty()) {
            curr = stack.pop();
            for (GraphNode next : curr.nexts) {
                if (!set.contains(next)) {
                    stack.add(curr); // 如果子节点还未查找过，将父节点重新放回stack中
                    stack.add(next);
                    set.add(next);
                    System.out.print(next.value + " ");
                    break;
                }
            }
        }
        System.out.println("]");
    }

    /**
     * 从node开始宽度优先遍历
     * 
     * @param node
     */
    public static void BFS(GraphNode node) {
        System.out.print("BFS: [ ");
        if (node == null) {
            return;
        }
        // 先进先出，用queue实现宽度优先
        Queue<GraphNode> queue = new LinkedList<>();
        // 用来记录已经遍历过的node，防止循环（图中可能有loop）
        HashSet<GraphNode> set = new HashSet<>();

        // 从node开始
        queue.add(node);
        set.add(node);

        GraphNode curr = null;
        while (!queue.isEmpty()) {
            curr = queue.poll();
            System.out.print(curr.value + " ");
            for (GraphNode next : curr.nexts) {
                if (!set.contains(next)) {
                    queue.add(next);
                    set.add(next);
                }
            }
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        GraphNode node_0 = new GraphNode(0);
        GraphNode node_1 = new GraphNode(1);
        GraphNode node_2 = new GraphNode(2);
        GraphNode node_3 = new GraphNode(3);
        GraphNode node_4 = new GraphNode(4);
        GraphNode node_5 = new GraphNode(5);
        GraphNode node_6 = new GraphNode(6);
        GraphNode node_7 = new GraphNode(7);

        node_0.in = 0;
        node_0.out = 3;
        node_1.in = 1;
        node_1.out = 1;
        node_2.in = 2;
        node_2.out = 1;
        node_3.in = 3;
        node_3.out = 1;
        node_4.in = 1;
        node_4.out = 2;
        node_5.in = 1;
        node_5.out = 1;
        node_6.in = 1;
        node_6.out = 1;
        node_7.in = 1;
        node_7.out = 0;

        Edge edge_1 = new Edge(12, node_0, node_1);
        Edge edge_3 = new Edge(25, node_0, node_3);
        Edge edge_4 = new Edge(10, node_0, node_4);
        Edge edge_12 = new Edge(2, node_1, node_2);
        Edge edge_25 = new Edge(3, node_2, node_5);
        // Edge edge_32 = new Edge(1, node_3, node_2); //loop
        Edge edge_43 = new Edge(30, node_4, node_3);
        Edge edge_46 = new Edge(15, node_4, node_6);
        Edge edge_53 = new Edge(4, node_5, node_3);
        Edge edge_67 = new Edge(5, node_6, node_7);

        node_0.nexts.add(node_1);
        node_0.nexts.add(node_3);
        node_0.nexts.add(node_4);
        node_0.edges.add(edge_1);
        node_0.edges.add(edge_3);
        node_0.edges.add(edge_4);

        node_1.nexts.add(node_2);
        node_1.edges.add(edge_12);

        node_2.nexts.add(node_5);
        node_2.edges.add(edge_25);

        // loop
        // node_3.nexts.add(node_2);
        // node_3.edges.add(edge_32);

        node_4.nexts.add(node_3);
        node_4.nexts.add(node_6);
        node_4.edges.add(edge_43);
        node_4.edges.add(edge_46);

        node_5.nexts.add(node_3);
        node_5.edges.add(edge_53);

        node_6.nexts.add(node_7);
        node_6.edges.add(edge_67);

        Graph graph = new Graph();
        graph.nodes.put(0, node_0);
        graph.nodes.put(1, node_1);
        graph.nodes.put(2, node_2);
        graph.nodes.put(3, node_3);
        graph.nodes.put(4, node_4);
        graph.nodes.put(5, node_5);
        graph.nodes.put(6, node_6);
        graph.nodes.put(7, node_7);
        graph.edges.add(edge_1);
        graph.edges.add(edge_3);
        graph.edges.add(edge_4);
        graph.edges.add(edge_12);
        graph.edges.add(edge_25);
        // graph.edges.add(edge_32); //loop
        graph.edges.add(edge_43);
        graph.edges.add(edge_46);
        graph.edges.add(edge_53);
        graph.edges.add(edge_67);

        BFS(node_0);
        DFS(node_0);

        List<GraphNode> topology = topologySort(graph);
        System.out.print("topology: [ ");
        for (GraphNode node : topology) {
            System.out.print(node.value + " ");
        }
        System.out.println("]");

        Set<Edge> kruskal = kruskal(graph);
        System.out.print("kruskal: [ ");
        for (Edge edge : kruskal) {
            System.out.print(edge.weight + " ");
        }
        System.out.println("]");

        Set<Edge> prim = kruskal(graph);
        System.out.print("prim: [ ");
        for (Edge edge : prim) {
            System.out.print(edge.weight + " ");
        }
        System.out.println("]");

        HashMap<GraphNode, Integer> dijstra = dijstra(node_0);
        System.out.print("dijstra: [ ");
        for (Entry<GraphNode, Integer> entry : dijstra.entrySet()) {
            System.out.print(entry.getKey().value + "(" + entry.getValue() + ") ");
        }
        System.out.println("]");
    }
}
