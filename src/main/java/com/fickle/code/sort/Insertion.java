package com.fickle.code.sort;

/**
 * 直接插入排序(Straight Insertion Sort)的基本思想是: 把n个待排序的元素看成为一个有序表和一个无序表。开始时有序表中只包含1个元素，
 * 无序表中包含有n-1个元素，排序过程中每次从无序表中取出第一个元素，将它插入到有序表中的适当位置，使之成为新的有序表，重复n-1次可完成排序过程。
 *
 * 直接插入排序的时间复杂度是O(N2)。
 * 假设被排序的数列中有N个数。遍历一趟的时间复杂度是O(N)，需要遍历多少次呢? N-1！因此，直接插入排序的时间复杂度是O(N2)。
 *
 * 直接插入排序是稳定的算法，它满足稳定算法的定义。
 * 算法稳定性 -- 假设在数列中存在a[i]=a[j]，若在排序之前，a[i]在a[j]前面；并且排序之后，a[i]仍然在a[j]前面。则这个排序算法是稳定的！
 *
 * @author Administrator
 * @apiNote com.fickle.code.sort
 */
public class Insertion {
    /*
     * 直接插入排序
     *
     * 参数说明:
     *     a -- 待排序的数组  {30,40,60,10,20,50}
     *     n -- 数组的长度
     */
    public static void insertSort(int[] a, int n) {
        if (n <= 1) {
            return;
        }
        int current = 1; // 分界指针，前面是有序，后面是无序
        for (int i = 1; i < n; i++) { //外层是无序
            int j = 0;
            while (current < n && j < current) {
                if (a[i] < a[j]) {
                    // a[i]插入到a[j]前面，平移后面所有有序节点
                    int prev = a[i];
                    int t = j;
                    while (i - t > 0) {
                        int temp = a[t];
                        a[t] = prev;
                        prev = temp;
                        t++;
                    }
                    a[i] = prev;
                    break;
                }
                j++;
            }
            current++;
        }
    }
}
