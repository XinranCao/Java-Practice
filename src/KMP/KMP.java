package KMP;

public class KMP {

    public static int containSubStr(String l, String s) {
        if (l == null || s == null || s.length() == 0 || l.length() < s.length()) {
            return -1;
        }
        char[] str_1 = l.toCharArray();
        char[] str_2 = s.toCharArray();
        int[] maxResembleArr = getMaxResembleArr(str_2);

        int i = 0;
        int j = 0;

        while (i < str_1.length && j < str_2.length) {
            if (str_1[i] == str_2[j]) {
                i++;
                j++;
            } else if (j == 0) {
                i++;
            } else {
                j = maxResembleArr[j];
            }
        }
        return j == str_2.length ? i - j : -1;
    }

    public static int[] getMaxResembleArr(char[] str) {
        if (str == null || str.length == 0) {
            return null;
        }
        if (str.length == 1) {
            return new int[] { -1 };
        }
        int[] res = new int[str.length];
        res[0] = -1;
        res[1] = 0;

        int i = 1;
        int compare = 0;
        while (i < str.length) {
            if (str[i] == str[compare]) {
                res[i++] = ++compare;
            } else if (compare > 0) {
                compare = res[compare];
            } else {
                res[i++] = 0;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        String str_1 = "abcdabcdefabcdaabcdea";
        String str_2 = "abcdea";
        System.out.println(containSubStr(str_1, str_2));
    }

}
