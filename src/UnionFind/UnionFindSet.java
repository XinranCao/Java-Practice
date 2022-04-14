package UnionFind;

import java.util.*;

public class UnionFindSet {

    public static class Element<V> {
        public V value;

        public Element(V v) {
            value = v;
        }
    }

    /**
     * 并查集结构
     */
    public static class UnionFind<V> {
        public HashMap<V, Element<V>> elementMap; // 用来记录元素位置，对比元素的值
        public HashMap<Element<V>, Element<V>> fatherMap; // 用来记录元素的头部信息(集合的头元素)
        public HashMap<Element<V>, Integer> sizeMap; // 用来记录元素所在集合的大小信息

        // 初始化一个并查集的时候，list中每个值创建一个元素，元素所在的集合只有自己，头元素也为自己，集合大小为1
        public UnionFind(List<V> list) {
            elementMap = new HashMap<>();
            fatherMap = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V value : list) {
                Element<V> element = new Element<V>(value);
                elementMap.put(value, element);
                fatherMap.put(element, element);
                sizeMap.put(element, 1);
            }
        }

        /**
         * 查找元素的头部信息，并在向上查找过程中更新每个元素的头部信息，使得集合结构变得更紧凑
         * 用于判断两个元素是否在同一集合内
         * 
         * @param element
         * @return
         */
        private Element<V> findHead(Element<V> element) {
            // 向上查找头部信息时候所经过的路径
            Stack<Element<V>> path = new Stack<>();
            // 一直向上查找，直到找到头部元素
            while (element != fatherMap.get(element)) {
                // 将路过的每个元素放入栈内
                path.push(element);
                element = fatherMap.get(element);
            }
            // 从栈内依次取出元素，并将他们的头部信息更新
            while (!path.isEmpty()) {
                fatherMap.put(path.pop(), element);
            }
            // 返回找到的头部元素
            return element;
        }

        /**
         * 判断两个元素是否处在同一集合内
         * 
         * @param a
         * @param b
         * @return
         */
        public boolean isSameSet(V a, V b) {
            // 如果两个元素都出现过再查找
            if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
                // 分别查找这两个元素的头部信息，如果一致，则代表他们在同一集合内，否则不在同一集合。
                return findHead(elementMap.get(a)) == findHead(elementMap.get(b));
            }
            return false;
        }

        /**
         * 将两个元素所在的不同集合，合并为同一集合
         * 
         * @param a
         * @param b
         */
        public void union(V a, V b) {
            // 如果两个元素都出现过再合并他们所在的集合
            if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
                // 先分别找到两个元素的头部元素
                Element<V> aFather = findHead(elementMap.get(a));
                Element<V> bFather = findHead(elementMap.get(b));
                // 如果已经在同一集合，就不处理
                if (aFather != bFather) {
                    // 分出两个元素所在的集合，哪个拥有更多的元素，将这个更大集合的头部设为新的集合头部
                    Element<V> big = sizeMap.get(aFather) >= sizeMap.get(bFather) ? aFather : bFather;
                    Element<V> small = big == aFather ? bFather : aFather;
                    // 更新并查集信息
                    fatherMap.put(small, big);
                    sizeMap.put(big, sizeMap.get(aFather) + sizeMap.get(bFather));
                    sizeMap.remove(small);
                }
            }
        }
    }

    /**
     * 岛问题
     * 找出二维数组内有多少岛
     * (上下左右连起来的1算一座岛，斜线连接不算)
     * 比如：1 1 0 是两座岛
     * 1 0 1
     * 
     * @param data
     * @return
     */
    public static int countIslands(int[][] data) {
        if (data == null || data[0] == null) {
            return 0;
        }
        // 数组的长和宽
        int row = data.length;
        int col = data[0].length;
        int res = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // 如果碰到了1，上下左右继续找1
                if (data[i][j] == 1) {
                    res++;
                    infect(data, i, j, row, col);
                }
            }
        }
        return res;
    }

    /**
     * 将data[i][j]可以连接到的所有1改为2
     * 
     * @param data
     * @param i
     * @param j
     * @param row
     * @param col
     */
    public static void infect(int[][] data, int i, int j, int row, int col) {
        if (i < 0 || i >= row || j < 0 || j >= col || data[i][j] != 1) {
            return;
        }
        // 将自己改为1
        data[i][j] = 2;
        // 上下左右继续找
        infect(data, i + 1, j, row, col);
        infect(data, i - 1, j, row, col);
        infect(data, i, j + 1, row, col);
        infect(data, i, j - 1, row, col);
    }

    public static void main(String[] args) {
        int[][] data = {
                { 0, 0, 0, 0, 0, 0, 1, 0 },
                { 0, 0, 0, 0, 0, 0, 1, 0 },
                { 0, 0, 1, 0, 0, 0, 1, 0 },
                { 0, 0, 1, 0, 1, 0, 0, 0 },
                { 0, 0, 0, 1, 1, 0, 1, 1 },
                { 1, 0, 1, 0, 1, 1, 1, 0 },
                { 1, 1, 0, 0, 0, 0, 1, 0 },
                { 0, 0, 1, 1, 0, 0, 1, 0 },
        };
        System.out.println("islands count: " + countIslands(data));
    }
}
