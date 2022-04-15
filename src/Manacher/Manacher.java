package Manacher;

public class Manacher {

    /**
     * 将字符串处理为特殊字符数组，使得找到回文位置中心更加便捷
     * 将原始字符串中每个字符前后都加上特殊字符 '#'
     * 如：“123” 处理为 [ # 1 # 2 # 3 # ]
     * 
     * @param s
     * @return
     */
    public static char[] manacherStr(String s) {
        char[] charArr = s.toCharArray();
        char[] manacher = new char[charArr.length * 2 + 1];

        for (int i = 0; i < manacher.length; i++) {
            manacher[i] = (i & 1) == 0 ? '#' : charArr[i >> 1];
        }

        return manacher;
    }

    /**
     * 找出字符串中最长回文子字符串长度
     * 
     * @param s
     * @return
     */
    public static int maxPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        // 特殊处理，得到manacher字符数组
        char[] str = manacherStr(s);
        int R = -1; // right，目前检查到的最大回文长度右端字符index + 1
        int C = -1; // center， 目前检查到的最大回文长度中间index
        int max = Integer.MIN_VALUE;
        int[] halfPaliLength = new int[str.length]; // 最大回文半径数组，记录每个位置为中心时，子字符串中最大回文半径

        for (int i = 0; i < str.length; i++) {
            // i位置为中心最小可能的回文半径长度
            // 2种大情况
            // 情况1：i >= R，需要从i两端开始依次检查所有子字符串，判断回文长度，最小可能性是只有i位置字符自己，长度为1
            // 情况2：分为三种子情况
            // 子情况1：i == R - 1，
            // 子情况2：i < R - 1， 并且 以 与i对应的回文位置 为中心的回文子字符串右边界超过C位置
            // 子情况2：i < R - 1， 并且 以 与i对应的回文位置 为中心的回文子字符串右边界未超过C位置
            // 情况2中任意一种情况下，最小可能性都为 i到R的距离 与 以与i对应位置为中心的回文子字符串半径 中的最小值
            // 前者为 R - i, 后者为 C -（i - C） = 2 * C - i
            halfPaliLength[i] = R > i ? Math.min(R - i, halfPaliLength[2 * C - i]) : 1;

            // 在不越界的前提下，以i为中心往两边扩，判断回文长度
            while (i + halfPaliLength[i] < str.length && i - halfPaliLength[i] > -1) {
                if (str[i + halfPaliLength[i]] == str[i - halfPaliLength[i]]) {
                    halfPaliLength[i]++;
                } else {
                    break;
                }
            }
            // 如果以i为中心的回文右边界超过原来的R，更新 R 与 C 的信息
            if (i + halfPaliLength[i] > R) {
                R = i + halfPaliLength[i];
                C = i;
            }
            max = Math.max(max, halfPaliLength[i]);
        }
        return max - 1;
    }

    public static void main(String[] args) {

        System.out.println(maxPalindrome("234frr9rrfjjf3"));

    }

}
