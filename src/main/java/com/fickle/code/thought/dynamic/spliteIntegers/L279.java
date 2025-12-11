package com.fickle.code.thought.dynamic.spliteIntegers;

/**
 * 完全平方数
 * <p>
 * 给你一个整数 n ，返回 和为 n 的完全平方数的最少数量。
 * 完全平方数 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。
 * <p>
 * 示例 1：
 * 输入：n = 12
 * 输出：3
 * 解释：12 = 4 + 4 + 4
 * <p>
 * 示例 2：
 * 输入：n = 13
 * 输出：2
 * 解释：13 = 4 + 9
 *
 * @author Administrator
 * @apiNote com.fickle.code.thought.dynamic.spliteIntegers
 */
public class L279 {

    /**
     * 其实相当于求所有完全平方数中和为n的最少数量(完全平方数可以重复)
     * 从[1,i^1/2]中取，假设当前枚举到 j，那么我们还需要取若干数的平方，构成 i−j*j
     * 此时我们发现该子问题和原问题类似，只是规模变小了。这符合了动态规划的要求
     * 于是我们可以写出状态转移方程:
     * f[i]=1+ min[1,i^1/2]f[i−j*j]
     *
     * @param n 整数
     * @return 和为 n 的完全平方数的最少数量
     */
    public int numSquares(int n) {
        int[] f = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int minn = Integer.MAX_VALUE;
            for (int j = 1; j * j <= i; j++) {
                minn = Math.min(minn, f[i - j * j]);
            }
            f[i] = minn + 1;
        }
        return f[n];
    }
}
