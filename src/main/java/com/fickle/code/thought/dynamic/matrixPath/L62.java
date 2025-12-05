package com.fickle.code.thought.dynamic.matrixPath;

import java.util.Arrays;

/**
 * 不同路径
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
 * <p>
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
 * <p>
 * 问总共有多少条不同的路径？
 * <p>
 * 输入：m = 3, n = 7
 * 输出：28
 *
 * @author Administrator
 * @apiNote com.fickle.code.thought.dynamic.matrixPath
 */
public class L62 {

    /**
     * 计算并保存每一步的走法，每次都走一步
     * - 状态定义：dp[i][j]表示从起点到达位置(i,j)的不同路径数
     * - 状态转移方程：dp[i][j] = dp[i-1][j] + dp[i][j-1]
     * - 边界条件：第一行和第一列的所有位置都只有一种路径
     *
     * 转换为一维DP：dp[j] = dp[j] + dp[j-1]
     * 思路为：将dp看作是每一行的数据，并进行更新。
     * 比如：第一行时，只有一种方式，所以都填充为1.
     *      到第二行时，我已有第一行的所有数据，将初始的第一列的值更新：dp[0]（将此位置替换为本行的数据） = dp[0]（上一行的数据） + dp[-1]（因为是第一列，没有左侧，所以为0）
     *      dp[1]位置的值为dp[1]（这个是上一行的数据） + dp[0]（来自本行左侧数据）
     *
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m, int n) {
        // 使用一维数组存储每列的路径数
        int[] dp = new int[n];

        // 初始化：第一行所有位置路径数都为1
        Arrays.fill(dp, 1);

        // 从第二行开始计算
        for (int i = 1; i < m; i++) {
            // 从第二列开始更新（第一列始终为1）
            for (int j = 1; j < n; j++) {
                // dp[j](本行该列的值) = dp[j] (上一行该列的值) + dp[j-1] (本行前一列的值)
                dp[j] = dp[j] + dp[j - 1];
            }
        }

        // 返回右下角位置的路径数
        return dp[n - 1];


    }
}
