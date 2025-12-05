package com.fickle.code.thought.dynamic.fibonacci;

/**
 * 爬楼梯
 * <p>
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 *
 * @author Administrator
 * @apiNote com.fickle.code.thought.dynamic
 */
public class L70 {

    /**
     * 在到达第 n 阶之前，只可能处于两个位置：
     * 在第 n-1 阶，然后跨 1 阶上来。
     * 在第 n-2 阶，然后跨 2 阶上来。
     *
     * 我们用 f(x) 表示爬到第 x 级台阶的方案数，考虑最后一步可能跨了一级台阶，也可能跨了两级台阶
     * 状态转移方程：f(x)=f(x−1)+f(x−2)
     * 意味着爬到第 x 级台阶的方案数是爬到第 x−1 级台阶的方案数和爬到第 x−2 级台阶的方案数的和。
     * 很好理解，因为每次只能爬 1 级或 2 级，所以 f(x) 只能从 f(x−1) 和 f(x−2) 转移过来，而这里要统计方案总数，我们就需要对这两项的贡献求和。
     *
     * 我们是从第 0 级开始爬的，所以从第 0 级爬到第 0 级我们可以看作只有一种方案，
     * 即 f(0)=1；从第 0 级到第 1 级也只有一种方案，即爬一级，f(1)=1。这两个作为边界条件就可以继续向后推导出第 n 级的正确结果。
     *
     *
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        if (n <= 1){
            return 1;
        }
        int p = 0, q = 0, c = 1;
        for (int i = 0; i < n; ++i) {
            p = q;
            q = c;
            c = p + q;
        }
        return c;
    }

}
