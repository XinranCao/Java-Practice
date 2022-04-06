package Recursion;

import java.util.*;

public class RecursiveSolution {

    public static void largestValue(int[] weights, int[] values, int maxWeight) {
        if (maxWeight == 0
                || weights == null || values == null
                || weights.length == 0 || values.length == 0) {

            System.out.println("invalie input");
            return;
        }

        System.out.println("max value: " + largestValueRecursion(weights, values, maxWeight, 0, 0));
    }

    public static int largestValueRecursion(int[] weights, int[] values, int maxWeight, int i, int currWeight) {

        if (i == weights.length) {
            return 0;
        }
        if (currWeight + weights[i] > maxWeight) {
            return 0;
        }
        int currValue = 0;

        currValue += Math.max(largestValueRecursion(weights, values, maxWeight, i + 1, currWeight),
                values[i] + largestValueRecursion(weights, values, maxWeight, i + 1, currWeight + weights[i]));
        return currValue;
    }

    public static void numConvertStr(String input) {
        char[] arr = input.toCharArray();
        System.out.println("original array: [ " + String.valueOf(arr) + " ]");
        System.out.print("possible convertion: ");
        numConvertStr(arr, 0);
        System.out.println();
    }

    public static void numConvertStr(char[] arr, int i) {
        if (i == arr.length) {
            System.out.print("[ " + String.valueOf(arr) + " ] ");
            return;
        }
        if (arr[i] == '0') {
            return;
        } else if (arr[i] == '1') {
            arr[i] = 'A';
            numConvertStr(arr, i + 1);
            arr[i] = '1';

            if (i + 1 == arr.length) {
                return;
            }
            char temp = arr[i + 1];
            int num = Character.getNumericValue(temp);
            arr[i] = ' ';
            arr[i + 1] = (char) (num + 'J');
            numConvertStr(arr, i + 2);
            arr[i] = '1';
            arr[i + 1] = temp;
        } else if (arr[i] == '2') {
            arr[i] = 'B';
            numConvertStr(arr, i + 1);
            arr[i] = '2';

            if (i + 1 == arr.length) {
                return;
            }
            char temp = arr[i + 1];
            int num = Character.getNumericValue(temp);
            if (num > 6) {
                return;
            }
            arr[i] = ' ';
            arr[i + 1] = (char) (num + 'T');
            numConvertStr(arr, i + 2);
            arr[i] = '2';
            arr[i + 1] = temp;
        } else {
            char temp = arr[i];
            int num = Character.getNumericValue(temp) - 1;
            arr[i] = (char) (num + 'A');
            numConvertStr(arr, i + 1);
            arr[i] = temp;
        }
    }

    /**
     * 将stack中元素逆序
     * 
     * @param stack
     */
    public static void reverseStack(Stack<String> stack) {
        if (stack == null || stack.isEmpty()) {
            return;
        }
        // 取出目前stack中最下层元素，并暂存
        String temp = getBottomStack(stack);
        // 继续处理
        reverseStack(stack);
        // 将刚拿出的元素都放回最上层
        stack.add(temp);
    }

    /**
     * 取出栈中最下面的元素，并将其他元素位置不变放回stack中
     * 
     * @param stack
     * @return
     */
    public static String getBottomStack(Stack<String> stack) {
        // 先将最上边的元素取出，并暂存
        String curr = stack.pop();
        // 如果stack已经空了，代表刚取出的这个元素就是最下面的元素，将其返回
        if (stack.isEmpty()) {
            return curr;
        } else {
            // 否则继续向下取出下一个元素，并等待返回最下层元素
            String bottom = getBottomStack(stack);
            // 将刚取出的元素放回
            stack.add(curr);
            // 继续将最下层元素返回至上一层
            return bottom;
        }
    }

    /**
     * 手牌游戏
     * 给一个数组[1,2,100,4] 两个玩家，轮流从最左或最右拿牌，直到拿完所有牌。得分是所有自己拿到牌的数值之和。
     * 两个玩家都是绝对理智聪明的，会做出最有利于自己的选择。
     * 返回赢家的最终得分
     * 
     * @param cards
     */
    public static void takeCardGame(int[] cards) {
        if (cards == null || cards.length == 0) {
            System.out.println("invalid input");
            return;
        }
        // 先手后手，谁赢返回谁的最终得分
        System.out.println("winner score: " + Math.max(cardGameFirstHand(cards, 0, cards.length - 1),
                cardGameSecondHand(cards, 0, cards.length - 1)));
    }

    /**
     * 手牌游戏的先手策略
     * 
     * @param cards // 所有手牌
     * @param i     // 剩下牌起始index
     * @param j     //剩下牌终止index
     * @return // 当前情况下，先手时，能取得的最大分数
     */
    public static int cardGameFirstHand(int[] cards, int i, int j) {
        if (i == j) {
            // 如果只剩一张牌，先手者将这张牌拿走，总分加上这张牌的价值
            return cards[i];
        }
        // 否则，拿一张牌，然后变为后手
        // 可以选择拿最左侧的牌或者最右侧的牌，选择获得最多分数的可能，并返回这种情况下获得的分数
        // 选择最左的话，获得最左侧牌的价值，并且剩下 i + 1 到 j 的牌
        // 选择最右的话，获得最右侧牌的价值，并且剩下 i 到 j - 1 的牌
        return Math.max(cards[i] + cardGameSecondHand(cards, i + 1, j),
                cards[j] + cardGameSecondHand(cards, i, j - 1));
    }

    /**
     * 手牌游戏的后手策略
     * 
     * @param cards // 所有手牌
     * @param i     // 剩下牌起始index
     * @param j     //剩下牌终止index
     * @return // 当前情况下，后手时，能取得的最大分数
     */
    public static int cardGameSecondHand(int[] cards, int i, int j) {
        if (i == j) {
            // 如果只剩一张牌，后手没有机会选，不会再有更多分数，返回0
            return 0;
        }
        // 否则，等待别人拿一张牌，然后变为先手
        // 对方可以选择拿最左侧的牌或者最右侧的牌，选择获得最多分数的可能，并返回这种情况下获得的分数
        // 所以后手只能取得这两者中，获得分数最小的那张牌
        // 如果别人选择最左的牌，剩下 i + 1 到 j 的牌，当前自己得分不变
        // 如果别人选择最右的牌，剩下 i 到 j - 1 的牌，当前自己得分不变
        return Math.min(cardGameFirstHand(cards, i + 1, j),
                cardGameFirstHand(cards, i, j - 1));
    }

    /**
     * 打印一个字符串所有的排列组合
     * 
     * @param str
     */
    public static void strPermuatation(String str) {
        if (str == null || str.length() == 0) {
            return;
        }
        // 用来存放所有处理完成的字符结果
        ArrayList<String> res = new ArrayList<>();
        char[] charArr = str.toCharArray();
        permuRecursion(charArr, 0, res);
        // 全部处理完成之后打印结果
        for (String curr : res) {
            System.out.print("[ " + curr + " ]");
        }
        System.out.println();
    }

    /**
     * 打印一个字符串所有排列组合的 递归解
     * 
     * @param charArr 需要处理的字符数组
     * @param i       当前处理的字符index
     * @param res     存放目前处理完成并生成的所有结果
     */
    public static void permuRecursion(char[] charArr, int i, ArrayList<String> res) {
        if (i == charArr.length) {
            // 当index移动到数组长度，代表整个数组处理完毕，将这个结果加入到res
            res.add(String.valueOf(charArr));
            return;
        }
        // 用来去重，26个字母长度的数组，true代表已在这个index上出现过所对应的字母（有重复），false代表无重复
        boolean[] tried = new boolean[26];
        // 从i当前位置向后处理，所有字符都可能出现在第i位上
        for (int j = i; j < charArr.length; j++) {
            // 如果不重复
            if (!tried[charArr[j] - 'a']) {
                // 记录这个字母出现过
                tried[charArr[j] - 'a'] = true;
                // 将第i位设置为第j为的字符
                swapChar(charArr, i, j);
                // 继续处理之后的字符数组
                permuRecursion(charArr, i + 1, res);
                // 处理完毕后再交换回来，这样的话，保持原数组原封不动，可以继续尝试其他结果
                swapChar(charArr, i, j);
            }
        }
    }

    /**
     * 交换字符数组中，i位置和j位置的字符
     * 
     * @param arr
     * @param i
     * @param j
     */
    public static void swapChar(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 打印一个字符串所有的子集，不用list，省空间的解法
     * 
     * @param str
     */
    public static void printAllSubStr2(String str) {
        if (str == null || str.length() == 0) {
            return;
        }
        char[] charArr = str.toCharArray();
        printSubStrRecursion2(charArr, 0);
        System.out.println();
    }

    /**
     * 打印一个字符串所有的子集，不用list 的递归解
     * 
     * @param res // 记录目前位置，处理好的字符
     * @param i   // 当前处理字符的index
     */
    public static void printSubStrRecursion2(char[] res, int i) {
        if (i == res.length) {
            // 当index移动到数组长度，代表整个数组处理完毕，打印结果res
            System.out.print("[ " + String.valueOf(res) + " ]");
            return;
        }
        // 加入当前字符，并继续处理index + 1 的字符
        printSubStrRecursion2(res, i + 1);
        // 暂存当前字符
        char temp = res[i];
        // 将当前字符设为空
        res[i] = 0;
        // 相当于不加入当前字符，并继续处理index + 1 的字符
        printSubStrRecursion2(res, i + 1);
        // 处理完毕后，将当前字符还原
        res[i] = temp;
    }

    /**
     * 打印一个字符串所有的子集
     * 如： “abc” 打印 [abc] [ab] [bc] [ac] [a] [b] [c] []
     * 
     * @param str
     */
    public static void printAllSubStr(String str) {
        if (str == null || str.length() == 0) {
            return;
        }
        char[] charArr = str.toCharArray();
        printSubStrRecursion(charArr, 0, new ArrayList<>());
        System.out.println();
    }

    /**
     * 打印一个字符串所有的子集 的递归解
     * 
     * @param charArr 字符数组
     * @param i       当前正在处理的字符index
     * @param res     当前已经处理完成的记录
     */
    public static void printSubStrRecursion(char[] charArr, int i, List<Character> res) {
        // 当index移动到数组长度，说明处理完成，打印结果
        if (i == charArr.length) {
            printList(res);
            return;
        }
        // 拷贝一份res
        List<Character> includeChar = copyRes(res);
        // 加入当前的字符
        includeChar.add(charArr[i]);
        // 继续处理 index + 1 的字符
        printSubStrRecursion(charArr, i + 1, includeChar);

        // 拷贝一份res
        List<Character> notIncludeChar = copyRes(res);
        // 不加入当前字符并继续处理 index + 1 的字符
        printSubStrRecursion(charArr, i + 1, notIncludeChar);
    }

    /**
     * 复制一个字符List并返回
     * 
     * @param res
     * @return
     */
    public static List<Character> copyRes(List<Character> res) {
        List<Character> newList = new ArrayList<>();
        for (char i : res) {
            newList.add(i);
        }
        return newList;
    }

    /**
     * 依次打印字符List
     * 
     * @param res
     */
    public static void printList(List<Character> res) {
        System.out.print("[ ");
        for (char i : res) {
            System.out.print(i);
        }
        System.out.print(" ]");
    }

    /**
     * 汉诺塔问题，将大小不一的圈从左杆移到右杆，小圈只能放在大圈上面，一次只能移动一个圈
     * 
     * @param n
     */
    public static void hanoi(int n) {
        if (n <= 0) {
            System.out.println("invalid input");
            return;
        }
        // n个圈，从左杆移到右杆
        hanoiRecursion(n, "左", "右", "中");
    }

    /**
     * 汉诺塔问题递归解
     * 
     * @param n    // n圈
     * @param from // 从from杆移出
     * @param to   // 移动到to杆
     * @param temp // 提供的用来暂存的杆
     */
    public static void hanoiRecursion(int n, String from, String to, String temp) {
        if (n == 1) {
            // 只剩一个圈需要移动的时候，从所在杆直接移动到目的杆
            System.out.println("move " + n + " from " + from + " " + to);
            return;
        }
        // 先将n-1个圈从所在杆移动到缓存杆
        hanoiRecursion(n - 1, from, temp, to);
        // 将最底下的最大圈移动到目的杆
        System.out.println("move " + n + " from " + from + " " + to);
        // 先将n-1个圈从缓存杆移动到目的杆
        hanoiRecursion(n - 1, temp, to, from);
    }

    public static void main(String[] args) {
        hanoi(3);
        printAllSubStr("abcd");
        printAllSubStr2("abcd");
        strPermuatation("abcd");
        int[] cards = new int[] { 8, 9, 3, 4, 5, 6, 7 };
        takeCardGame(cards);
        Stack<String> stack = new Stack<>();
        stack.push("0");
        stack.push("1");
        stack.push("2");
        stack.push("3");
        stack.push("4");
        stack.push("5");
        Stack<String> original = new Stack<>();
        original.push("0");
        original.push("1");
        original.push("2");
        original.push("3");
        original.push("4");
        original.push("5");
        System.out.print("revert: [ ");
        while (!original.isEmpty()) {
            System.out.print(original.pop() + " ");
        }
        System.out.println("]");

        reverseStack(stack);
        System.out.print("original: [ ");
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
        System.out.println("]");

        numConvertStr("12356623");
        numConvertStr("124121");

        int[] weights = new int[] { 1, 2, 3, 4, 6, 8 };
        int[] values = new int[] { 1, 2, 8, 4, 24, 12 };
        int maxWeight = 10;
        largestValue(weights, values, maxWeight);
    }
}
