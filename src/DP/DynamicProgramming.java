package DP;

public class DynamicProgramming {

    /**
     * 打印2维数组
     * 
     * @param dp
     */
    public static void printDP(int[][] dp) {
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.println("");
        }
    }

    /**
     * 打印3维数组
     * 
     * @param dp
     */
    public static void printDP(int[][][] dp) {
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                for (int k = 0; k < dp[0][0].length; k++) {
                    System.out.print(dp[i][j][k] + " ");
                }
                System.out.println("");
            }
            System.out.println("");
        }
    }

    /**
     * 检查某个位置是否越界(3维数组)
     * 如果越界，返回0，否则返回数组中这个位置记录的值
     * 
     * @param dp    // 3维数组
     * @param currH // 某个位置的高
     * @param currX // 某个位置的长
     * @param currY // 某个位置的宽
     * @return
     */
    public static int checkInside(int[][][] dp, int currH, int currX, int currY) {
        if (currH < 0 || currX < 0 || currY < 0 || currH > dp.length - 1
                || currX > dp[0].length - 1 || currY > dp[0][0].length - 1) {
            return 0;
        }
        return dp[currH][currX][currY];
    }

    /**
     * 检查某个位置是否越界(2维数组)
     * * 如果越界，返回0，否则返回数组中这个位置记录的值
     * 
     * @param dp    // 2维数组
     * @param currX // 某个位置的长
     * @param currY // 某个位置的宽
     * @return
     */
    public static int checkInside(int[][] dp, int currX, int currY) {
        if (currX < 0 || currY < 0
                || currX > dp.length - 1 || currY > dp[0].length - 1) {
            return 0;
        }
        return dp[currX][currY];
    }

    /**
     * 动态规划解决币值组成价值问题
     * 用给定币值的硬币组成一定的价值，每种币值可以任意张，有多少种组成方法
     * 如：给定4种币值[2,3,5,7]，组成10块钱
     * 共有5种组成办法：2+2+2+2+2，2+2+3+3，2+3+5，3+7，5+5
     * 
     * @param arr
     * @param cost
     * @return
     */
    public static int coinsCompDP(int[] arr, int cost) {
        // 动态规划数组
        // 长度：从 0 到 arr数组长度，所以 length + 1
        // 宽度：从 0 到 cost价值，所以 cost + 1
        int[][] dp = new int[arr.length + 1][cost + 1];

        // 当最后一种币值考虑完毕，剩下0块钱，代表之前币值的组成方法成功得到cost价值，
        // 动态规划数组中记录为1
        // 其他位置，由于array初始化为0，暂时不需要填写
        dp[arr.length][0] = 1;

        // 从底层向上填写，从左边向右边填写。
        for (int i = arr.length - 1; i >= 0; i--) {
            for (int j = 0; j <= cost; j++) {
                // 每个位置依赖于两个位置，并且为两者记录之和：
                // 1：正下方的记录。因为代表当前币值不参与，继续用剩下币值组合出价值
                // 2：本币值，从0张开始，一直到N张（N*币值<=价值），每种情况下，可以成功的组合方式数
                // 第2种是当前位置下方一行中，多个位置之和。这个和恰好是当前行、当前价值减去当前币值所在位置的记录
                // 所以可以简化为 1 2 两种情况下，两个记录之和
                dp[i][j] += dp[i + 1][j] + checkInside(dp, i, j - arr[i]);
            }
        }
        // 从0位置的币值开始考虑，需要组成cost价值，所以这个位置的记录就是答案
        return dp[0][cost];
    }

    /**
     * 求最大公因数
     * 
     * @param m
     * @param n
     * @return
     */
    public static long gcd(long m, long n) {
        // 始终可以保持 m > n
        // 因为：如果 m < n, m % n = m, gcd(n, m % n)等于将两者颠倒
        // m > n 时，m % n 得到余数，相当于辗转相除
        // 当可以除尽时，n == 0， 代表最大公因数是m
        return n == 0 ? m : gcd(n, m % n);
    }

    /**
     * 动态规划解决生存问题
     * 给一个范围，给一个当前位置，给一个步数，每步必须向前后左右任意方向移动1的距离
     * 如果走出范围，则生存失败，如果最终在有效范围内，则生存成功
     * 生存成功的概率是多少
     * 
     * @param X     给定范围的长
     * @param Y     给定范围的宽
     * @param currX 当前位置的长
     * @param currY 当前位置的宽
     * @param steps 步数
     * @return
     */
    public static String surviveDP(int X, int Y, int currX, int currY, int steps) {
        // 动态规划数组，高从 0 到 steps，所以为steps + 1
        // 长和宽都不能超过给定范围，所以分别为X 和 Y
        int[][][] dp = new int[steps + 1][X][Y];
        // 初始化数据，如果走完所有步数时，在有效范围内，则生存成功，成功的方法为1
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                dp[0][i][j] = 1;
            }
        }
        // 从step剩1开始填写
        for (int height = 1; height <= steps; height++) {
            for (int i = 0; i < X; i++) {
                for (int j = 0; j < Y; j++) {
                    // 每个位置依赖于4个位置，并为这些位置的记录之和
                    // 分别代表向前后左右移动1的距离
                    // 如果所依赖的位置越界，所依赖位置的记录为0
                    dp[height][i][j] += checkInside(dp, height - 1, i + 1, j);
                    dp[height][i][j] += checkInside(dp, height - 1, i - 1, j);
                    dp[height][i][j] += checkInside(dp, height - 1, i, j + 1);
                    dp[height][i][j] += checkInside(dp, height - 1, i, j - 1);
                }
            }
        }
        // 所有的可能性
        long all = (long) Math.pow(4, steps);
        // 存活的可能性
        long res = (long) dp[steps][currX][currY];
        // 得到最大公因数
        long divider = gcd(all, res);

        // 返回存活的可能性（分数形式）
        return String.valueOf((res / divider) + "/" + (all / divider));
    }

    /**
     * 动态规划解决象棋中马走到目标位置的问题
     * 给一个棋盘的范围，给一个目标位置，给一个规定步数
     * 走规定步数次后，马从原点(0,0)到达目标位置总共有多少种走法
     * 
     * @param X     棋盘的长
     * @param Y     棋盘的宽
     * @param currX 目标位置的长
     * @param currY 目标位置的宽
     * @param steps
     * @return
     */
    public static int wayChessDP(int X, int Y, int currX, int currY, int steps) {
        // 动态规划数组
        // 从 0 到 step 步数，所以高为step + 1
        // 不可以超出范围，所以长宽分别为X Y
        int[][][] dp = new int[steps + 1][X][Y];
        // 当剩0步时，如果再目标位置，则成功的方法为1
        dp[0][currX][currY] = 1;
        // 从剩1步开始依次填写
        for (int height = 1; height <= steps; height++) {
            for (int i = 0; i < X; i++) {
                for (int j = 0; j < Y; j++) {
                    // 每个位置依赖于8个位置，分别是马的8个可以走的位置
                    // 将这个8个位置的成功方法数相加
                    // 如果越界，则为0
                    dp[height][i][j] += checkInside(dp, height - 1, i - 1, j - 2);
                    dp[height][i][j] += checkInside(dp, height - 1, i - 2, j - 1);
                    dp[height][i][j] += checkInside(dp, height - 1, i - 2, j + 1);
                    dp[height][i][j] += checkInside(dp, height - 1, i - 1, j + 2);
                    dp[height][i][j] += checkInside(dp, height - 1, i + 1, j + 2);
                    dp[height][i][j] += checkInside(dp, height - 1, i + 2, j + 1);
                    dp[height][i][j] += checkInside(dp, height - 1, i + 2, j - 1);
                    dp[height][i][j] += checkInside(dp, height - 1, i + 1, j - 2);
                }
            }
        }
        // 从(0,0)开始走step步，所以这个位置的记录就是答案
        return dp[steps][0][0];
    }

    /**
     * 动态规划解决两个人轮流拿牌的问题
     * 手牌游戏
     * 如：给一个数组[1,2,100,4] 两个玩家，轮流从最左或最右拿牌，直到拿完所有牌。得分是所有自己拿到牌的数值之和。
     * 两个玩家都是绝对理智聪明的，会做出最有利于自己的选择。
     * 返回赢家的最终得分
     * 
     * @param arr
     * @return
     */
    public static int takeCardGameDP(int[] arr) {
        int length = arr.length;
        // 先手后手两个动态规划数组
        // 长和宽分别表示数组中剩下牌的起始位置和终止位置
        int[][] firstDP = new int[length][length];
        int[][] secondDP = new int[length][length];

        // 初始化两个数组
        // 当剩下最后一张牌时，i == j
        // 对于先手者，还能拿的分数就是剩余牌的价值
        // 对于后手者，没有再加分的机会
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (i == j) {
                    // 所以在这些位置分别记录还可以增加的分数
                    firstDP[i][j] = arr[i];
                    secondDP[i][j] = 0;
                }
            }
        }

        // 对角线右上角为有效位置，因为左下角i > j 代表起始位置在终止位置之后，不可能出现
        int col = 1;
        // 沿着对角线依次填写
        while (col < length) {
            int i = 0;
            int j = col;
            while (i < length && j < length) {
                // 对于先手者来说，有两种选择，拿开头的牌或者末尾的牌，拿完之后变成后手
                // 所以可以得到的分数是两种选择中，可能获得更大分数的选择
                // 所以分数等于获取到的牌的分数，以及剩下的牌中，后手情况下获得的分数
                // 因此依赖于后手dp中，左方与下方两个位置的记录
                // 对于后手者来说，目前得不到任何分数，但是下一轮变为先手
                // 变为先手之前，依赖于另一个人基于对自己最好选择做出的判断
                // 因此依赖于先手dp中左方和下方两个位置的记录，并取其较小值
                firstDP[i][j] = Math.max(arr[i] + secondDP[i + 1][j], arr[j] + secondDP[i][j - 1]);
                secondDP[i][j] = Math.min(firstDP[i + 1][j], firstDP[i][j - 1]);
                i++;
                j++;
            }
            col++;
        }
        // 牌组从0位置到末位位置的先手和后手策略中，获得分数最大的记录就是答案
        return Math.max(firstDP[0][length - 1], secondDP[0][length - 1]);
    }

    /**
     * 动态规划解决拿硬币问题
     * 给几枚硬币，最少拿多少枚硬币凑成总价值total
     * 如：[2,3,5,7] 凑成10
     * 有两种方法：2 + 3 + 5 或者 3 + 7
     * 3 + 7 用了两枚硬币，最少，所以答案为2
     * 
     * @param coins
     * @param total
     * @return
     */
    public static int wayTakeCoinDP(int[] coins, int total) {
        int length = coins.length;
        // 动态规划数组
        // 从第0位置的硬币开始考虑，一直到全部硬币都考虑完，所以是length + 1
        // 从 0 到 total 价值，所以是total + 1
        int[][] dp = new int[length + 1][total + 1];

        // 初始化数组，没有填过的位置未-2
        // 无效位置为-1
        // 如果剩余价值为0，代表成功，并且当前没再用硬币
        for (int i = 0; i <= length; i++) {
            for (int j = 0; j <= total; j++) {
                dp[i][j] = -2;
                if (j == 0) {
                    dp[i][j] = 0;
                } else if (i == length) {
                    dp[i][j] = -1;
                }
            }
        }
        // 依次填写
        for (int i = length - 1; i >= 0; i--) {
            for (int j = 1; j <= total; j++) {
                // 每个位置依赖于两个位置，为两者之和
                // 1：正下方的位置，代表当前硬币没拿，继续考虑之后的硬币
                // 2：当前行，当前价值减去当前硬币价值的列
                int p1 = dp[i + 1][j];
                int p2 = -1;
                // 判断是否越界，越界的话，无效
                // 否则将其记录为p2
                if (j - coins[i] >= 0) {
                    p2 = dp[i + 1][j - coins[i]];
                }
                // 判断无效
                // 如果两者都无效，当前位置也无效
                if (p1 == -1 && p2 == -1) {
                    dp[i][j] = -1;
                } else if (p1 == -1) { // 如果一方无效一方有效，取有效值
                    // 如果是第二种情况，记录 +1，代表又用了一枚硬币
                    dp[i][j] = p2 + 1;
                } else if (p2 == -1) {
                    dp[i][j] = p1;
                } else { // 如果两者都有效，取用硬币数较少者
                    dp[i][j] = Math.min(p1, p2 + 1);
                }
            }
        }
        // 从第0枚硬币开始考虑，组成total价值，所以这个位置的记录就是答案
        return dp[0][total];
    }

    /**
     * 动态规划解决一维数组到达目标位置的问题
     * 给一个数组范围，目标位置，步数，当前位置
     * 每次必须向左或向右走一步，走到目标位置有多少种走法
     * 
     * @param N     数组从 0 到 N 位置
     * @param E     目标位置
     * @param steps 规定步数
     * @param curr  当前位置
     * @return
     */
    public static int wayWalkDP(int N, int E, int steps, int curr) {
        // 动态规划数组
        // 从 0 到 step 步，所以为 step + 1
        // 从 0 到 N 位置， 所以为 N+1
        int[][] dp = new int[steps + 1][N + 1];
        // 初始化数组
        for (int i = 0; i <= steps; i++) {
            for (int j = 0; j <= N; j++) {

                dp[i][j] = -1;
                if (i == 0) {
                    // 如果剩余步数为0，并且已在目标位置，那么走法为1
                    // 否则为0
                    if (j == E) {
                        dp[i][j] = 1;
                    } else if (j != 0) { // 0 位置越界
                        dp[i][j] = 0;
                    }
                }
            }
        }

        // 依次填写
        for (int i = 1; i <= steps; i++) {
            for (int j = 1; j <= N; j++) {
                if (j == 1) { // 如果走到最左边，只能往右走，走到2位置
                    dp[i][j] = dp[i - 1][2] == -1 ? -1 : dp[i - 1][2];
                } else if (j == N) { // 如果走到最右边，只能往左走，走到 N - 1 位置
                    dp[i][j] = dp[i - 1][N - 1] == -1 ? -1 : dp[i - 1][N - 1];
                } else {
                    // 如果在中间
                    // 如果向左或向右走无效，则当前位置也无效
                    if (dp[i - 1][j - 1] == -1 || dp[i - 1][j + 1] == -1) {
                        dp[i][j] = -1;
                    } else { // 否则向左向右两种结果相加
                        dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j + 1];
                    }
                }
            }
        }
        // 从curr位置走step步，所以这个位置的记录就是答案
        return dp[steps][curr];
    }

    public static void main(String[] args) {
        System.out.println("ways to get to target: " + wayWalkDP(5, 4, 4, 2));
        System.out.println("ways to take coins: " + wayTakeCoinDP(new int[] { 2, 3, 5, 7 }, 10));
        System.out.println("winner score: " + takeCardGameDP(new int[] { 8, 9, 3, 4, 5, 6, 7 }));
        System.out.println("ways to get to position: " + wayChessDP(8, 9, 3, 3, 4));
        System.out.println("ways to survive: " + surviveDP(3, 3, 0, 0, 2));
        System.out.println("ways to compose money: " + coinsCompDP(new int[] { 2, 3, 5, 7 }, 10));

    }
}
