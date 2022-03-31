package Sorting;

import Tools.Tools;

public class RadixSort {

    /**
     * 基数排序
     * 
     * @param arr
     */
    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        radixSort(arr, 0, arr.length - 1, digitCount(arr));
    }

    /**
     * 找出数组中最大的数有几位
     * 
     * @param arr
     * @return
     */
    public static int digitCount(int[] arr) {
        int Max = Integer.MIN_VALUE;
        // 找出最大的数
        for (int curr : arr) {
            Max = Math.max(Max, curr);
        }
        int digit = 0;
        // 不断 /10 算出位数
        while (Max != 0) {
            Max /= 10;
            digit++;
        }
        return digit;
    }

    /**
     * 基数排序具体操作
     * 
     * @param arr
     * @param L
     * @param R
     * @param digit 数组中最大的数有几位
     */
    public static void radixSort(int[] arr, int L, int R, int digit) {
        final int radix = 10;
        // 创建一个空列表，用来记录每一步排序
        int[] bucket = new int[R - L + 1];
        // 个十百千万，按位数一层一层排序
        for (int d = 1; d <= digit; d++) {
            // 0-9 十个空位，用来记录每一位上0-9出现的次数
            int[] count = new int[radix];

            // 将某一位（d）上0-9出现的总次数记录
            for (int i = L; i <= R; i++) {
                int temp = getDigit(arr[i], d);
                count[temp]++;
            }

            // 计算某一位（d）上 <= 0-9出现的总次数
            for (int i = 1; i < radix; i++) {
                count[i] += count[i - 1];
            }

            // 数组从后往前，根据元素内d位上出现的数字，将元素放在对应的位置上（countp[出现的数字] - 1）
            for (int i = R; i >= L; i--) {
                int temp = getDigit(arr[i], d);
                bucket[count[temp] - 1] = arr[i];
                count[temp]--;
            }

            // 将整理好的数组放回原位
            for (int i = L, j = 0; i <= R; i++, j++) {
                arr[i] = bucket[j];
            }
        }
    }

    /**
     * 找出num中第digitNum位置上的数字
     * 比如：12345 第1位数字为5，第2位数字位4...
     * 
     * @param num
     * @param digitNum
     * @return
     */
    public static int getDigit(int num, int digitNum) {
        return ((num / ((int) Math.pow(10, digitNum - 1))) % 10);
    }

    public static void main(String[] args) {
        // 基数排序 radix sort
        int[] arr = Tools.genRandomArr(10, 1000);
        Tools.printIntArr(arr, "original:");
        radixSort(arr);
        Tools.printIntArr(arr, "radix sort:");
    }

}
