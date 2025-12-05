package com.fickle.code.thought.dynamic.fibonacci;

/**
 * 题目描述: 假设农场中成熟的母牛每年都会生 1 头小母牛，并且永远不会死。
 * 第一年有 1 只小母牛，从第二年开始，母牛开始生小母牛。
 * 每只小母牛 3 年之后成熟又可以生小母牛。
 * <p>
 * 给定整数 N，求 N 年后牛的数量。
 *
 * @author Administrator
 * @apiNote com.fickle.code.thought.dynamic.fibonacci
 */
public class Cows {
    /**
     * 计算第n年后牛的数量
     *
     * 解题思路：
     * 设C(n)为第n年牛的总数，则：
     * - 第n年的牛数量 = 第n-1年存活的牛 + 第n年新出生的牛
     * - 只有成熟的母牛才能生育，而每只小母牛需要3年才能成熟
     * - 因此第n年能生育的母牛数量 = 第n-3年时牛的总数
     * - 第n年新出生的小牛数量 = 第n-3年时牛的总数
     * - 所以递推关系为：C(n) = C(n-1) + C(n-3)
     *
     * 边界条件：
     * - C(1) = 1 (第一年只有1只小牛)
     * - C(2) = 1 (第二年初始牛还未成熟)
     * - C(3) = 1 (第三年初始牛还未成熟)
     * - C(4) = C(3) + C(1) = 1 + 1 = 2
     *
     * @param n 年份
     * @return 第n年后牛的数量
     */
    public int countCows(int n) {
        // 处理无效输入
        if (n <= 0) {
            return 0;
        }

        // 处理基础情况
        if (n < 4) {
            return 1;
        }

        // 使用滚动数组优化空间复杂度
        // prev3: 三年前的牛数量 C(n-3)
        // prev2: 两年前的牛数量 C(n-2)
        // prev1: 一年前的牛数量 C(n-1)
        int prev3 = 1; // C(1) = 1
        int prev2 = 1; // C(2) = 1
        int prev1 = 1; // C(3) = 1
        int current = 0;

        // 从第4年开始计算到第n年
        for (int i = 3; i < n; i++) {
            // 根据递推关系：C(n) = C(n-1) + C(n-3)
            current = prev1 + prev3;

            // 更新状态：向后滚动一年
            prev3 = prev2;
            prev2 = prev1;
            prev1 = current;
        }

        return current;
    }

}
