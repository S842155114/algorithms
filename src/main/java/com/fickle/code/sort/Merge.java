package com.fickle.code.sort;

/**
 * 将两个的有序数列合并成一个有序数列，我们称之为"归并"。归并排序(Merge Sort)就是利用归并思想对数列进行排序。
 * 根据具体的实现，归并排序包括"从上往下"和"从下往上"2种方式
 * <p>
 * 从下往上的归并排序:将待排序的数列分成若干个长度为1的子数列，然后将这些数列两两合并；得到若干个长度为2的有序数列，再将这些数列两两合并；
 * 得到若干个长度为4的有序数列，再将它们两两合并；直接合并成一个数列为止。
 * <p>
 * 从上往下的归并排序:它与"从下往上"在排序上是反方向的。它基本包括3步:
 * 分解 -- 将当前区间一分为二，即求分裂点 mid = (low + high)/2;
 * 求解 -- 递归地对两个子区间a[low...mid] 和 a[mid+1...high]进行归并排序。递归的终结条件是子区间长度为1。
 * 合并 -- 将已排序的两个子区间a[low...mid]和 a[mid+1...high]归并为一个有序的区间a[low...high]。
 * <p>
 * 归并排序的时间复杂度是O(N*lgN)。假设被排序的数列中有N个数。遍历一趟的时间复杂度是O(N)，需要遍历多少次呢?
 * 归并排序的形式就是一棵二叉树，它需要遍历的次数就是二叉树的深度，而根据完全二叉树的可以得出它的时间复杂度是O(N*lgN)
 * <p>
 * 归并排序是稳定的算法，它满足稳定算法的定义
 *
 * @author Administrator
 * @apiNote com.fickle.code.sort
 */
public class Merge {

    /*
     * 将一个数组中的两个相邻有序区间合并成一个 - 插入排序
     *
     * 参数说明:
     *     a -- 包含两个有序区间的数组
     *     start -- 第1个有序区间的起始地址。
     *     mid   -- 第1个有序区间的结束地址。也是第2个有序区间的起始地址。
     *     end   -- 第2个有序区间的结束地址。
     */
    public static void merge(int[] a, int start, int mid, int end) {
        // 将数组的两个部分，根据mid分割开，然后双指针进行赋值
        int i,c = start;
        int j = mid + 1;
        // 插入排序,mid前有序，mid后也有序 [2,5,7] [4,6,8]
        while (j <= end) {
            for(i = c; i < j; i++) {
                if (a[i] > a[j]){
                    c = i + 1;// 记录i的位置，由于j有序，下次只需从i + 1开始
                    int temp = a[j];
                    int k = i;
                    while (k < j){ // 插入，i - j的平移
                        a[j] = a[k];
                        a[k] = temp;
                        temp = a[j];
                        k++;
                    }
                    break;
                }
            }
            j++;
        }

    }

    /*
     * 将一个数组中的两个相邻有序区间合并成一个 - 比较排序
     *
     * 参数说明:
     *     a -- 包含两个有序区间的数组
     *     start -- 第1个有序区间的起始地址。
     *     mid   -- 第1个有序区间的结束地址。也是第2个有序区间的起始地址。
     *     end   -- 第2个有序区间的结束地址。
     */
    public static void merge1(int[] a, int start, int mid, int end) {
        int[] tmp = new int[end-start+1];    // tmp是汇总2个有序区的临时区域
        int i = start;            // 第1个有序区的索引
        int j = mid + 1;        // 第2个有序区的索引
        int k = 0;                // 临时区域的索引

        while(i <= mid && j <= end) {
            if (a[i] <= a[j])
                tmp[k++] = a[i++];
            else
                tmp[k++] = a[j++];
        }

        while(i <= mid)
            tmp[k++] = a[i++];

        while(j <= end)
            tmp[k++] = a[j++];

        // 将排序后的元素，全部都整合到数组a中。
        for (i = 0; i < k; i++)
            a[start + i] = tmp[i];

    }

    /*
     * 归并排序(从上往下)
     *
     * 参数说明:
     *     a -- 待排序的数组
     *     start -- 数组的起始地址
     *     endi -- 数组的结束地址
     */
    public static void mergeSortUp2Down(int[] a, int start, int end) {
        if(a == null || start >= end) {
            return;
        }

        int mid =  (start + end) / 2;

        mergeSortUp2Down(a, start, mid);
        mergeSortUp2Down(a, mid + 1, end);

        merge(a, start, mid, end);
    }

    /*
     * 对数组a做若干次合并: 数组a的总长度为len，将它分为若干个长度为gap的子数组；
     *             将"每2个相邻的子数组" 进行合并排序。
     *
     * 参数说明:
     *     a -- 待排序的数组
     *     len -- 数组的长度
     *     gap -- 子数组的长度
     */
    public static void mergeGroups(int[] a, int len, int gap) {
        int i;
        for(i = 0; i + 2 * gap < len; i += 2 * gap) {
            merge(a, i, i + gap - 1, i + 2 * gap - 1);
        }
        // 若 i+gap < len，则剩余一个子数组没有配对。
        // 将该子数组合并到已排序的数组中。
        if ( i+gap < len)
            merge(a, i, i + gap - 1, len - 1);
    }

    /*
     * 归并排序(从下往上)
     *
     * 参数说明:
     *     a -- 待排序的数组
     */
    public static void mergeSortDown2Up(int[] a) {
        if (a == null || a.length < 2) {
            return;
        }

        for (int gap = 1; gap < a.length; gap = gap * 2) {
            mergeGroups(a,a.length, gap);
        }
    }
}

