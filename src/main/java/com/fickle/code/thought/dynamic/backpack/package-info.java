/**
 * 0-1 背包问题
 * <p>
 * 有一个容量为 N 的背包，要用这个背包装下物品的价值最大，这些物品有两个属性: 体积 w 和价值 v。
 * 定义一个二维数组 dp 存储最大价值，其中 dp[i][j] 表示前 i 件物品体积不超过 j 的情况下能达到的最大价值。
 * <p>
 * 设第 i 件物品体积为 w，价值为 v，根据第 i 件物品是否添加到背包中，可以分两种情况讨论:
 * 第 i 件物品没添加到背包，总体积不超过 j 的前 i 件物品的最大价值就是总体积不超过 j 的前 i-1 件物品的最大价值，dp[i][j] = dp[i-1][j]。
 * 第 i 件物品添加到背包中，在这种前提下，默认j是装满的最大体积，所以如果i要入包，需要i-1预留j的体积，dp[i][j] = dp[i-1][j-w] + v。
 * 第 i 件物品可添加也可以不添加，取决于哪种情况下最大价值更大。
 * <p>
 * 因此，0-1 背包的状态转移方程为:
 * dp[i][j]=max(dp[i-1][j],dp[i-1][j-w] + v)
 *
 * @author Administrator
 * @apiNote com.fickle.code.thought.dynamic.backpack
 */
package com.fickle.code.thought.dynamic.backpack;