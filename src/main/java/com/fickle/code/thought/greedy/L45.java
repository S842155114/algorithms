package com.fickle.code.thought.greedy;

/**
 * @author Administrator
 * @apiNote com.fickle.code.greedy
 */
public class L45 {
    public static void main(String[] args) {
        jump(new int[]{2,3,1,1,4});
    }

    /**
     * 给定一个长度为 n 的 0 索引整数数组 nums。初始位置在下标 0。
     * 每个元素 nums[i] 表示从索引 i 向后跳转的最大长度。换句话说，如果你在索引 i 处，你可以跳转到任意 (i + j) 处：
     * 0 <= j <= nums[i] 且
     * i + j < n
     * 返回到达 n - 1 的最小跳跃次数。测试用例保证可以到达 n - 1。
     *
     * 示例 1:
     * 输入: nums = [2,3,1,1,4]
     * 输出: 2
     * 解释: 跳到最后一个位置的最小跳跃数是 2。
     *      从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
     *
     * 示例 2:
     * 输入: nums = [2,3,0,1,4]
     * 输出: 2
     * @param nums
     * @return
     *
     * 正向查找，每次找到可到达的最远位置
     */
    public static int jump(int[] nums) {
        int length = nums.length;
        int end = 0;
        int maxPosition = 0;
        int steps = 0;
        for (int i = 0; i < length - 1; i++) {// 在i到end的范围内查找最大值
            maxPosition = Math.max(maxPosition, i + nums[i]);
            if (i == end) {
                end = maxPosition;
                steps++;
            }
        }
        return steps;
    }
}
