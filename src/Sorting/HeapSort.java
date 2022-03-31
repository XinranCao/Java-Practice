package Sorting;

import Tools.Tools;

import java.util.PriorityQueue;

public class HeapSort {

    /**
     * 一个几乎排好序的数组，每个位置上的数字需要移动的距离不超过k
     * 将这个数组排序
     * 
     * @param arr
     * @param k   每个元素需要移动的最大距离
     */
    public static void sortedArrDisLessK(int[] arr, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        // heap最右端指针
        int index = 0;

        // 将前k个元素先放进heap中（下面的循环又加了一个元素进heap，总共k+1个元素）
        for (; index < Math.min(k, arr.length); index++) {
            heap.add(arr[index]);
        }

        // 从0开始处理数组数据
        int processIdx = 0;

        // 每次往heap中新加一个元素，然后poll出最小元素，是已经排好序的元素
        for (; index < arr.length; index++, processIdx++) {
            heap.add(arr[index]);
            arr[processIdx] = heap.poll();
        }

        // 当heap指针越界停止，把heap中剩下的元素从小到大依次取完
        while (!heap.isEmpty()) {
            arr[processIdx++] = heap.poll();
        }
    }

    /**
     * heap sort
     * 
     * @param arr
     */
    public static void heapSort(int[] arr) {

        if (arr == null || arr.length < 2) {
            return;
        }

        // 先将数组整理为大根堆
        // for (int i = 0; i < arr.length; i++) { // O(N)
        // heapInsert(arr, i); // O(log N)
        // }

        for (int i = arr.length - 1; i >= 0; i--) { // O(N)
            heapify(arr, i, arr.length);
        }

        int heapSize = arr.length;
        Tools.swap(arr, 0, --heapSize); // 交换0位置和最后一位
        // 每次找出一个最大值
        while (heapSize > 0) {
            heapify(arr, 0, heapSize);
            Tools.swap(arr, 0, --heapSize);
        }
    }

    /**
     * 将新元素插入到heap中，并调整为大根堆
     * 
     * @param arr
     * @param i   新插入数的index
     */
    public static void heapInsert(int[] arr, int i) {
        while (arr[i] > arr[(i - 1) / 2]) {
            Tools.swap(arr, i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    /**
     * 从index的元素开始，将heap重新调整为大根堆
     * 
     * @param arr
     * @param index    需要调整的元素index
     * @param heapSize 堆内元素的个数，用来找到堆的边界
     */
    public static void heapify(int[] arr, int index, int heapSize) {
        int leftChild = index * 2 + 1;
        // 不断向下调整，直到出边界
        while (leftChild < heapSize) {
            // 找到左右子元素中最大的
            int largest = leftChild + 1 < heapSize && arr[leftChild] < arr[leftChild + 1] ? leftChild + 1 : leftChild;
            largest = arr[index] > arr[largest] ? index : largest;
            if (largest == index) {
                return;
            }
            // 如果父元素比子元素小，交换他们
            Tools.swap(arr, index, largest);
            index = largest;
            leftChild = index * 2 + 1;
        }
    }

    public static void main(String[] args) {
        // 堆排序 heap sort
        int[] arr_1 = Tools.genRandomArr(30, 500);
        Tools.printIntArr(arr_1, "original:");
        heapSort(arr_1);
        Tools.printIntArr(arr_1, "heap sort:");

        // 一个几乎排好序的数组，每个位置上的数字需要移动的距离不超过k
        // 将这个数组排序
        int[] arr_2 = new int[] { 2, 1, 4, 3, 6, 5, 6, 8 };
        Tools.printIntArr(arr_2, "original:");
        sortedArrDisLessK(arr_2, 2);
        Tools.printIntArr(arr_2, "sortedArrDisLessK:");
    }
}
