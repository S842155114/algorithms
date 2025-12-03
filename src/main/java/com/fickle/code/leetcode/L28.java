package com.fickle.code.leetcode;

import java.util.*;

/**
 * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
 * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
 *
 * @author Administrator
 * @apiNote com.fickle.code.leetcode
 */
public class L28 {
    /**
     * 输入都是正数并且数据范围小时可用
     *
     * @param nums
     * @return
     */
    public static int longestConsecutive(int[] nums) {
        int len = nums.length;
        int max = 0;
        int maxLen = 0;
        for (int i = 0; i < len; i++) {
            max = Math.max(max, nums[i]);
        }
        int[] buckets = new int[max + 2];
        for (int i = 0; i < len; i++) {
            buckets[nums[i]]++;
        }
        int temp = 0; // 0 1 1 1 1 0 0 0 1 0 1 0 1 1 0
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] == 0) {
                if (temp == 0) {
                    continue;
                }
                maxLen = Math.max(maxLen, temp);
                temp = 0;
            } else {
                temp++;
            }
        }
        return maxLen;
    }

    /**
     * 输入有负数，并且数据范围比较大
     *
     * @param nums
     * @return
     */
    public static int longestConsecutive1(int[] nums) {
        // 先放入map
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], 0); // 不考虑重复数
        }
        int max = 0;
        // 遍历map，每一个数都向右取它的下一个值
        loop:
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() != 0) {
                continue;
            }
            // 构造一个栈，将当前数据向前所有的连续数字进行压栈
            Deque<Integer> stack = new LinkedList<>();
            Integer number = entry.getKey();
            while (map.containsKey(++number)) {
                stack.push(number);
                // 如果当前已有连续最大
                Integer val = map.get(number);
                if (val != 0) {
                    entry.setValue(val + stack.size());
                    stack.clear();
                    continue loop;
                }
            }
            // 设置当前数值向右的最大连续数
            entry.setValue(stack.size() + 1);
            while (!stack.isEmpty()) {
                Integer pop = stack.pop();
                // 设置当前数值向右的最大连续数
                map.put(pop, stack.size() + 1);
            }
        }
        for (Integer val :map.values()){
            max = Math.max(max, val);
        }


        return max;
    }

    /**
     * 判断当前值-1的值是否在集合中，如果存在则直接跳过，去找集合中存在的最小值，然后找其+1的最大连续
     * @param nums
     * @return
     */
    public int longestConsecutive2(int[] nums) {
        Set<Integer> num_set = new HashSet<Integer>();
        for (int num : nums) {
            num_set.add(num);
        }

        int longestStreak = 0;

        for (int num : num_set) {
            if (!num_set.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;

                while (num_set.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }

                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }

        return longestStreak;
    }


    public static void main(String[] args) {
        int[] a = {0, 3, 7, 2, 5, 8, 4, 6, 0, 1};
        System.out.println(longestConsecutive1(a));
        ;


    }
}
