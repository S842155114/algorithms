package com.fickle.code.sort;

/**
 * 调用所有排序算法
 *
 * @author Administrator
 * @apiNote com.fickle.code.sort
 */
public class Main {
    public static void main(String[] args) {
        int[] a = {83, 32, 66, 47, 22, 18, 53, 75};
        int i = a.length;
        //Bubble.bubbleSort2(a,i);
        //Quick.quickSort(a,0,i-1);
        //Insertion.insertSort(a, i);
        //Shell.shellSort1(a, i);
        //Selection.selectSort(a, i);
        //Heap.heapSortAsc(a, i);
        //Merge.mergeSortUp2Down(a, 0, i - 1);
        //Merge.mergeSortDown2Up(a);
        //Bucket.bucketSort(a, 83);
        Radix.radixSort(a);

        for (int i1 : a) {
            System.out.println(i1);
        }
    }
}
