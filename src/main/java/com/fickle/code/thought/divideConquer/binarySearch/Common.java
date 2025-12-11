package com.fickle.code.thought.divideConquer.binarySearch;

/**
 * 正常实现
 *
 * @author Administrator
 * @apiNote com.fickle.code.thought.divideConquer.binarySearch
 */
public class Common {
    /**
     * 通用实现
     */
    public int binarySearch(int[] nums, int key) {
        int l = 0, h = nums.length - 1;
        while (l <= h) {
            // m = (l + h) / 2
            // m = l + (h - l) / 2
            // l + h 可能出现加法溢出，所以使用第二种方式。
            int m = l + (h - l) / 2;
            if (nums[m] == key) {
                return m;
            } else if (nums[m] > key) {
                h = m - 1;
            } else {
                l = m + 1;
            }
        }
        return -1;
    }

    /**
     * 该实现和正常实现有以下不同:
     * 循环条件为 l < h
     * h 的赋值表达式为 h = m
     * 最后返回 l 而不是 -1
     * 在 nums[m] >= key 的情况下，可以推导出最左 key 位于 [l, m] 区间中，这是一个闭区间。
     * h 的赋值表达式为 h = m，因为 m 位置也可能是解。
     */
    public int binarySearch1(int[] nums, int key) {
        int l = 0, h = nums.length - 1;
        // 在 h 的赋值表达式为 h = mid 的情况下，如果循环条件为 l <= h，那么会出现循环无法退出的情况，因此循环条件只能是 l < h。
        while (l < h) {
            int m = l + (h - l) / 2;
            if (nums[m] >= key) {
                h = m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }
}
