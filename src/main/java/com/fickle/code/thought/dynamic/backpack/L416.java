package com.fickle.code.thought.dynamic.backpack;

/**
 * 分割等和子集-之前做过
 * <p>
 * 给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
 * <p>
 * 示例 1：
 * 输入：nums = [1,5,11,5]
 * 输出：true
 * 解释：数组可以分割成 [1, 5, 5] 和 [11] 。
 *
 * @author Administrator
 * @apiNote com.fickle.code.thought.dynamic.backpack
 */
public class L416 {
    /**
     * 两个子集的元素和相等说明当前数组的sum/2为每个子集的sum
     * 变成了判断数组中是否存在元素的和为sum/2
     * 如果用0-1背包方式做：
     * dp[j] 代表：0-i个数中任意数的和能否==j
     *
     * @param nums 只包含正整数 的 非空 数组
     * @return 是否可以将这个数组分割成两个子集，使得两个子集的元素和相等
     */
    public boolean canPartition(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return false;
        }
        int sum = 0, maxNum = 0;
        for (int num : nums) {
            sum += num;
            maxNum = Math.max(maxNum, num);
        }
        if (sum % 2 != 0) {
            return false;
        }
        int target = sum / 2;
        if (maxNum > target) {
            return false;
        }
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        for (int num : nums) {
            // j到当前值num时，j-num =0 ，dp[0] = true，则dp[j] = true
            for (int j = target; j >= num; --j) {
                dp[j] |= dp[j - num];
            }
        }
        return dp[target];
    }
}
