package com.fickle.code.leetcode;

import java.util.Stack;

/**
 * 给定一个整数数组 temperatures ，表示每天的温度，返回一个数组 answer ，其中 answer[i] 是指对于第 i 天，
 * 下一个更高温度出现在几天后。如果气温在这之后都不会升高，请在该位置用 0 来代替。
 * @author Administrator
 * @apiNote com.fickle.code.leetcode
 */
public class L739 {
    /**
     * 这个时间复杂度太高了，没有想到好的办法
     * @param temperatures
     * @return
     */
    public static int[] dailyTemperatures(int[] temperatures) {
        int[] result = new int[temperatures.length];
        int index = 0;
        for (int i = 0; i < temperatures.length; i++) {
            int temp = 0;
            if (index !=0 && temperatures[i] >= temperatures[i-1]) {
                temp = index;
            }
            for (int j = Math.max(temp,i + 1); j < temperatures.length; j++) {
                if (temperatures[j] > temperatures[i]) {
                    result[i] = j - i;
                    index = j;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 用栈的方式实现
     * 用数组定义了一个栈及其栈指针
     * 通过将数据的索引压栈，然后找到大于栈顶元素的方式从栈中取出索引，并为该索引的位置设置值
     *
     * 这种场景下，压栈的数据是从大到小的，直到找到大于栈顶最小值的数据，然后依次进行比较
     * @param temperatures
     * @return
     */
    public static int[] dailyTemperatures1(int[] temperatures) {
        int n = temperatures.length;
        int[] ans = new int[n];
        int[] stack = new int[n]; // 数组模拟栈，效率更高
        int top = -1; // 栈顶指针
        for (int i = 0; i < n; i++) {
            int t = temperatures[i];
            while (top >= 0 && t > temperatures[stack[top]]) { // 栈不为空并且当前值大于栈顶数据
                int j = stack[top--]; // 数据出栈
                ans[j] = i - j; // 栈顶的索引位置赋值
            }
            stack[++top] = i; // 当前索引入栈
        }
        return ans;
    }

    /**
     * 与上述相同，只不过用的Stack对象
     * @param temperatures
     * @return
     */
    public int[] dailyTemperatures3(int[] temperatures) {
        int n = temperatures.length;
        int[] dist = new int[n];
        Stack<Integer> indexs = new Stack<>();
        for (int curIndex = 0; curIndex < n; curIndex++) {
            while (!indexs.isEmpty() && temperatures[curIndex] > temperatures[indexs.peek()]) {
                int preIndex = indexs.pop();
                dist[preIndex] = curIndex - preIndex;
            }
            indexs.add(curIndex);
        }
        return dist;
    }

    public static void main(String[] args) {
        int[] a = new int[]{73,74,75,71,69,72,76,73};
        for ( int i : dailyTemperatures1(a)){
            System.out.println(i);
        }
    }
}
