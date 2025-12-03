package com.fickle.code.thought.greedy;

import java.util.Arrays;

/**
 * @author Administrator
 * @apiNote com.fickle.code.greedy
 */
public class L455 {
    public static void main(String[] args) {
        int[] a = {1,2};
        int[] b = {1,2,3};
        System.out.println(findContentChildren(a,b));
    }

    //public static int findContentChildren(int[] g, int[] s) {
    //    int count = 0;
    //    // 先对两个数组进行排序 0-1
    //    Arrays.sort(g);
    //    Arrays.sort(s);
    //    // 遍历g获取s，第一个拿到的直接continue
    //    for(int i=0;i<g.length;i++){
    //        int index = i;
    //        for (int j=index;j<s.length;j++){
    //            if (g[i] <= s[j]) {
    //                index = j + 1;
    //                count++;
    //                break;
    //            }
    //        }
    //    }
    //
    //    return count;
    //}

    //作者：力扣官方题解
    //链接：https://leetcode.cn/problems/assign-cookies/solutions/534281/fen-fa-bing-gan-by-leetcode-solution-50se/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    public static int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int m = g.length, n = s.length;
        int count = 0;
        //    g           s
        // m是孩子个数，n是饼干数
        //    i           j
        for (int i = 0, j = 0; i < m && j < n; i++, j++) {
            // 先找到满足当前孩子的饼干位置
            while (j < n && g[i] > s[j]) {
                j++;
            }
            if (j < n) {
                count++;
            }
        }
        return count;
    }
}
