package com.fickle.code.thought.dynamic.matrixPath;

/**
 * 最小路径和问题解决方案
 * <p>
 * 给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，
 * 使得路径上的数字总和为最小。
 * <p>
 * 说明：每次只能向下或者向右移动一步。
 * <p>
 * 示例：
 * 输入：grid = [[1,3,1],
 * [1,5,1],
 * [4,2,1]]
 * 输出：7
 * 解释：因为路径 1→3→1→1→1 的总和最小。
 */
public class L64 {

    /**
     * 动态规划解法 - 构造相同大小的dp矩阵存储到达每个位置的最小路径和
     * <p>
     * 解题思路：
     * 1. 创建与原网格同样大小的dp数组
     * 2. dp[i][j] 表示从左上角到位置(i,j)的最小路径和
     * 3. 状态转移方程：
     * - 第一行：dp[0][j] = dp[0][j-1] + grid[0][j]
     * - 第一列：dp[i][0] = dp[i-1][0] + grid[i][0]
     * - 其他位置：dp[i][j] = min(dp[i-1][j], dp[i][j-1]) + grid[i][j]
     * <p>
     * 时间复杂度：O(m*n)
     * 空间复杂度：O(m*n)
     *
     * @param grid 输入的二维网格数组
     * @return 从左上角到右下角的最小路径和
     */
    public int minPathSum(int[][] grid) {
        // 获取网格的行数和列数
        int row = grid.length;
        int col = grid[0].length;

        // 创建dp数组，dp[i][j]表示从起点到位置(i,j)的最小路径和
        int[][] dp = new int[row][col];

        // 遍历整个网格填充dp数组
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // 处理第一行（只能从左边来）
                if (i == 0) {
                    // 处理起点(0,0)
                    if (j == 0) {
                        dp[i][j] = grid[i][j];
                        continue;
                    }
                    // 第一行其他元素：当前值 + 左边元素的最小路径和
                    dp[i][j] = grid[i][j] + dp[i][j - 1];
                }
                // 处理第一列（只能从上面来）
                else if (j == 0) {
                    dp[i][j] = grid[i][j] + dp[i - 1][j];
                }
                // 处理其余位置（可以选择从上面或左边来的较小值）
                else {
                    dp[i][j] = grid[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // 返回右下角位置的最小路径和
        return dp[row - 1][col - 1];
    }

    /**
     * 回溯算法解法 - 利用原数组存储中间结果进行记忆化搜索
     * <p>
     * 解题思路：
     * 1. 使用递归方式从起点到终点计算最小路径和
     * 2. 利用原数组存储已计算的结果（以负数形式），避免重复计算
     * 3. 这是一种自顶向下的动态规划方法
     * <p>
     * 时间复杂度：O(m*n)
     * 空间复杂度：O(m+n) （递归栈深度）
     *
     * @param grid 输入的二维网格数组
     * @return 从左上角到右下角的最小路径和
     */
    public int minPathSum1(int[][] grid) {
        // 从起点(0,0)开始回溯
        return backtrack(grid, 0, 0);
    }

    /**
     * 回溯算法实现 - 计算从位置(i,j)到右下角的最小路径和
     * <p>
     * 先直接到终点，然后从终点弹栈回溯
     * <p>
     * 使用记忆化技术：将计算结果存储在原数组中（以负数形式），避免重复计算
     *
     * @param grid 输入的二维网格数组（会被修改用于存储中间结果）
     * @param i    当前行索引
     * @param j    当前列索引
     * @return 从位置(i,j)到右下角的最小路径和
     */
    private int backtrack(int[][] grid, int i, int j) {
        // 获取当前位置的值
        int v = grid[i][j];

        // 如果该位置已经计算过（值为负数），直接返回其绝对值
        // 这是记忆化搜索的关键：避免重复计算
        if (v < 0) {
            return 0 - v;
        }

        // 处理边界情况
        // 当处于最后一列时，只能向下移动
        if (j == grid[0].length - 1) {
            // 当前不是最后一行，继续向下移动
            if (i < grid.length - 1) {
                v += backtrack(grid, i + 1, j);
            }
        }
        // 当处于最后一行时，只能向右移动
        else if (i == grid.length - 1) {
            v += backtrack(grid, i, j + 1);
        }
        // 其他情况：可以选择向右或向下，选择路径和较小的方向
        else {
            v += Math.min(backtrack(grid, i, j + 1), backtrack(grid, i + 1, j));
        }

        // 将计算结果以负数形式存储在原数组中，作为记忆化标记
        grid[i][j] = 0 - v;
        return v;
    }
}
