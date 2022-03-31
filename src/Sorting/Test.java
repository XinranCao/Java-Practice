package Sorting;

public class Test {

    /**
     * 一个整数数组内有2个整数出现了奇数次，其他整数出现了偶数次，找出这两个数字。
     * 
     * @param arr
     */
    public static void twoOddTimesNum(int[] arr) {
        int eor = 0, num_1 = 0, num2 = 0;
        // 将数组内所有整数进行亦或运算，得到 num_1 ^ num2
        for (int curr : arr) {
            eor ^= curr;
        }
        // 找出 num_1 和 num2 最右侧第一个不同 1（2进制）
        int rightOne = eor & (~eor + 1);
        // 将数组内这在这位有1的所有数进行亦或运算，得到num_1
        for (int curr : arr) {
            if ((rightOne & curr) == 0) {
                num_1 ^= curr;
            }
        }
        num2 = num_1 ^ eor;

        System.out.println("num_1: " + num_1 + "\nnum_2: " + num2);
    }

    public static void main(String[] args) {
        int[] arr = new int[] { 2, 3, 2, 3, 4, 4, 4, 5, 55, 5, 5, 55, 2, 2 };
        twoOddTimesNum(arr);
    }
}
