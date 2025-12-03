package com.fickle.code.sort;

/**
 * 最简单和最基本的排序
 * 它是一种较简单的排序算法。它会遍历若干次要排序的数列，每次遍历时，它都会从前往后依次的比较相邻两个数的大小；如果前者比后者大，则交换它们的位置。
 * 这样，一次遍历之后，最大的元素就在数列的末尾！ 采用相同的方法再次遍历时，第二大的元素就被排列在最大元素之前。重复此操作，直到整个数列都有序为止！
 * <p>
 * 冒泡排序的时间复杂度是O(N2)。 假设被排序的数列中有N个数。遍历一趟的时间复杂度是O(N)，需要遍历多少次呢? N-1次！因此，冒泡排序的时间复杂度是O(N2)
 * <p>
 * 冒泡排序是稳定的算法，它满足稳定算法的定义。 算法稳定性 -- 假设在数列中存在a[i]=a[j]，若在排序之前，a[i]在a[j]前面；并且排序之后，
 * a[i]仍然在a[j]前面。则这个排序算法是稳定的！
 *
 * @author Administrator
 * @apiNote com.fickle.code.sort
 */
public class Bubble {

    /*
     * 冒泡排序
     *
     * 参数说明:
     *     a -- 待排序的数组 [5,3,6,7,4]
     *     n -- 数组的长度
     */
    public static void bubbleSort1(int[] a, int n) {
        // i = 5,4,3,2,1
        for (int i = n - 1; i > 0; i--) { // 只控制循环
            for (int j = 0; j < i; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
        for (int i : a) {
            System.out.println(i);
        }
    }

    /*
     * 冒泡排序(改进版) ，加了一个判断标识，可以提前结束
     *
     * 参数说明:
     *     a -- 待排序的数组
     *     n -- 数组的长度
     */
    public static void bubbleSort2(int[] a, int n) {
        // i = 5,4,3,2,1
        int flag = 0;
        for (int i = n - 1; i > 0; i--) { // 只控制循环
            for (int j = 0; j < i; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;

                    flag = 1;
                }
            }
            if(flag == 0){ // 没有发生交换
                break;
            }
        }
        for (int i : a) {
            System.out.println(i);
        }
    }

}
