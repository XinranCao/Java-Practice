package Manacher;

public class Manacher {

    public static char[] manacherStr(String s) {
        char[] charArr = s.toCharArray();
        char[] manacher = new char[charArr.length * 2 + 1];

        for (int i = 0; i < manacher.length; i++) {
            manacher[i] = (i & 1) == 0 ? '#' : charArr[i >> 1];
        }

        return manacher;
    }

    public static int maxPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = manacherStr(s);
        int R = -1;
        int C = -1;
        int max = Integer.MIN_VALUE;
        int[] halfPaliLength = new int[str.length];

        for (int i = 0; i < str.length; i++) {
            halfPaliLength[i] = R > i ? Math.min(R - i, halfPaliLength[2 * C - i]) : 1;

            while (i + halfPaliLength[i] < str.length && i - halfPaliLength[i] > -1) {
                if (str[i + halfPaliLength[i]] == str[i - halfPaliLength[i]]) {
                    halfPaliLength[i]++;
                } else {
                    break;
                }
            }
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
