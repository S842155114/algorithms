package com.fickle.code.thought.dynamic.fibonacci;

/**
 * 打家劫舍 II
 * <p>
 * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，这意味着第一个房屋和最后一个房屋是紧挨着的。
 * 同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，今晚能够偷窃到的最高金额。
 * <p>
 * 输入：nums = [1,2,3,1]
 * 输出：4
 * 解释：你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
 * 偷窃到的最高金额 = 1 + 3 = 4 。
 *
 * @author Administrator
 * @apiNote com.fickle.code.thought.dynamic
 */
public class L213 {
    /**
     * 因为房子连成环状，所以出现了一个新的约束，那就是第一个房子和最后一间房子不能同时偷
     * 也就是说，偷了第一间就不能偷最后一间
     *         偷了最后一间就不能偷第一间
     * 需要计算nums[0] + [2,n-2]和[1,n-1]的最大值
     *
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        int n = nums.length;
        return Math.max(nums[0] + rob1(nums, 2, n - 1), rob1(nums, 1, n));
    }

    private int rob1(int[] nums, int start, int end) { // [start,end) 左闭右开
        int f0 = 0, f1 = 0;
        for (int i = start; i < end; ++i) {
            int newF = Math.max(f1, f0 + nums[i]);
            f0 = f1;
            f1 = newF;
        }
        return f1;
    }

    public static void main(String[] args) {
        L213 l = new L213();
        System.out.println(l.rob(new int[]{2, 1, 1,2}));
    }
}
