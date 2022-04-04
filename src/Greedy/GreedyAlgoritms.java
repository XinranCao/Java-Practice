package Greedy;

import java.util.*;

public class GreedyAlgoritms {
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
    }

}
