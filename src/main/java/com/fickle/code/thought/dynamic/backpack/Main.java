package com.fickle.code.thought.dynamic.backpack;

/**
 * 0-1背包问题的通用场景
 *
 * @author Administrator
 * @apiNote com.fickle.code.thought.dynamic.backpack
 */
public class Main {
    public int knapsack(int W, int N, int[] weights, int[] values) {
        int[][] dp = new int[N + 1][W + 1];
        for (int i = 1; i <= N; i++) {
            // 第i个数 的体积和价值
            int w = weights[i - 1], v = values[i - 1];
            for (int j = 1; j <= W; j++) {
                if (j >= w) { // 判断当前j是否可以放入w
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - w] + v);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[N][W];
    }

    /**
     * 在程序实现时可以对 0-1 背包做优化。
     * 观察状态转移方程可以知道，前 i 件物品的状态仅与前 i-1 件物品的状态有关，因此可以将 dp 定义为一维数组，
     * 其中 dp[j] 既可以表示 dp[i-1][j] 也可以表示 dp[i][j]。此时:
     * dp[j] = max(dp[j],dp[j-w] + v)
     * 因为 dp[j-w] 表示 dp[i-1][j-w]，因此不能先求 dp[i][j-w]，以防将 dp[i-1][j-w] 覆盖。
     * 也就是说要先计算 dp[i][j] 再计算 dp[i][j-w]，在程序实现时需要按倒序来循环求解。
     */
    public int knapsack1(int W, int N, int[] weights, int[] values) {
        int[] dp = new int[W + 1];
        for (int i = 1; i <= N; i++) {
            int w = weights[i - 1], v = values[i - 1];
            for (int j = W; j >= 1; j--) {
                if (j >= w) { // 在当前体积下的最大值，如果j < w则不需要计算，沿用上次的
                    dp[j] = Math.max(dp[j], dp[j - w] + v);
                }
            }
        }
        return dp[W];
    }
}
