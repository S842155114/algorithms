package com.fickle.code.leetcode;

import java.util.Arrays;

/**
 * 给定一个循环数组 nums （ nums[nums.length - 1] 的下一个元素是 nums[0] ），返回 nums 中每个元素的 下一个更大元素 。
 * <p>
 * 数字 x 的 下一个更大的元素 是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1 。
 * <a href="https://leetcode.cn/problems/next-greater-element-ii/description/">...</a>
 *
 * @author Administrator
 * @apiNote com.fickle.code.leetcode
 */
public class L503 {
    public static int[] nextGreaterElements(int[] nums) {
        int[] output = new int[nums.length];
        Arrays.fill(output, -1);
        int[] stack = new int[nums.length * 2];
        int top = -1;
        for (int i = 0; i < nums.length * 2 - 1 ; i++) {
            int temp = nums[i % nums.length];
            while (top > -1 && temp > nums[stack[top]]) {
                int index = stack[top--];
                output[index] = temp;
            }
            stack[++top] = i % nums.length;
        }
        return output;
    }

    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3, 4, 3};
        for (int i : nextGreaterElements(a)) {
            System.out.println(i);
        }
    }
}
