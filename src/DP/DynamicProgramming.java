package DP;

public class DynamicProgramming {

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
    }
}
