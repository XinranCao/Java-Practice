package Sorting;

import Tools.Tools;

public class MergeSort {

    /**
     * 小和问题/逆序对问题merge sort排序中的merge操作
     * 
     * @param arr 整数数组
     * @param L   需要排序的最左侧index
     * @param M   需要排序的最中间index
     * @param R   需要排序的最右侧index
     * @return 返回当前所有和
     */
    public static int smallSumMergeHelper(int[] arr, int L, int M, int R) {
        int[] temp = new int[R - L + 1];
        int i = 0, sum = 0;
        int p = L;
        int q = M + 1;
        while (p <= M && q <= R) {
            sum += arr[p] < arr[q] ? (R - q + 1) * arr[p] : 0;
            temp[i++] = arr[p] < arr[q] ? arr[p++] : arr[q++];
        }
        while (p <= M) {
            temp[i++] = arr[p++];
        }
        while (q <= R) {
            temp[i++] = arr[q++];
        }
        for (int curr = 0; curr < temp.length; curr++) {
            arr[L + curr] = temp[curr];
        }
        return sum;
    }

    /**
     * 小和问题/逆序对问题
     * 
     * @param arr 整数数组
     * @param L   需要排序的最左侧index
     * @param R   需要排序的最右侧index
     * @return 返回小和
     */
    public static int smallSumMerge(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        int M = L + ((R - L) >> 1);
        return smallSumMerge(arr, L, M) + smallSumMerge(arr, M + 1, R) + smallSumMergeHelper(arr, L, M, R);
    }

    /**
     * merge sort排序中的merge操作
     * 
     * @param arr 整数数组
     * @param L   需要排序的最左侧index
     * @param M   需要排序的最中间index
     * @param R   需要排序的最右侧index
     */
    public static void mergeHelper(int[] arr, int L, int M, int R) {
        int[] temp = new int[R - L + 1];
        int i = 0;
        int p = L;
        int q = M + 1;
        while (p <= M && q <= R) {
            temp[i++] = arr[p] <= arr[q] ? arr[p++] : arr[q++];
        }
        while (p <= M) {
            temp[i++] = arr[p++];
        }
        while (q <= R) {
            temp[i++] = arr[q++];
        }
        for (int curr = 0; curr < temp.length; curr++) {
            arr[L + curr] = temp[curr];
        }
    }

    /**
     * merge sort排序
     * 
     * @param arr 整数数组
     * @param L   需要排序的最左侧index
     * @param R   需要排序的最右侧index
     */
    public static void mergeSort(int[] arr, int L, int R) {
        if (L == R) {
            return;
        }
        int M = L + ((R - L) >> 1);
        mergeSort(arr, L, M);
        mergeSort(arr, M + 1, R);
        mergeHelper(arr, L, M, R);
    }

    public static void main(String[] args) {

        // merge sort排序
        int[] arr_1 = Tools.genRandomArr(30, 500);
        Tools.printIntArr(arr_1, "original:");
        mergeSort(arr_1, 0, arr_1.length - 1);
        Tools.printIntArr(arr_1, "merge sort:");

        // 利用merge sort解决小和问题
        int[] arr_2 = new int[] { 1, 3, 4, 2, 5 };
        Tools.printIntArr(arr_2, "array:");
        System.out.println("小和：" + smallSumMerge(arr_2, 0, arr_2.length - 1));

    }
}
