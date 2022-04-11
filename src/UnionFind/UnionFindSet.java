package UnionFind;

import java.util.*;

public class UnionFindSet {

    public static class Element<V> {
        public V value;

        public Element(V v) {
            value = v;
        }
    }

    public static class UnionFind<V> {
        public HashMap<V, Element<V>> elementMap;
        public HashMap<Element<V>, Element<V>> fatherMap;
        public HashMap<Element<V>, Integer> sizeMap;

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

        private Element<V> findHead(Element<V> element) {
            Stack<Element<V>> path = new Stack<>();
            while (element != fatherMap.get(element)) {
                path.push(element);
                element = fatherMap.get(element);
            }
            while (!path.isEmpty()) {
                fatherMap.put(path.pop(), element);
            }
            return element;
        }

        public boolean isSameSet(V a, V b) {
            if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
                return findHead(elementMap.get(a)) == findHead(elementMap.get(b));
            }
            return false;
        }

        public void union(V a, V b) {
            if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
                Element<V> aFather = findHead(elementMap.get(a));
                Element<V> bFather = findHead(elementMap.get(b));
                if (aFather != bFather) {
                    Element<V> big = sizeMap.get(aFather) >= sizeMap.get(bFather) ? aFather : bFather;
                    Element<V> small = big == aFather ? bFather : aFather;
                    fatherMap.put(small, big);
                    sizeMap.put(big, sizeMap.get(aFather) + sizeMap.get(bFather));
                    sizeMap.remove(small);
                }
            }
        }
    }

    public static int countIslands(int[][] data) {
        if (data == null || data[0] == null) {
            return 0;
        }
        int row = data.length;
        int col = data[0].length;
        int res = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (data[i][j] == 1) {
                    res++;
                    infect(data, i, j, row, col);
                }
            }
        }
        return res;
    }

    public static void infect(int[][] data, int i, int j, int row, int col) {
        if (i < 0 || i >= row || j < 0 || j >= col || data[i][j] != 1) {
            return;
        }
        data[i][j] = 2;
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
