package com.fickle.code.sort;

/**
 * 它的基本思想是: 首先在未排序的数列中找到最小(or最大)元素，然后将其存放到数列的起始位置；接着，
 * 再从剩余未排序的元素中继续寻找最小(or最大)元素，然后放到已排序序列的末尾。以此类推，直到所有元素均排序完毕。
 *
 * 选择排序的时间复杂度是O(N2)。
 * 假设被排序的数列中有N个数。遍历一趟的时间复杂度是O(N)，需要遍历多少次呢? N-1！因此，选择排序的时间复杂度是O(N2)。
 *
 * 选择排序的稳定性是有一些争议的，不过一般提到排序算法，往往默认是数组实现，所以通常认为选择排序是不稳定的。
 * 用数组实现的选择排序是不稳定的，用链表实现的选择排序是稳定的。
 * 不过，一般提到排序算法时，大家往往会默认是数组实现，所以选择排序是不稳定的。
 *
 * @author Administrator
 * @apiNote com.fickle.code.sort
 */
public class Selection {
    /*
     * 选择排序
     *
     * 参数说明:
     *     a -- 待排序的数组 {80,30,60,40,20,10,50,70}
     *     n -- 数组的长度
     */
    public static void selectSort(int[] a, int n) {
        // 遍历数组，选择最值
        for (int i = 0; i < n; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (a[j] < a[minIndex]) {
                    minIndex = j;
                }
            }
            // 交换当前值和最小值
            int temp = a[i];
            a[i] = a[minIndex];
            a[minIndex] = temp;
        }
    }
}
