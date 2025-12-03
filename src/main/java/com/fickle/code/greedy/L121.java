package com.fickle.code.greedy;

/**
 * @author Administrator
 * @apiNote com.fickle.code.greedy
 */
public class L121 {
    public static void main(String[] args) {
        int[] a = {7,1,5,3,6,4};
        System.out.println(maxProfit1(a));
    }

    /**
     * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
     * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
     * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
     *
     * 示例 1：
     * 输入：[7,1,5,3,6,4]
     * 输出：5
     * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     *      注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
     * 示例 2：
     * 输入：prices = [7,6,4,3,1]
     * 输出：0
     * 解释：在这种情况下, 没有交易完成, 所以最大利润为 0。
     *
     * [7,5,6,3,5,2]
     * @param prices
     * @return
     */
    public static int maxProfit1(int[] prices) {
        if (prices == null || prices.length < 2) return 0;
        int result = 0;
        // 双指针，分别从i和i+1开始
        // 第一次low是0，fast变成0以后最大的数的index
        // 然后low一直后移，一直到等于fast
        // 这时，fast再指向后面最大的index，重复
        // 直到fast是最后一个index
        int low = 0,fast = 0;
        while(low == fast){
            fast = low + 1;
            if(fast == prices.length){
                break;
            }
            for (int i=fast;i<prices.length;i++){
                if(prices[i] >= prices[fast]){
                    fast = i;
                }
            }
            while (low < fast){
                result = Math.max(result, prices[fast] - prices[low]);
                low++;
            }
        }


        // 第二次low是fast前最小的数的index，fast不变
        // 第三次low = fast，


        return result;
    }

    public int maxProfit(int[] prices) {
        // 记录历史最低价
        int minprice = Integer.MAX_VALUE;
        // 记录最大收益
        int maxprofit = 0;
        for (int price : prices) {
            // 更新历史最低
            if (price < minprice) {
                minprice = price;
                // 更新最大收益
            } else if (price - minprice > maxprofit) {
                maxprofit = price - minprice;
            }
        }
        return maxprofit;
    }
}
