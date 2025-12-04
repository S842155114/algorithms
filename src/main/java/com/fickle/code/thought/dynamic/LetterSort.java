package com.fickle.code.thought.dynamic;

/**
 * 信件错排
 * <p>
 * 题目描述: 有 N 个 信 和 信封，它们被打乱，求错误装信方式的数量。
 *
 * @author Administrator
 * @apiNote com.fickle.code.thought.dynamic
 */
public class LetterSort {

    /**
     * 要求每一封信都装错
     * 当n = 1时 result = 0
     * n = 2 result = 1
     * n = 3 result = 2
     * n = 4 result = 9
     * <p>
     * 加入将最终结果看作D(n)
     * 当将信n放入信封时，由于不应该放入正确的信封，所以有n-1个信封可以放
     * 假如信放入了信封k：那此时信k可放的信封有两种情况：1、k放入n的信封，那么剩下n-2个信封和信，则方法有D(n-2)；
     * 2、k不放入信封n，则剩下包括k在内的n-1个信封和信，则方法有D(n-1)
     * 所以，状态转移方程为：D(n) = (n-1)(D(n-1) + D(n-2))
     *
     * @param n
     * @return
     */
    public int solute(int n) {
        if (n == 0 ||  n == 1)
            return 0;
        if (n == 2)
            return 1;

        int[] memo = new int[n + 1];
        memo[0] = 0;
        memo[1] = 0;
        memo[2] = 1;
        return getMax(n, memo);
    }

    private int getMax(int n, int[] memo) {
        if (memo[n] != 0) {
            return memo[n];
        } else {
            memo[n] = (n - 1) * (getMax(n - 1, memo) + getMax(n - 2, memo));
        }

        return memo[n];
    }

}
