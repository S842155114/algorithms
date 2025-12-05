package com.fickle.code.str;

/**
 * Knuth-Morris-Pratt算法（简称KMP）是最常用的字符串匹配算法之一
 *
 * 算法的主要思想是：先对匹配串进行预处理，得出next数组
 * 进行匹配时，根据next数组查询匹配串匹配失败时的，再次移动匹配串时可以忽略的长度
 *
 * S: bacbababaabcbab    P: ababaca
 *        ababaca
 *             ^ ① 匹配失败
 *          ababaca
 *          ^ ②
 *
 * ①匹配到这一位时，发现匹配失败了，这时候c的索引是5，是P的第6个字符
 * 那么c的前5个字符是完成匹配的，所以此时应该找 S与P匹配的这两段字符串的最长相同前后缀，作为下次P字串开始匹配的索引
 * 也就是 找S的ababa与P的ababa的最长公共前后缀 next[cIndex-1]
 *           a        a
 *           ab       ba
 *           aba      aba   √
 *           abab     baba
 * 此时找到这个aba，证明
 * 正常情况下，应该将P的头从S的开始配置位置向后移动1位继续匹配
 * 但是我们此时已经发现这部分有公共前后缀，相当于说，P可以跳过一部分已知的匹配
 *
 * 当 S[i] 和 P[j] 匹配失败时，我们不需要让 i 回溯
 * 我们已经知道 P[0...j-1] 和 S[i-j...i-1] 是匹配的。
 * next[j-1] 告诉我们 P[0...j-1] 这个子串自身有多长的前缀和后缀是相同的。
 * 这意味着我们可以将模式串 P 向右滑动，让 P 的这个前缀直接对齐 S 中已经匹配过的后缀，然后从 next[j-1] 的位置继续比较。
 *
 * 规则： 当 S[i] != P[j] 时，j 的新位置是 next[j-1]
 *
 * @author Administrator
 * @apiNote com.fickle.code.str
 */
public class KMP {

    public int match(String s, String p) {
        int sLen = s.length();
        int pLen = p.length();
        // p的预处理
        int[] next = generateNext(p);

        // 2. 开始匹配
        // i 指向主串S，j 指向模式串P
        for (int i = 0, j = 0; i < sLen; i++) {
            // 如果 s.charAt(i) != p.charAt(j)，说明需要移动模式串
            // j 需要回溯到 next[j-1] 的位置
            while (j > 0 && s.charAt(i) != p.charAt(j)) {
                j = next[j - 1];
            }
            // 如果当前字符匹配，则继续比较下一个字符
            if (s.charAt(i) == p.charAt(j)) {
                j++;
            }
            // 如果 j 到达了模式串的末尾，说明匹配成功
            if (j == pLen) {
                // 返回匹配的起始位置
                return i - j + 1;
            }
        }
        // 遍历完主串都没找到，返回-1

        return -1;
    }

    private int[] generateNext(String p) {
        int len = p.length();
        int[] next = new int[len];
        next[0] = 0;
        // 以i为界限切割p，将p[0...i]当作字串处理
        // i 指向子串的末尾，j 指向当前最长相等前后缀的长度
        // 我们实际上是在计算 P[0...i] 的next值，这个值依赖于 P[0...i-1] 的next值
        for (int i = 1, j = 0; i < len; i++) {
            // 当j已有匹配并且当前字符匹配失败时处理
            // 如果 P[i] != P[j]，说明需要回溯 j
            // 回溯到哪个位置呢？回溯到 P[0...j-1] 的最长相等前后缀的长度，即 next[j-1]
            // 这里借助了动态规划的思想，如果上一个字符匹配成功，则说明前j-1个字符存在于目标字符串中，
            // 当我再匹配串时，只需要从j-1个字串中的最长前后缀长度（next[j-1]）位置开始即可
            while (j > 0 && p.charAt(i) != p.charAt(j)) {
                // 回溯到前一个位置的最长相等前后缀长度
                // 即P[0...j-1]的最长相等前后缀长度
                j = next[j - 1];
            }
            // 匹配成功则j后移
            if (p.charAt(i) == p.charAt(j)) {
                j++;
            }

            // 赋值当前位置的最长相等前后缀的长度
            next[i] = j;
        }


        return  next;
    }

}
