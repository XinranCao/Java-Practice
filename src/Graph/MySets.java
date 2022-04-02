package Graph;

import java.util.*;

/**
 * 并查集的代替结构
 * 用于实现kruskal算法
 * 
 */
public class MySets {

    // 记录某一节点所位于的节点集合（能与该节点连接到一起的所有节点的集合）
    public HashMap<GraphNode, List<GraphNode>> setMap;

    // 初始情况下，每个节点所位于的节点集合只有自己
    public MySets(Collection<GraphNode> collection) {
        setMap = new HashMap<>();
        for (GraphNode curr : collection) {
            List<GraphNode> set = new ArrayList<>();
            set.add(curr);
            setMap.put(curr, set);
        }
    }

    /**
     * 判断两个节点是否在同一节点集合内
     * 
     * @param from 节点1
     * @param to   节点2
     * @return
     */
    public boolean isSameSet(GraphNode from, GraphNode to) {
        // 分别找到两个节点所在的集合
        List<GraphNode> fromSet = setMap.get(from);
        List<GraphNode> toSet = setMap.get(to);
        // 判断两个集合是否为同一集合
        return fromSet == toSet;
    }

    /**
     * 将两个节点所在的集合合并起来，并更新setMap中节点与集合的关联信息
     * 
     * @param from
     * @param to
     */
    public void union(GraphNode from, GraphNode to) {
        List<GraphNode> fromSet = setMap.get(from);
        List<GraphNode> toSet = setMap.get(to);

        for (GraphNode node : toSet) {
            // 将第二个节点所在的集合中的所有节点，添加到第一个节点所在的集合中
            fromSet.add(node);
            // 更新setMap，将第二个节点所关联的集合更改为合并完成后的集合
            setMap.put(node, fromSet);
        }
    }
}
