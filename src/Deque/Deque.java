package Deque;

import java.util.HashMap;

public class Deque {

    public static class Element<V> {
        public V value;

        public Element(V v) {
            value = v;
        }
    }

    public static class ArrayDeque<V> {
        private static final int EXPAND_BASE = 2;
        private Object[] elements;
        private int head;
        private int tail;
        private int size;

        public ArrayDeque(int length) {
            elements = new Object[length];
            head = 0;
            tail = 0;
            size = 0;
        }

        private void expand() {
            Object[] newElements = new Object[elements.length * EXPAND_BASE];

            for (int i = head, j = 0; i < elements.length; i++, j++) {
                newElements[j] = elements[i];
            }

            for (int i = 0, j = elements.length - head; i < head; i++, j++) {
                newElements[j] = elements[i];
            }

            head = 0;
            tail = elements.length;
            elements = newElements;
        }

        private int getMod(int index) {
            if (index < 0) {
                index += elements.length;
            } else if (index >= elements.length) {
                index -= elements.length;
            }
            return index;
        }

        public void addHead(Element<V> newEle) {
            head = getMod(head - 1);
            elements[head] = newEle;
            if (head == tail) {
                expand();
            }
            size++;
        }

        public void addTail(Element<V> newEle) {
            elements[tail] = newEle;
            tail = getMod(tail + 1);
            if (head == tail) {
                expand();
            }
            size++;
        }

        @SuppressWarnings("unchecked")
        public Element<V> removeHead() {
            if (size == 0) {
                return null;
            }
            Element<V> remove = (Element<V>) elements[head];
            head = getMod(head + 1);
            size--;
            return remove;
        }

        @SuppressWarnings("unchecked")
        public Element<V> removeTail() {
            if (size == 0) {
                return null;
            }
            tail = getMod(tail - 1);
            Element<V> remove = (Element<V>) elements[tail];
            size--;
            return remove;
        }

        @SuppressWarnings("unchecked")
        public Element<V> peakHead() {
            if (size == 0) {
                return null;
            }
            return (Element<V>) elements[head];
        }

        @SuppressWarnings("unchecked")
        public Element<V> peakTail() {
            if (size == 0) {
                return null;
            }
            int lastIndex = getMod(tail - 1);
            return (Element<V>) elements[lastIndex];
        }

        public int getSize() {
            return size;
        }
    }

    public static <V> int[] largestArr(int[] arr, int w) {
        if (arr == null || arr.length == 0 || w < 1 || w > arr.length) {
            return null;
        }
        int[] res = new int[arr.length - w + 1];
        HashMap<Element<Integer>, Integer> eleIndex = new HashMap<Element<Integer>, Integer>();
        HashMap<Integer, Element<Integer>> indexEle = new HashMap<Integer, Element<Integer>>();
        for (int i = 0; i < arr.length; i++) {
            Element<Integer> ele = new Element<Integer>(arr[i]);
            eleIndex.put((Element<Integer>) ele, i);
            indexEle.put(i, (Element<Integer>) ele);
        }

        ArrayDeque<Integer> deque = new ArrayDeque<Integer>(w);
        for (int i = 0; i < arr.length; i++) {
            while (deque.peakTail() != null && deque.peakTail().value <= arr[i]) {
                deque.removeTail();
            }

            deque.addTail((Element<Integer>) indexEle.get(i));

            if (i + 1 >= w) {
                while (deque.peakHead() != null && eleIndex.get(deque.peakHead()) < (i - w + 1)) {
                    deque.removeHead();
                }
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
