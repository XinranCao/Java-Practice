package KMP;

public class KMP {

    /**
     * 判断字符串l时候包含子字符串s
     * 如果包含，返回l中子字符串s的起始位置
     * 如果不包含，返回-1
     * 
     * @param l
     * @param s
     * @return
     */
    public static int containSubStr(String l, String s) {
        // 如果所给的字符串为空，或者l比s短，则l不可能包含子字符串s
        if (l == null || s == null || s.length() == 0 || l.length() < s.length()) {
            return -1;
        }
        char[] str_1 = l.toCharArray();
        char[] str_2 = s.toCharArray();
        int[] maxResembleArr = getMaxResembleArr(str_2); // 得到子字符串s的最大相似数组

        int i = 0; // i表示大字符串l正在检查的位置
        int j = 0; // j表示小字符串s正在检查的位置
        while (i < str_1.length && j < str_2.length) { // 两者都不越界的时候，说明还没有比较完
            if (str_1[i] == str_2[j]) { // 如果大小字符串当前位置一致，继续对比下一位
                i++;
                j++;
            } else if (j == 0) { // 如果不一致，并且j在小字符串0位置，继续从大字符串i+1位置试图匹配
                i++;
            } else { // 如果不一致，并且前几位有相同的字符，则j利用最大相似数组向前跳，找到可能继续匹配的位置
                j = maxResembleArr[j];
            }
        }
        // 如果小字符串先越界，代表大字符串l包含小字符串s，子字符串s出现的起始位置为 i - j
        // 因为对比完成时，i在子字符串s的结尾位置，j为子字符串s的长度
        // 如果大字符串先越界，代表没有找到子字符串s，不包含s，返回-1
        return j == str_2.length ? i - j : -1;
    }

    /**
     * 前缀后缀最大相同长度数组
     * 记录一个数组每个位置之前，前缀后缀最大相同长度
     * 人为规定0位上为-1
     * 如：数组【 1 2 3 1 2 3 4 ] 中index为6的位置之前，前缀后缀最大有3位，即 123 == 123,
     * 所以前缀后缀最大相同长度数组index6上记录为3
     * 输入这个数组，得到输出[ -1 0 0 0 1 2 3]
     * 
     * @param str
     * @return
     */
    public static int[] getMaxResembleArr(char[] str) {
        if (str == null || str.length == 0) {
            return null;
        }
        if (str.length == 1) { // 如果只有1位，直接返回 [-1]
            return new int[] { -1 };
        }
        int[] res = new int[str.length];
        res[0] = -1; // 人为规定0位上位-1
        res[1] = 0; // 前缀后缀不能是整个子数组，所以1位置上一定为0

        int i = 2; // 从第i位开始填，从i - 1位置开始对比前缀后缀，也需要知道i - 1位置的前缀后缀最大相同长度信息
        int compare = 0; // 既代表第i - 1位置需要对比的位置，也代表i - 1位置前缀后缀最大相同长度
        // 比如：[ 1 4 1 4 3 ] 当 i == 4 时，compare == 1，代表[ 1 4 1 ] 前缀后缀最大相同长度为1
        // 接下来需要比较 index为 0 的[4] 和 index为 i - 1 的[4]
        while (i < str.length) {
            if (str[i - 1] == str[compare]) { // 如果i-1位置的元素和compare位置的元素一致
                // i 位置之前前缀后缀最大相同长度为 compare + 1
                // ++compare,代表向右移一位，并且等于i位置之前前缀后缀最大相同长度
                // i位置信息更新后，i++
                res[i++] = ++compare;
            } else if (compare > 0) {
                // 如果不一致，并且i - 1 位置之前的前缀后缀最大相同长度 > 0
                // 代表 i 位置之前前缀后缀最大相同长度小于i - 1位置之前的。。。
                // 这种情况下，compare位置往前跳，寻找可能存在的更短的前缀后缀相同长度，并且继续对比
                // 比如：[ 1 2 3 1 2 1 2 3 1 2 9 9 ]
                // 当来到index == 11 的时候 compare == 5 已经生成的数组为[-1 0 0 0 1 2 1 2 3 4 5]
                // 对比发现 [9] != compare位置的[1] 并且 compare == 5, 5 > 0
                // 找出数组中index为5的数据：2， compare = 2
                // 继续对比 [9] 和 compare(2)位置的[3]
                // 因为前缀后缀相同，保证了[ 1 2 3 1 2 ]的前缀 和 [ 1 2 3 1 2 ]后缀 也相同，所以可以如此往前跳
                compare = res[compare];
            } else {
                // 如果跳到最开头仍然不一致，代表i位置之前，没有任何长度的前缀后缀相同
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
