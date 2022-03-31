package Sorting;

import Tools.Tools;

public class QuickSort {

    /**
     * 荷兰国旗问题 quick sort
     * 
     * @param arr
     */
    public static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * quick sort 递归
     * 
     * @param arr
     * @param L
     * @param R
     */
    public static void quickSort(int[] arr, int L, int R) {
        if (L < R) {
            Tools.swap(arr, L + (int) (Math.random() * (R - L + 1)), R);
            int[] middle = partition(arr, L, R);
            quickSort(arr, L, middle[0] - 1);
            quickSort(arr, middle[1] + 1, R);
        }
    }

    /**
     * quick sort 具体操作
     * 
     * @param arr
     * @param L
     * @param R
     * @return
     */
    public static int[] partition(int[] arr, int L, int R) {
        int left = L - 1; // 左指针
        int right = R; // 右指针
        while (L < right) {
            if (arr[L] < arr[R]) {
                Tools.swap(arr, ++left, L++); // 将当前值与左边界外的第一个数互换，左指针右移，当前标右移
            } else if (arr[L] > arr[R]) {
                Tools.swap(arr, --right, L); // 将当前值与右边界外第一个数互换，右指针左移，当前标不动
            } else {
                L++;
            }
        }
        Tools.swap(arr, right, R); // 将对比值与右边界内第一个数互换
        return new int[] { left + 1, right };
    }

    public static void main(String[] args) {
        int[] arr = new int[] { 1, 3, 4, 2, 5, 3, 2, 2, 4, 7, 4, 6, 3, 7, 3 };
        Tools.printIntArr(arr, "original:");
        quickSort(arr);
        Tools.printIntArr(arr, "quick sort:");
    }
}
