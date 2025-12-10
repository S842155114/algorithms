package com.fickle.code.thought.dynamic.array;

import java.util.Arrays;

/**
 * 等差数列划分
 * <p>
 * 如果一个数列 至少有三个元素 ，并且任意两个相邻元素之差相同，则称该数列为等差数列。
 * 例如，[1,3,5,7,9]、[7,7,7,7] 和 [3,-1,-5,-9] 都是等差数列。
 * 给你一个整数数组 nums ，返回数组 nums 中所有为等差数组的 子数组 个数。
 * 子数组 是数组中的一个连续序列。
 * <p>
 * 示例 1：
 * 输入：nums = [1,2,3,4]
 * 输出：3
 * 解释：nums 中有三个子等差数组：[1, 2, 3]、[2, 3, 4] 和 [1,2,3,4] 自身。
 *
 * @author Administrator
 * @apiNote com.fickle.code.thought.dynamic.array
 */
public class L413 {
    /**
     * 首先，因为连续，可以先找出所有等差子数组，再将子数组可以切分的进行处理
     *
     * @param nums 整数数组
     * @return nums 中所有为等差数组的 子数组（数组中的一个连续序列） 个数
     */
    public int numberOfArithmeticSlices(int[] nums) {
        if (nums.length < 3) {
            return 0;
        }
        int count = nums.length / 3 + 1;
        // 等差子数组，每一行存储的两个值为子数组的开始和结束索引
        int[][] child = new int[count][2];
        // 遍历nums的索引
        int i = 1;
        // j为child的行数
        int j = 0;
        // 等差数组的差
        int d = Integer.MAX_VALUE;
        while (i < nums.length) {
            // 求当前值与前一位值的差
            int n = nums[i - 1] - nums[i];
            // 当d为初始值证明是开始
            if (d == Integer.MAX_VALUE) {
                child[j][0] = i - 1;
                d = n;
                i++;
                continue;
            }
            // 开始等差
            if (n == d) {
                if (i - child[j][0] > 1) {
                    child[j][1] = i;
                }

                i++;
            } else { // 结束等差
                d = Integer.MAX_VALUE;
                if (child[j][1] != 0) {
                    j++;
                }
            }
        }
        // 把child中的所有等差序列进行拆分，找出最小为3个数的所有可能
        int result = 0;
        for (int k = 0; k < count; k++) {
            int n = child[k][1] - child[k][0] + 1;
            if (n < 1) {
                continue;
            }
            // 数学计算方式，计算从1到n-2的所有连续正整数的和
            result += (n - 2) * (n - 1) / 2;
        }

        return result;
    }

    /**
     * 定义状态：dp[i]表示 从nums[0]到nums[i] 且以nums[i]为结尾的等差数列子数组的数量。
     * <p>
     * 状态转移方程：dp[i] = dp[i-1]+1 if nums[i]-nums[i-1]==nums[i-1]-nums[i-2] else 0
     * <p>
     * 解释：如果nums[i]能和nums[i-1] nums[i-2]组成等差数列，则以nums[i-1]结尾的等差数列均可以nums[i]结尾，
     *      且多了一个新等差数列[nums[i],nums[i-1],nums[i-2]]
     */
    public int numberOfArithmeticSlices1(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        for (int i = 2; i < n; i++) {
            if (nums[i] - nums[i - 1] == nums[i - 1] - nums[i - 2]) {
                dp[i] = dp[i - 1] + 1;
            }
            dp[i] = 0;
        }
        return Arrays.stream(dp).sum();
    }

    /**
     * dp解法
     * dp计数 l为 以此时遍历到的数 结尾的子数组数量
     */
    public int numberOfArithmeticSlices2(int[] nums) {
        int n = nums.length, l = 0, ans = 0;
        for (int i = 2; i < n; i++) {
            if (nums[i] + nums[i - 2] == nums[i - 1] * 2) {
                ans += ++l;
            }
            l = 0;
        }
        return ans;
    }

    public static void main(String[] args) {
        L413 l = new L413();
        System.out.println(l.numberOfArithmeticSlices(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}));
    }
}
