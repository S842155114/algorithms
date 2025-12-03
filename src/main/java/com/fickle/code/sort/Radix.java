package com.fickle.code.sort;

/**
 * 基数排序(Radix Sort)是桶排序的扩展
 * 将整数按位数切割成不同的数字，然后按每个位数分别比较。
 * 具体做法是: 将所有待比较数值统一为同样的数位长度，数位较短的数前面补零。
 * 然后，从最低位开始，依次进行一次排序。这样从最低位排序一直到最高位排序完成以后, 数列就变成一个有序序列。
 *
 * @author Administrator
 * @apiNote com.fickle.code.sort
 */
public class Radix {

    /*
     * 获取数组a中最大值
     *
     * 参数说明:
     *     a -- 数组
     *     n -- 数组长度
     */
    private static int getMax(int[] a) {
        int max;

        max = a[0];
        for (int i = 1; i < a.length; i++)
            if (a[i] > max)
                max = a[i];

        return max;
    }

    /*
     * 对数组按照"某个位数"进行排序(桶排序)
     *
     * 参数说明:
     *     a -- 数组
     *     exp -- 指数。对数组a按照该指数进行排序。
     *
     * 例如，对于数组a={50, 3, 542, 745, 2014, 154, 63, 616}；
     *    (01) 当exp=1表示按照"个位"对数组a进行排序
     *    (02) 当exp=10表示按照"十位"对数组a进行排序
     *    (03) 当exp=100表示按照"百位"对数组a进行排序
     *    ...
     */
    private static void countSort(int[] a, int exp) {
        int[] count = new int[10];
        int[] output = new int[a.length];
        for (int i = 0; i < a.length; i++) { // 桶排序
            count[(a[i] / exp) % 10]++;
        }

        // 计算每个值在count中的索引
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        // 通过索引碰撞法来有序输出数组
        // 由于碰撞后索引需要做-1操作，所以逆向迭代数组，以保证数组的稳定性
        for (int i = a.length - 1; i >= 0; i--) {
            output[count[(a[i] / exp) % 10] - 1] = a[i];
            count[(a[i] / exp) % 10]--;
        }

        for (int i = 0; i < a.length; i++) {
            a[i] = output[i];
        }

        //
        //int[] output = new int[a.length];    // 存储"被排序数据"的临时数组
        //int[] buckets = new int[10];
        //
        //// 将数据出现的次数存储在buckets[]中
        //for (int i = 0; i < a.length; i++)
        //    buckets[(a[i] / exp) % 10]++;
        //
        //// 更改buckets[i]。目的是让更改后的buckets[i]的值，是该数据在output[]中的位置。
        //for (int i = 1; i < 10; i++)
        //    buckets[i] += buckets[i - 1];
        //
        //// 将数据存储到临时数组output[]中
        //for (int i = a.length - 1; i >= 0; i--) {
        //    output[buckets[(a[i] / exp) % 10] - 1] = a[i];
        //    buckets[(a[i] / exp) % 10]--;
        //}
        //
        //// 将排序好的数据赋值给a[]
        //for (int i = 0; i < a.length; i++)
        //    a[i] = output[i];

        //output = null;
        //buckets = null;
    }

    /*
     * 基数排序
     *
     * 参数说明:
     *     a -- 数组
     */
    public static void radixSort(int[] a) {
        int exp;    // 指数。当对数组按各位进行排序时，exp=1；按十位进行排序时，exp=10；...
        int max = getMax(a);    // 数组a中的最大值

        // 从个位开始，对数组a按"指数"进行排序
        for (exp = 1; max / exp > 0; exp *= 10)
            countSort(a, exp);
    }
}
