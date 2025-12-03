package com.fickle.code.sort;

/**
 * 将数组分到有限数量的桶子里。每个桶子再个别排序（有可能再使用别的排序算法或是以递归方式继续使用桶排序进行排序）
 * 假设待排序的数组a中共有N个整数，并且已知数组a中数据的范围[0, MAX)。在桶排序时，创建容量为MAX的桶数组r，并将桶数组元素都初始化为0；
 * 将容量为MAX的桶数组中的每一个单元都看作一个"桶"。在排序时，逐个遍历数组a，将数组a的值，作为"桶数组r"的下标。当a中数据被读取时，就将桶的值加1。
 * 例如，读取到数组a[3]=5，则将r[5]的值+1。
 * <p>
 * 平均时间复杂度: O(n + k)
 * 最佳时间复杂度: O(n + k)
 * 最差时间复杂度: O(n ^ 2)
 * 空间复杂度: O(n * k)
 * 桶排序最好情况下使用线性时间O(n)，桶排序的时间复杂度，取决与对各个桶之间数据进行排序的时间复杂度，因为其它部分的时间复杂度都为O(n)。
 * 很显然，桶划分的越小，各个桶之间的数据越少，排序所用的时间也会越少。但相应的空间消耗就会增大。
 * <p>
 * 稳定
 *
 * @author Administrator
 * @apiNote com.fickle.code.sort
 */
public class Bucket {

    /*
     * 桶排序
     *
     * 参数说明:
     *     a -- 待排序数组
     *     max -- 数组a中最大值的范围
     */
    public static void bucketSort(int[] a, int max) {
        if (a == null || max < 1)
            return;
        // 根据组数中的最大值构造一个大桶
        int[] bucket = new int[max + 1];
        for (int j : a) {
            bucket[j]++;
        }
        // 桶里已经有数据了，遍历桶
        for (int i = 0, j = 0; i <= max; i++) {
            while (bucket[i]-- > 0) {
                a[j++] = i;
            }
        }

    }

}
