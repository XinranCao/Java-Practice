package Deque;

import java.util.HashMap;

public class Deque {

    public static class Element<V> {
        public V value;

        public Element(V v) {
            value = v;
        }
    }

    /**
     * 用array实现双端队列，头尾都可以增删元素
     * 用两个指针分别指向头元素 与 尾元素之后一位
     * 
     */
    public static class ArrayDeque<V> {
        private static final int EXPAND_BASE = 2; // 用于扩容
        private Object[] elements;
        private int head; // 头元素位置
        private int tail; // 尾元素后一位位置
        private int size; // 存放了多少个有效元素

        public ArrayDeque(int length) {
            elements = new Object[length];
            head = 0;
            tail = 0;
            size = 0;
        }

        /**
         * 扩容
         */
        private void expand() {
            Object[] newElements = new Object[elements.length * EXPAND_BASE];

            // 从头元素开始，一直到array末端，将元素一一拷贝
            for (int i = head, j = 0; i < elements.length; i++, j++) {
                newElements[j] = elements[i];
            }

            // 从0位置开始，将剩下的元素一一拷贝
            for (int i = 0, j = elements.length - head; i < head; i++, j++) {
                newElements[j] = elements[i];
            }
            // 更新头尾元素位置
            head = 0;
            tail = elements.length;
            elements = newElements;
        }

        /**
         * 判断越界，并返回正确指针位置
         * 比如：头部指针本来在0位置，从头部加入一个元素，头部指针左移，变成-1，越界，将头部指针放到array最末端
         * 
         * @param index
         * @return
         */
        private int getMod(int index) {
            if (index < 0) {
                index += elements.length;
            } else if (index >= elements.length) {
                index -= elements.length;
            }
            return index;
        }

        /**
         * 从头部加入一个元素
         * 
         * @param newEle
         */
        public void addHead(Element<V> newEle) {
            // 移动头部指针位置，并放入新元素
            head = getMod(head - 1);
            elements[head] = newEle;
            if (head == tail) { // 如果头部指针与尾部后一个位置指针重合，代表array已满，需要扩容
                expand();
            }
            size++;
        }

        /**
         * 从尾部加入一个元素
         * 
         * @param newEle
         */
        public void addTail(Element<V> newEle) {
            // 移动尾部指针位置，并放入新元素
            elements[tail] = newEle;
            tail = getMod(tail + 1);
            if (head == tail) { // 如果头部指针与尾部后一个位置指针重合，代表array已满，需要扩容
                expand();
            }
            size++;
        }

        /**
         * 从头部删除一个元素
         * 
         * @return
         */
        @SuppressWarnings("unchecked")
        public Element<V> removeHead() {
            if (size == 0) {
                return null;
            }
            // 记录要删除的元素
            Element<V> remove = (Element<V>) elements[head];
            // 更新头部位置。
            // 注意：该元素其实仍被记录在array中，只是不在头部指针 - 尾部指针的范围中
            head = getMod(head + 1);
            size--;
            return remove;
        }

        /**
         * 从尾部删除一个元素
         * 
         * @return
         */
        @SuppressWarnings("unchecked")
        public Element<V> removeTail() {
            if (size == 0) {
                return null;
            }
            // 更新尾部位置。
            // 注意：该元素其实仍被记录在array中，只是不在头部指针 - 尾部指针的范围中
            tail = getMod(tail - 1);
            Element<V> remove = (Element<V>) elements[tail]; // 记录要删除的元素
            size--;
            return remove;
        }

        /**
         * 查看头部元素
         * 
         * @return
         */
        @SuppressWarnings("unchecked")
        public Element<V> peakHead() {
            if (size == 0) {
                return null;
            }
            return (Element<V>) elements[head];
        }

        /**
         * 查看尾部元素
         * 
         * @return
         */
        @SuppressWarnings("unchecked")
        public Element<V> peakTail() {
            if (size == 0) {
                return null;
            }
            int lastIndex = getMod(tail - 1);
            return (Element<V>) elements[lastIndex];
        }
    }

    /**
     * 窗口问题
     * 给一个数组与一个窗口大小，窗口从数组头部移动到尾部
     * 记录每个窗口范围里最大的数字，并返回这个数组
     * 如：给一个数组[ 4,3,5,3,6,1,7] 窗口为 4
     * 窗口从[ 4,3,5 ] 一直移动到[ 6,1,7 ]
     * 每次窗口中最大数字组成的数组为： [ 4,5,6,6,7 ]
     * 
     * @param <V>
     * @param arr
     * @param w   // 窗口大小
     * @return
     */
    public static <V> int[] largestArr(int[] arr, int w) {
        // 无效参数处理
        if (arr == null || arr.length == 0 || w < 1 || w > arr.length) {
            return null;
        }
        // 总共有 length - w + 1 个窗口位置
        int[] res = new int[arr.length - w + 1];
        // 用来记录元素所在位置对应关系的两个hashmap
        HashMap<Element<Integer>, Integer> eleIndex = new HashMap<Element<Integer>, Integer>();
        HashMap<Integer, Element<Integer>> indexEle = new HashMap<Integer, Element<Integer>>();
        for (int i = 0; i < arr.length; i++) {
            Element<Integer> ele = new Element<Integer>(arr[i]);
            eleIndex.put((Element<Integer>) ele, i);
            indexEle.put(i, (Element<Integer>) ele);
        }

        // 利用双端队列
        ArrayDeque<Integer> deque = new ArrayDeque<Integer>(w);
        for (int i = 0; i < arr.length; i++) {
            // 每次移动窗口，必然从尾部加入一个元素
            // 初始形成w大小的窗口时，也需要加入一个元素
            // 行程单调递减的双端队列，当队列不为空时，查看尾部元素，如果比新元素小，则移出这个元素
            // 直到队列为空，或者尾部元素比新元素大，再放入新元素
            while (deque.peakTail() != null && deque.peakTail().value <= arr[i]) {
                deque.removeTail();
            }
            // 放入新元素
            deque.addTail((Element<Integer>) indexEle.get(i));

            // 窗口仍未形成的时候，窗口内元素不够w大小，不作处理
            // 窗口形成并开始移动时，在进行操作
            if (i + 1 >= w) {
                // 如果队列不为空，查看头部元素，检查这个元素是否已经过期（即不在窗口范围内）
                // 如果已经过期，删除这个元素
                while (deque.peakHead() != null && eleIndex.get(deque.peakHead()) < (i - w + 1)) {
                    deque.removeHead();
                }
                // 因为单调递减，所以未过期的最大元素就是窗口范围内的最大元素
                // 将这个元素记录在ers中
                res[i + 1 - w] = deque.peakHead().value;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] arr = new int[] { 1, 3, 7, 3, 5, 1, 4, 3, 5, 4, 3, 2, 3, 4, 1, 1, 4, 3, 3, 6, 7 };
        int[] ans = largestArr(arr, 3);
        System.out.print("largest every window: ");
        for (int e : ans) {
            System.out.print(e + " ");
        }
        System.out.println();
    }

}
