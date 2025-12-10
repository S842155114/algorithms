package com.fickle.code.thought.dynamic.array;

/**
 * 区域和检索 - 数组不可变
 * 给定一个整数数组  nums，处理以下类型的多个查询:
 * 计算索引 left 和 right （包含 left 和 right）之间的 nums 元素的 和 ，其中 left <= right
 * 实现 NumArray 类：
 * NumArray(int[] nums) 使用数组 nums 初始化对象
 * int sumRange(int i, int j) 返回数组 nums 中索引 left 和 right 之间的元素的 总和 ，包含 left 和 right 两点（也就是 nums[left] + nums[left + 1] + ... + nums[right] )
 * <p>
 * 示例 1：
 * 输入：
 * ["NumArray", "sumRange", "sumRange", "sumRange"]
 * [[[-2, 0, 3, -5, 2, -1]], [0, 2], [2, 5], [0, 5]]
 * 输出：
 * [null, 1, -1, -3]
 *
 * @author Administrator
 * @apiNote com.fickle.code.thought.dynamic.array
 */
public class L303 {

    int[] sums;

    /**
     * 先对数组进行预处理
     * <p>
     * 求出数组中每一个元素的前缀和
     * f(i) = num[0] + .... + num[i-1] = f(i - 1) + num[i - 1]
     *
     * @param nums
     */
    public L303(int[] nums) {
        int n = nums.length;
        sums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + nums[i];
        }
    }

    /**
     * i和j之间的数据和 = f(j+1) - f(i)
     *
     * @param i
     * @param j
     * @return
     */
    public int sumRange(int i, int j) {
        return sums[j + 1] - sums[i];
    }
}
