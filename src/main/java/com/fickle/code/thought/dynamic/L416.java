package com.fickle.code.thought.dynamic;

/**
 * @author Administrator
 * @apiNote com.fickle.code.dynamic
 */
public class L416 {

    /**
     * 给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
     * <p>
     * 示例 1：
     * 输入：nums = [1,5,11,5]
     * 输出：true
     * 解释：数组可以分割成 [1, 5, 5] 和 [11] 。
     * <p>
     * 示例 2：
     * 输入：nums = [1,2,3,5]
     * 输出：false
     * 解释：数组不能分割成两个元素和相等的子集。
     * <p>
     * 提示：
     * 1 <= nums.length <= 200
     * 1 <= nums[i] <= 100
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(canPartition(new int[]{1, 2, 3, 5}));
    }

    /**
     * 这道题可以换一种表述：给定一个只包含正整数的非空数组 nums[0]，判断是否可以从数组中选出一些数字，使得这些数字的和等于整个数组的元素和的一半。
     * 因此这个问题可以转换成「0−1 背包问题」。这道题与传统的「0−1 背包问题」的区别在于，传统的「0−1 背包问题」要求选取的物品的重量之和不能超过背包的总容量，
     * 这道题则要求选取的数字的和恰好等于整个数组的元素和的一半。类似于传统的「0−1 背包问题」，可以使用动态规划求解。
     * <p>
     * 创建二维数组 dp，包含 n 行 target+1 列，其中 dp[i][j] 表示从数组的 [0,i] 下标范围内选取若干个正整数（可以是 0 个），
     * 是否存在一种选取方案使得被选取的正整数的和等于 j。初始时，dp 中的全部元素都是 false。
     * <p>
     * 在定义状态之后，需要考虑边界情况。以下两种情况都属于边界情况。
     * 如果不选取任何正整数，则被选取的正整数之和等于 0。因此对于所有 0≤i<n，都有 dp[i][0]=true。
     * 当 i==0 时，只有一个正整数 nums[0] 可以被选取，因此 dp[0][nums[0]]=true。
     * <p>
     * 对于 i>0 且 j>0 的情况，如何确定 dp[i][j] 的值？需要分别考虑以下两种情况。
     * 如果 j≥nums[i]，则对于当前的数字 nums[i]，可以选取也可以不选取，两种情况只要有一个为 true，就有 dp[i][j]=true。
     * 如果不选取 nums[i]，则 dp[i][j]=dp[i−1][j]；
     * 如果选取 nums[i]，则 dp[i][j]=dp[i−1][j−nums[i]]。
     * 如果 j<nums[i]，则在选取的数字的和等于 j 的情况下无法选取当前的数字 nums[i]，因此有 dp[i][j]=dp[i−1][j]。
     *
     * @param nums
     * @return
     */
    public static boolean canPartition(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return false;
        }
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if ((sum & 1) == 1) {
            return false;
        }
        int target = sum / 2;
        // 创建0-1背包
        boolean[][] dp =  new boolean[n][target + 1];
        // 初始化第一行
        if(nums[0] <= target) {
            dp[0][nums[0]] = true;
        }
        // lable：简化1
        dp[0][0] = true;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= target; j++) {
                // 先将上一行的值拿过来
                dp[i][j] = dp[i - 1][j];
                //// 判断当前值是否能取到
                //if (j == nums[i]) {
                //    dp[i][j] = true;
                //    continue;
                //}
                //if(j > nums[i]){
                //    dp[i][j] = dp[i - 1][j - nums[i]] ||  dp[i - 1][j];
                //}
                // lable：简化1
                if(nums[i] <= j){
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i]];
                }
            }
            // 特殊情况处理
            // 1、只要有一个可以取到则证明都可以取到
            if (dp[i][target]) {
                return true;
            }

        }


        return dp[n-1][target];
    }


    /**
     * 上述代码的空间复杂度是 O(n×target)。但是可以发现在计算 dp 的过程中，每一行的 dp 值都只与上一行的 dp 值有关，
     * 因此只需要一个一维数组即可将空间复杂度降到 O(target)。此时的转移方程为：
     * dp[j]=dp[j]∣dp[j−nums[i]]
     * 且需要注意的是第二层的循环我们需要从大到小计算，因为如果我们从小到大更新 dp 值，那么在计算 dp[j] 值的时候，
     * dp[j−nums[i]] 已经是被更新过的状态，不再是上一行的 dp 值
     */
    public boolean canPartition1(int[] nums) {
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
        // 用于后面做j-num时如果两者相等的判断
        dp[0] = true;
        for (int i = 0; i < n; i++) {
            int num = nums[i];
            for (int j = target; j >= num; --j) {
                dp[j] |= dp[j - num];
            }
            if (dp[target]) {
                return true;
            }
        }
        return dp[target];
    }
}
