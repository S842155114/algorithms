package com.fickle.code.thought.greedy;

/**
 * @author Administrator
 * @apiNote com.fickle.code.greedy
 */
public class L55 {
    public static void main(String[] args) {
        System.out.println(canJump(new int[]{3,2,1,0,4}));
    }

    /**
     * 给你一个非负整数数组 nums ，你最初位于数组的 第一个下标 。数组中的每个元素代表你在该位置可以跳跃的最大长度。
     * 判断你是否能够到达最后一个下标，如果可以，返回 true ；否则，返回 false 。
     *
     * 示例 1：
     * 输入：nums = [2,3,1,1,4]
     * 输出：true
     * 解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标。
     *
     * 示例 2：
     * 输入：nums = [3,2,1,0,4]
     * 输出：false
     * 解释：无论怎样，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0 ， 所以永远不可能到达最后一个下标。
     * @param nums
     * @return
     */
    public static boolean canJump(int[] nums) {
        if (nums == null || nums.length < 2) return true;
        int endIndex = nums.length - 1;
        // 反向遍历数组，判断可以跳到最终节点的有效节点
        for (int i = endIndex - 1; i > -1; i--) {
            if (nums[i] + i >= endIndex) {
                endIndex = i;
            }
        }
        return endIndex == 0;
    }
}
