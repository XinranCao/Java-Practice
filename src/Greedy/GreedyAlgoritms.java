package Greedy;

import java.util.*;

public class GreedyAlgoritms {

    /**
     * N皇后问题
     * 
     * @param num
     * @param mode “quick”：位运算优化代码。或其他：普通解
     * @return 返回总共有多少种放置queen的方案
     */
    public static int queenProblem(int num, String mode) {
        if (num < 1) {
            return 0;
        }

        if (mode == "quick") {
            // quick mode只支持<=32的皇后问题，因为整数只有32位，位运算利用的是2进制做记录
            if (num > 32) {
                System.out.println("quick algorithm only solve num <= 32");
                return 0;
            }
            // -1的2进制是32个1
            int limit = num == 32 ? -1 : (1 << num) - 1;
            return numQueenRowNQuick(limit, 0, 0, 0);
        }
        int[] record = new int[num];
        return numQueenRowN(0, record, num);
    }

    /**
     * N皇后问题，普通的暴力求解方法
     * 在常数时间的操作上，利用位运算优化。但总时间依然是 O(N^N) 级别
     * 
     * @param limit      2进制位数上，从右到左依次有N个1，代表这是N皇后问题，棋盘允许N列上可以放置queen
     * @param colLimit   之前已放置的所有queen所在的列，这行的queen都不能放置。2进制位上，1代表不能放置，0可以放置。
     * @param leftLimit  之前已放置的所有queen向左下延伸的斜线，这行的queen都不能放置。2进制位上，1代表不能放置，0可以放置。
     * @param rightLimit 之前已放置的所有queen向右下延伸的斜线，这行的queen都不能放置。2进制位上，1代表不能放置，0可以放置。
     * @return
     */
    public static int numQueenRowNQuick(int limit, int colLimit, int leftLimit, int rightLimit) {
        // 如果colLimit前N为都是1（与limit相同）则代表N列上都已放置queen，合理放置所有queen的方案+1
        if (limit == colLimit) {
            return 1;
        }
        int count = 0;
        // 这一行所有queen可以放置的位置，2进制位数上为1的位置可以放置，0不可以放置
        // col、left、right三个相互或运算再取反，得出的2进制数上，1代表可以放置，0代表不可以放置
        // 再与limit与运算，去除N位向左的所有1
        int possible = limit & (~(colLimit | leftLimit | rightLimit));
        int rightMostOne = 0;
        // 将这行所有可能放置的位置进行尝试
        while (possible != 0) {
            // 取出最右边第一个1，即第一个可能放置的位置
            rightMostOne = possible & (~possible + 1);
            // 更新需要尝试的位置
            possible -= rightMostOne;
            // 从下一行继续尝试
            count += numQueenRowNQuick(
                    limit,
                    colLimit | rightMostOne, // 这一行在rightMostOne位置放置了queen，更新colLimit
                    (leftLimit | rightMostOne) << 1, // 这一行在rightMostOne位置放置了queen，更新leftLimit。所有位置向左移动1，（斜线向左下）
                    (rightLimit | rightMostOne) >>> 1); // 这一行在rightMostOne位置放置了queen，更新rightLimit。所有位置向右移动1，（斜线向右下）
            // >>>代表无符号右移，左边补0
        }
        return count;
    }

    /**
     * N皇后问题，普通的暴力求解方法
     * 
     * @param currRow 当前行
     * @param record  记录已放置queen位置
     * @param row     总共有多少行
     * @return
     */
    public static int numQueenRowN(int currRow, int[] record, int row) {
        if (currRow == row) { // 如果当前行index为总行数，代表已走出棋盘，所有queen都合理放置
            return 1; // 所以成功的放置方案+1
        }
        int count = 0;
        // 依次尝试在每一列放置queen是否可行，共有多少可行方案
        for (int i = 0; i < row; i++) {
            if (queenValid(record, currRow, i)) { // 如果再i列放置queen可行
                record[currRow] = i; // 记录queen位置
                count += numQueenRowN(currRow + 1, record, row); // 去下一行寻找下一个queen放置位置
            }
        }
        return count;
    }

    /**
     * 判断row这行中，col列是否可以放置queen（不与之前放置的queen位置冲突）
     * 
     * @param record 用来记录每行中queen的位置，如：record[3] = 0 代表在第4行第1列有一个queen
     * @param row
     * @param col
     * @return
     */
    public static boolean queenValid(int[] record, int row, int col) {
        // 对于每一个之前放置过的queen
        for (int i = 0; i < row; i++) {
            // 如果即将放置的queen与已放置的queen在同列，或者处于同一斜线上，则这个queen的位置并不合规。
            if (record[i] == col // 判断在同一斜线的方法：行位置差与列位置差相同的话，处在45°斜线上
                    || (Math.abs(record[i] - col) == Math.abs(i - row))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 一个整数流，用于解决【随时新加入一个数到一个已知数组中，立马获取这个数组的中位数】问题
     */
    public static class NumStream {
        // 大根堆，用于记录前一半数组
        private PriorityQueue<Integer> firstHalf;
        // 小根堆，用于记录后一半数组
        private PriorityQueue<Integer> secondHalf;

        /**
         * 用于将整数从大到小排列的比较器
         */
        private class secondComparator implements Comparator<Integer> {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        }

        public NumStream() {
            firstHalf = new PriorityQueue<>(new secondComparator());
            secondHalf = new PriorityQueue<>();
        }

        public float insertAndGetMedian(int newInt) {
            if (firstHalf.isEmpty()) { // 如果前半数组为空，则这个新数是数组中第一个数
                firstHalf.add(newInt); // 那么直接加入存放前半数组的大根堆中
            } else if (newInt <= firstHalf.peek()) { // 如果新数不大于前半数组中最大的数
                firstHalf.add(newInt); // 放入存放前半数组的大根堆中
            } else { // 否则放入存放后半数组的小根堆中
                secondHalf.add(newInt);
            }
            // 不论那一半数组的数量比另一半多2个，从多的queue中拿出一个，放入少的queue中
            if (firstHalf.size() - secondHalf.size() > 1) {
                secondHalf.add(firstHalf.poll());
            }
            if (secondHalf.size() - firstHalf.size() > 1) {
                firstHalf.add(secondHalf.poll());
            }
            // 如果加入新数字后并调整后，前半后半数组中的数量一致，则中位数是（前半数组最大值+后半数组最大值） / 2
            if (firstHalf.size() == secondHalf.size()) {
                return ((float) firstHalf.peek() + (float) secondHalf.peek()) / 2;
            } else if (firstHalf.size() > secondHalf.size()) {
                return firstHalf.peek(); // 如果加入新数并调整后，前半数组数量多，则中位数是前半数组中最大值
            } else {
                return secondHalf.peek(); // 如果加入新数并调整后，后半数组数量多，则中位数是后半数组中最小值
            }
        }
    }

    public static class BusNode {
        public int cost;
        public int profit;

        public BusNode(int c, int p) {
            cost = c;
            profit = p;
        }
    }

    /**
     * 用于将BusNode按照cost从小到大排列的比较器
     */
    public static class CostComparator implements Comparator<BusNode> {
        @Override
        public int compare(BusNode o1, BusNode o2) {
            return o1.cost - o2.cost;
        }
    }

    /**
     * 用于将BusNode按照profit从大到小排列的比较器
     */
    public static class ProfitComparator implements Comparator<BusNode> {
        @Override
        public int compare(BusNode o1, BusNode o2) {
            return o2.profit - o1.profit;
        }
    }

    /**
     * costArr 表示i号项目的花费
     * profitsArr 表示i号项目在扣除花费之后还能挣到的钱（利润）
     * 你每做完一个项目，马上获得的收益，可以支持你去做下一个项目。
     * 
     * @param k         正数k 最多做k个项目
     * @param W         正数W 初始资金
     * @param costArr   正数数组costs
     * @param profitArr 正数数组profits
     * @return 最后获得的最大钱数
     */
    public static int highestProfit(int k, int W, int[] costArr, int[] profitArr) {
        // 小根堆，用于将BusNode按照cost从小到大排列
        PriorityQueue<BusNode> lowestCost = new PriorityQueue<>(new CostComparator());
        // 大根堆，用于将BusNode按照profit从大到小排列
        PriorityQueue<BusNode> highestProfit = new PriorityQueue<>(new ProfitComparator());

        // 先根据cost-profit对生成所有BusNode，并将其放入lowestCost queue中
        for (int i = 0; i < costArr.length; i++) {
            lowestCost.add(new BusNode(costArr[i], profitArr[i]));
        }

        // k次
        while (k > 0) {
            // 如果lowestCost不为空，将里面所有cost小于初始资金W的BusNoe取出，并放入highestProfit大根堆中
            while (!lowestCost.isEmpty() && lowestCost.peek().cost <= W) {
                highestProfit.add(lowestCost.poll());
            }
            // 如果highestProfit大根堆为空，则没有任何可以盈利的机会，直接返回所有资金W
            if (highestProfit.isEmpty()) {
                return W;
            }
            // 从大根堆中取出profit最高的BusNode，将获利profit加入资金中
            W += highestProfit.poll().profit;
            k--;
        }
        return W;
    }

    /**
     * 找出最小花费
     * 
     * 一块金条切成两半，是需要花费和长度数值一样的铜板的。比如长度为20的金条，不管切成长度多大的两半，都要花费20个铜板。
     * 一群人想整分整块金条，怎么分最省铜板？
     * 例如，给定数组（10,20.30)，代表一共三个人，整块金条长度为10+20+30=60。
     * 金条要分成10.20.30.
     * 如果先把长度60的金条分成10和50，花费60；
     * 再把长度50的金条分成20和30，花费50；
     * 一共花费110铜板.
     * 但是如果先把长度60的金条分成30和30，花费60；再把长度30金条分成10和20，
     * 花费30；
     * 一共花费90铜板。
     * 输入一个数组，返回分割的最小代价。
     * 
     * @param arr
     * @return
     */
    public static int lowestCost(int[] arr) {
        int res = 0;
        if (arr == null || arr.length == 0) {
            return res;
        }
        // 将数组从小到大排列
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (Integer item : arr) {
            queue.add(item);
        }
        int temp = 0;
        // 当queue里只剩1个元素时，停止
        while (queue.size() > 1) {
            // 每次取出最小的两个值并相加
            temp = queue.poll() + queue.poll();
            // 总花费为之前的花费加上两个值相加的结果
            res += temp;
            // 将两个值相加的结果放回queue中
            queue.add(temp);
        }
        return res;
    }

    /**
     * 若(str_1 + str_2) < (str_2 + str_1)，则str_1 < str_2
     */
    public static class StringComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return (o1 + o2).compareTo(o2 + o1);
        }
    }

    /**
     * 找出将字符串数组中所有字符串相连的所有可能性中，最小字典序的排列，并返回这个新组成的字符串
     * 如：[“ab”, "bb", "a", "ba"] 可以组成的拥有最小字典序的字符串为：“aabbabb”
     * 其中 a+ab 的字典序比 a+bb 的字典序小
     * 
     * @param strings
     * @return
     */
    public static String lowestString(String[] strings) {
        if (strings == null | strings.length == 0) {
            return "";
        }
        // 将数组按 若(str_1 + str_2) < (str_2 + str_1)，则str_1 < str_2 的规则，从小到大排序
        Arrays.sort(strings, new StringComparator());
        String res = "";
        // 从小到大依次将字符串连接起来
        for (String str : strings) {
            res += str;
        }
        return res;
    }

    public static class Program {
        public int start;
        public int end;

        public Program(int st, int ed) {
            start = st;
            end = ed;
        }
    }

    /**
     * 用于将项目按照终止时间从小到大排列的比较器
     */
    public static class ProgramComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o1.end - o2.end;
        }
    }

    /**
     * 从initTime开始，找到最多可以安排多少个项目
     * 每个项目有起始时间start和终止时间end
     * 时间冲突的项目不可以同时安排
     * 
     * @param programs
     * @param initTime
     * @return
     */
    public static int mostProgramArrange(Program[] programs, int initTime) {
        int res = 0;
        if (programs == null || programs.length == 0) {
            return res;
        }
        // 将所有项目按结束时间从小到大排序
        Arrays.sort(programs, new ProgramComparator());
        for (Program program : programs) {
            // 如果这个项目起始时间不早于initTime，则安排这个项目
            if (program.start >= initTime) {
                res++;
                initTime = program.end; // 将initTime更新为这个项目的终止时间
            }
        }
        return res;
    }

    public static void main(String[] args) {

        String[] strings = new String[] { "c", "abb", "a", "bab", "bba" };
        System.out.println("lowest string: " + lowestString(strings));

        Program[] programs = new Program[] {
                new Program(0, 2),
                new Program(3, 7),
                new Program(1, 3),
                new Program(0, 1),
                new Program(2, 5),
                new Program(4, 6),
                new Program(5, 6)
        };
        System.out.println("max # of programs: " + mostProgramArrange(programs, 0));

        int[] arr = new int[] { 10, 20, 30, 15, 15 };
        System.out.println("lowest cost: " + lowestCost(arr));

        int[] costArr = new int[] { 5, 1, 3, 2, 4 };
        int[] profArr = new int[] { 1, 1, 4, 2, 1 };
        System.out.println("highest profit: " + highestProfit(4, 1, costArr, profArr));

        NumStream numbers = new NumStream();
        System.out.println("median: " + numbers.insertAndGetMedian(1));
        System.out.println("median: " + numbers.insertAndGetMedian(2));
        System.out.println("median: " + numbers.insertAndGetMedian(3));
        System.out.println("median: " + numbers.insertAndGetMedian(4));
        System.out.println("median: " + numbers.insertAndGetMedian(5));
        System.out.println("median: " + numbers.insertAndGetMedian(6));

        System.out.println("# of queens: " + queenProblem(14, "quick"));
    }

}
