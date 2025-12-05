package com.fickle.code.str;

/**
 * 朴素算法（Naive)(暴力破解)
 * 朴素的字符串匹配算法又称为暴力匹配算法（Brute Force Algorithm），最为简单的字符串匹配算法。
 *
 * @author Administrator
 * @apiNote com.fickle.code.str
 */
public class Native {

    /**
     * 朴素算法
     * @param s
     * @param p
     * @return
     */
    public boolean isMatch(String s, String p) {
        // s是主串，p是模式串
        int sLen = s.length(), pLen = p.length();

        // 空模式串匹配任何字符串
        if (pLen == 0) return true;

        // 主串长度小于模式串长度，无法匹配
        if (sLen < pLen) return false;

        // 遍历主串的每个可能起始位置
        for (int i = 0; i <= sLen - pLen; i++) {
            int j;
            // 检查从位置i开始是否能完全匹配模式串
            for (j = 0; j < pLen; j++) {
                if (s.charAt(i + j) != p.charAt(j)) {
                    break;
                }
            }
            // 如果模式串全部匹配完，则找到匹配
            if (j == pLen) {
                return true; // 或者返回i，表示匹配开始位置
            }
        }
        return false;
    }

    /**
     * 暴力破解
     * @param s
     * @param p
     * @return
     */
    public boolean isMatch1(String s, String p) {
        int sLen = s.length(), pLen = p.length();
        int i = 0;
        int j = 0;
        while (i < sLen && j < pLen) {
            // 相等则继续匹配
            if (s.charAt(i) == p.charAt(j)) {
                i++;
                j++;
            } else {
                // 不相等时需要重新匹配，i退回到开始匹配的下一个字符开始
                i = i - j + 1;
                // j从头开始
                j = 0;
            }
        }
        return j == pLen;
    }
}
