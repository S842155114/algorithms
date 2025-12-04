package com.fickle.code.thought.dynamic;

/**
 * 打家劫舍
 * <p>
 * 你是一个专业的小偷，计划偷窃沿街的房屋。
 * 每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
 * <p>
 * 输入：[2,7,9,3,1]
 * 输出：12
 * 解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
 * 偷窃到的最高金额 = 2 + 9 + 1 = 12 。
 *
 * @author Administrator
 * @apiNote com.fickle.code.thought.dynamic
 */
public class L198 {
    /**
     * 由于不可连续，所以dp[n] = Max(dp[n-1], dp[n-2] + num[n])
     *
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int p = 0;
        int q = 0;
        int c = 0;
        for (int num : nums) {
            p = q;
            q = c;
            c = Math.max(p + num, q);
        }
        return c;
    }

    public static void main(String[] args) {
        L198 l = new L198();
        System.out.println(l.rob(new int[]{2, 7, 9, 3, 1}));
    }
}
