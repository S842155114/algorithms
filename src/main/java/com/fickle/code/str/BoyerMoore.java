package com.fickle.code.str;

/**
 * Boyer-Moore字符串搜索算法实现
 * <p>
 * Boyer-Moore算法是一种高效的字符串搜索算法，其主要优势在于：
 * 1. 从右向左进行模式匹配，可以实现较大的跳跃距离
 * 2. 使用坏字符规则和好后缀规则来决定模式串向右移动的距离
 * 3. 时间复杂度最好情况下为O(n/m)，最坏情况下为O(n*m)
 *
 * @author Administrator
 * @apiNote com.fickle.code.str
 */
public class BoyerMoore {

    // ASCII字符集大小，用于坏字符规则表的大小
    private static final int ASCII_SIZE = 256;

    /**
     * 预处理坏字符规则
     * 构建坏字符表，记录模式串中每个字符最后出现的位置
     *
     * @param pattern 搜索模式串
     * @param badChar 坏字符表数组，大小为ASCII_SIZE
     */
    private static void preprocessBadChar(String pattern, int[] badChar) {
        // 初始化所有字符的位置为-1，表示这些字符在模式串中未出现
        for (int i = 0; i < ASCII_SIZE; i++) {
            badChar[i] = -1;
        }
        // 遍历模式串，记录每个字符最后出现的位置
        for (int i = 0; i < pattern.length(); i++) {
            // 将字符pattern.charAt(i)在模式串中的位置设置为i
            badChar[pattern.charAt(i)] = i;
        }
    }

    /**
     * 预处理好后缀规则，生成好后缀移动表。
     * 这个方法会填充传入的 bmGs 数组。
     *
     * @param pattern 模式串
     * @param bmGs    好后缀移动表，bmGs[j] 表示当在模式串位置 j 发生不匹配时，模式串应该移动的距离
     */
    private static void preprocessGoodSuffix(String pattern, int[] bmGs) {
        int m = pattern.length();
        int[] suffix = new int[m];

        // --- 步骤 1: 计算临时 suffix 数组 ---
        // suffix[i] 表示模式串中子串 P[0...i] 的最长后缀的长度，该后缀同时也是 P 的一个前缀。

        // suffix[i] 是模式串中子串 P[0...i] 的最长后缀的长度，该后缀同时也是整个模式串 P 的一个后缀。
        // 举例: P = "BACABAC"。计算 suffix[4]。 子串 P[0...4] 是 "BACAB"。
        // "BACAB" 的后缀有: "B", "AB", "CAB", "ACAB", "BACAB"。
        // 整个 P 的后缀有: "C", "AC", "BAC", "ABAC", "CABAC", "ACABAC", "BACABAC"。
        // 没有共同的后缀，所以 suffix[4] = 0。
        suffix[m - 1] = m;
        // g (guard): 可以看作是当前 已知匹配区域 的左边界。
        // f (front): 当前正在处理的 子串 P[0...i] 的起始位置。 由于是反向匹配，所以这里说的起始位置是反向匹配的起始位置
        int g = m - 1;
        int f = 0;

        for (int i = m - 2; i >= 0; i--) {
            // g: 在上一次计算中，匹配失败的位置。所以，我们上次找到的匹配后缀是 P[g+1 ... f]
            // 如果当前要计算的 i 在我们已知匹配区域（g 到 f）之间，因为上一次f为上一轮的匹配子串的开始匹配位置，
            // 而g如果匹配成功会左移，形成了(g,f]区间，(g, f] 这个区间就代表了我们“已知的匹配信息”，
            // 如果此时i进入了此区间，证明  上一轮g已经经过了当前i的位置，也就是说当前i的位置已经计算过了

            // m - 1 + i - f 是一个非常巧妙的索引映射。我们称之为 i'。suffix[i']一定为已经计算过的值。
            // P[0...i'] 这个子串的长度 i' + 1正好等于 P[0...i] 中“暴露”在 P[0...f] 内部的部分的长度。这个长度就是 i - g + 1
            // i' + 1 = (m - 1 + i - f) + 1 = m + i - f
            // i - g + 1 = i - (f - (f-g)) + 1 = i - f + (f-g) + 1
            // 这就是 suffix[i']。我们称之为 L_mirror。它表示子串 P[0...i'] 的最长后缀匹配长度。

            // i - g == 当前准备开始匹配的字符位置 - 上一次匹配失败的边界，
            // i - g 的值代表了在 P[0...i]这个子串中，我们“已知”与 P 的后缀相匹配的部分的长度 即 P[g+1...i]的长度。我们称之为 L_known
            // 这个条件是整个优化的精髓。它是在检查一个“更小范围”内的匹配情况
            // 此处suffix[m - 1 + i - f] < i - g则意味着：
            // suffix[i] 不会比 f-g更长

            // 整合条件 L_mirror < L_known
            // 这个条件的含义是：那个“镜像”子串的匹配长度，比我们“已知”的匹配长度要短。
            // 如果 L_mirror < L_known，这就提供了一个强有力的保证：P[0...i] 的最长后缀匹配长度 suffix[i]，不可能比 L_known (即 i-g) 更长。
            // 因为 L_known 是我们亲眼所见、在 P[0...f] 的计算中已经验证过的匹配长度。
            // 如果连它的一个“代理” i' 的匹配长度都比它短，那么 P[0...i] 的匹配就必然被 g 这个边界“截断”了，无法延伸到更远的地方。
            if (i > g && suffix[m - 1 + i - f] < i - g) {
                // 优化：如果 i 在 []，可以利用之前计算的结果，避免重复比较
                suffix[i] = suffix[m - 1 + i - f];
            } else {
                // 回溯：需要重新计算匹配前缀
                // 如果 i 跑到了已知区域 g 的左边，说明之前的匹配信息作废了，需要把 g 移动到 i。
                if (i < g) {
                    g = i;
                }
                // 将f置为从右向左开始匹配的起始位置
                f = i;
                // pattern.charAt(g): 从 P[0...i] 的 g 位置向左比较。
                // pattern.charAt(g + m - 1 - f): 从整个 P 的后缀相应位置向左比较。
                // 这个循环就是在寻找 P[0...i] 的后缀与 P 的后缀的最长匹配。
                while (g >= 0 && pattern.charAt(g) == pattern.charAt(m - 1 + g - f)) {
                    g--;
                }
                // 计算出匹配的长度。f 是起始位置，g 是匹配失败的前一个位置，所以 f-g 就是匹配的字符数。
                suffix[i] = f - g;
            }
        }

        // --- 步骤 2: 计算 bmGs 表 ---
        // 情况1: 好后缀的子串是模式串的前缀
        // 首先，将所有移动距离初始化为模式串长度 m
        // 初始化。默认情况下，任何不匹配都移动整个模式串的长度 m。这是最安全但可能不是最大的移动。
        for (int i = 0; i < m; i++) {
            bmGs[i] = m;
        }

        int j = 0;
        // 处理“好后缀的子串是模式前缀”的情况。
        //if (suffix[i] == i + 1): 这个条件判断 P[0...i] 是否是整个模式串 P 的一个前缀。（因为它的最长匹配后缀长度是它自身的长度 i+1，意味着 P[0...i] 同时是 P 的后缀，这只有在 P 以 P[0...i] 开头时才成立）。
        //for (; j < m - 1 - i; j++) { ... }: 如果 P[0...i] 是一个前缀，那么对于那些不匹配位置 j（0 <= j < m-1-i），我们可以移动 m-1-i 的距离，让这个前缀与文本中的好后缀对齐。
        for (int i = m - 1; i >= 0; i--) {
            if (suffix[i] == i + 1) {
                // 如果 P[0...i] 是一个前缀
                for (; j < m - 1 - i; j++) {
                    if (bmGs[j] == m) {
                        bmGs[j] = m - 1 - i;
                    }
                }
            }
        }

        // 情况2: 好后缀在模式串的其他位置出现
        // 处理“好后缀在模式串其他位置出现”的情况。
        //bmGs[m - 1 - suffix[i]] = m - 1 - i;: 这行代码是精髓。
        //假设在 j 位置失配，那么好后缀是 P[j+1...m-1]。
        //我们在模式串中寻找另一个 P[j+1...m-1]。
        //这个循环就是在做这件事。suffix[i] 代表了 P[0...i] 的后缀与 P 的后缀的匹配长度。
        //m - 1 - suffix[i] 计算出的就是失配位置 j。
        //m - 1 - i 计算出的就是需要移动的距离。
        //这个循环会覆盖掉默认值 m，给出更精确（通常更小）的移动距离。
        for (int i = 0; i < m - 1; i++) {
            bmGs[m - 1 - suffix[i]] = m - 1 - i;
        }
    }

    /**
     * BM字符串匹配算法实现 (正确版本)
     * 结合坏字符规则和好后缀规则进行高效字符串搜索
     *
     * @param text    文本串（主串）
     * @param pattern 模式串（要查找的字符串）
     * @return 第一次匹配的起始位置，如果没有匹配则返回-1
     */
    public static int search(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();

        // 边界情况处理
        if (m == 0) return 0;
        if (n < m) return -1;

        // 初始化坏字符表和好后缀移动表
        int[] badChar = new int[ASCII_SIZE];
        int[] bmGs = new int[m]; // 好后缀移动表

        // 预处理
        preprocessBadChar(pattern, badChar);
        preprocessGoodSuffix(pattern, bmGs);

        // 模式串在文本串中的起始位置
        int i = 0;

        // 当模式串还能在文本串中完整匹配时继续搜索
        while (i <= n - m) {
            // 从模式串的末尾开始比较
            int j = m - 1;

            // 从右向左比较模式串和文本串中的字符
            while (j >= 0 && pattern.charAt(j) == text.charAt(i + j)) {
                j--;
            }

            // 如果j<0，说明找到了一个完整匹配
            if (j < 0) {
                return i;
            } else {
                // 发生不匹配，计算移动距离

                // 1. 计算坏字符规则下的移动距离
                int badCharShift = j - badChar[text.charAt(i + j)];

                // 2. 从表中获取好后缀规则下的移动距离
                int goodSuffixShift = bmGs[j];

                // 选择两者中的较大移动距离
                i += Math.max(badCharShift, goodSuffixShift);
            }
        }
        // 未找到匹配项
        return -1;
    }

    /**
     * 主函数，用于测试BM算法
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        System.out.println("=== Boyer-Moore算法测试 (修正版) ===\n");

        // 测试用例1: 基本匹配
        System.out.println("测试用例1: 基本匹配");
        String text1 = "ABABDABACDABABCABAB";
        String pattern1 = "ABABCABAB";
        int index1 = search(text1, pattern1);
        System.out.println("文本串: " + text1);
        System.out.println("模式串: " + pattern1);
        System.out.println("匹配位置: " + index1 + " (期望: 10)");
        System.out.println();

        // 测试用例2: 多个匹配
        System.out.println("测试用例2: 多个匹配");
        String text2 = "ABCABCABC";
        String pattern2 = "ABC";
        int index2 = search(text2, pattern2);
        System.out.println("文本串: " + text2);
        System.out.println("模式串: " + pattern2);
        System.out.println("首次匹配位置: " + index2 + " (期望: 0)");
        System.out.println();

        // 测试用例3: 无匹配
        System.out.println("测试用例3: 无匹配");
        String text3 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String pattern3 = "XYZ";
        int index3 = search(text3, pattern3);
        System.out.println("文本串: " + text3);
        System.out.println("模式串: " + pattern3);
        System.out.println("匹配位置: " + index3 + " (期望: 23)");
        System.out.println();

        // 测试用例4: 坏字符规则触发
        System.out.println("测试用例4: 坏字符规则触发");
        String text4 = "EXAMPLESTRING";
        String pattern4 = "STRING";
        int index4 = search(text4, pattern4);
        System.out.println("文本串: " + text4);
        System.out.println("模式串: " + pattern4);
        System.out.println("匹配位置: " + index4 + " (期望: 7)");
        System.out.println();

        // 测试用例5: 好后缀规则触发
        System.out.println("测试用例5: 好后缀规则触发");
        String text5 = "ABCCBABABCBABABC";
        String pattern5 = "ABABC";
        int index5 = search(text5, pattern5);
        System.out.println("文本串: " + text5);
        System.out.println("模式串: " + pattern5);
        System.out.println("匹配位置: " + index5 + " (期望: 5)");
        System.out.println();

        // 测试用例6: 重复字符模式
        System.out.println("测试用例6: 重复字符模式");
        String text6 = "AAAAAAABAA";
        String pattern6 = "AAAAB";
        int index6 = search(text6, pattern6);
        System.out.println("文本串: " + text6);
        System.out.println("模式串: " + pattern6);
        System.out.println("匹配位置: " + index6 + " (期望: 3)");
        System.out.println();

        // 测试用例7: 模式串比文本串长
        System.out.println("测试用例7: 模式串比文本串长");
        String text7 = "SHORT";
        String pattern7 = "LONGPATTERN";
        int index7 = search(text7, pattern7);
        System.out.println("文本串: " + text7);
        System.out.println("模式串: " + pattern7);
        System.out.println("匹配位置: " + index7 + " (期望: -1)");
        System.out.println();

        // 测试用例8: 展示前缀匹配的好后缀情况
        System.out.println("测试用例8: 展示前缀匹配的好后缀情况");
        String text8 = "ABCABCABCABCA";
        String pattern8 = "ABCABCA";
        int index8 = search(text8, pattern8);
        System.out.println("文本串: " + text8);
        System.out.println("模式串: " + pattern8);
        System.out.println("说明: 对于模式串\"ABCABCA\"，前缀\"ABC\"与后缀\"ABC\"相同，会触发好后缀规则。");
        System.out.println("匹配位置: " + index8 + " (期望: 0)");
        System.out.println();

        System.out.println("=== 测试完成 ===");
    }
}
