package DP;

public class DynamicProgramming {

    public static void printDP(int[][] dp) {
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.println("");
        }
    }

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

    public static int checkInside(int[][][] dp, int currH, int currX, int currY) {
        if (currH < 0 || currX < 0 || currY < 0 || currH > dp.length - 1
                || currX > dp[0].length - 1 || currY > dp[0][0].length - 1) {
            return 0;
        }
        return dp[currH][currX][currY];
    }

    public static int checkInside(int[][] dp, int currX, int currY) {
        if (currX < 0 || currY < 0
                || currX > dp.length - 1 || currY > dp[0].length - 1) {
            return 0;
        }
        return dp[currX][currY];
    }

    public static int coinsCompDP(int[] arr, int cost) {
        int[][] dp = new int[arr.length + 1][cost + 1];

        dp[arr.length][0] = 1;
        for (int j = 1; j <= cost; j++) {
            dp[arr.length][j] = 0;
        }

        for (int i = arr.length - 1; i >= 0; i--) {
            for (int j = 0; j <= cost; j++) {
                dp[i][j] += dp[i + 1][j] + checkInside(dp, i, j - arr[i]);
            }
        }

        return dp[0][cost];

    }

    public static long gcd(long m, long n) {
        return n == 0 ? m : gcd(n, m % n);
    }

    public static String surviveDP(int X, int Y, int currX, int currY, int steps) {
        int[][][] dp = new int[steps + 1][X][Y];
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                dp[0][i][j] = 1;
            }
        }
        for (int height = 1; height <= steps; height++) {
            for (int i = 0; i < X; i++) {
                for (int j = 0; j < Y; j++) {
                    dp[height][i][j] += checkInside(dp, height - 1, i + 1, j);
                    dp[height][i][j] += checkInside(dp, height - 1, i - 1, j);
                    dp[height][i][j] += checkInside(dp, height - 1, i, j + 1);
                    dp[height][i][j] += checkInside(dp, height - 1, i, j - 1);
                }
            }
        }

        long all = (long) Math.pow(4, steps);
        long res = (long) dp[steps][currX][currY];
        long divider = gcd(all, res);

        return String.valueOf((res / divider) + "/" + (all / divider));
    }

    public static int wayChessDP(int X, int Y, int currX, int currY, int steps) {
        int[][][] dp = new int[steps + 1][X + 1][Y + 1];
        dp[0][currX][currY] = 1;
        for (int height = 1; height <= steps; height++) {
            for (int i = 0; i <= X; i++) {
                for (int j = 0; j <= Y; j++) {
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
        return dp[steps][0][0];
    }

    public static int takeCardGameDP(int[] arr) {
        int length = arr.length;
        int[][] firstDP = new int[length][length];
        int[][] secondDP = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (i == j) {
                    firstDP[i][j] = arr[i];
                    secondDP[i][j] = 0;
                } else if (i > j) {
                    firstDP[i][j] = -1;
                    secondDP[i][j] = -1;
                }
            }
        }

        int col = 1;
        while (col < length) {
            int i = 0;
            int j = col;
            while (i < length && j < length) {
                firstDP[i][j] = Math.max(arr[i] + secondDP[i + 1][j], arr[j] + secondDP[i][j - 1]);
                secondDP[i][j] = Math.min(firstDP[i + 1][j], firstDP[i][j - 1]);
                i++;
                j++;
            }
            col++;
        }

        return Math.max(firstDP[0][length - 1], secondDP[0][length - 1]);

    }

    public static int wayTakeCoinDP(int[] coins, int total) {
        int length = coins.length;
        int[][] dp = new int[length + 1][total + 1];
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

        for (int i = length - 1; i >= 0; i--) {
            for (int j = 1; j <= total; j++) {
                int p1 = dp[i + 1][j];
                int p2 = -1;
                if (j - coins[i] >= 0) {
                    p2 = dp[i + 1][j - coins[i]];
                }
                if (p1 == -1 && p2 == -1) {
                    dp[i][j] = -1;
                } else if (p1 == -1) {
                    dp[i][j] = p2 + 1;
                } else if (p2 == -1) {
                    dp[i][j] = p1;
                } else {
                    dp[i][j] = Math.min(p1, p2 + 1);
                }
            }
        }
        return dp[0][total];
    }

    public static int wayWalkDP(int N, int E, int steps, int curr) {
        int[][] dp = new int[steps + 1][N + 1];
        for (int i = 0; i <= steps; i++) {
            for (int j = 0; j <= N; j++) {
                dp[i][j] = -1;
                if (i == 0) {
                    if (j == E) {
                        dp[i][j] = 1;
                    } else if (j != 0) {
                        dp[i][j] = 0;
                    }
                }
            }
        }

        for (int i = 1; i <= steps; i++) {
            for (int j = 1; j <= N; j++) {
                if (j == 1) {
                    dp[i][j] = dp[i - 1][2] == -1 ? -1 : dp[i - 1][2];
                } else if (j == N) {
                    dp[i][j] = dp[i - 1][N - 1] == -1 ? -1 : dp[i - 1][N - 1];
                } else {
                    if (dp[i - 1][j - 1] == -1 || dp[i - 1][j + 1] == -1) {
                        dp[i][j] = -1;
                    } else {
                        dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j + 1];
                    }
                }
            }
        }
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
