package com.fickle.code.str;

import java.util.*;

/**
 * Java中的后缀树实现
 *
 * 后缀树是一种压缩字典树，包含给定文本的所有后缀。
 * 它支持快速的字符串操作，如子串搜索、查找重复子串等。
 */
public class SuffixTree {

    /**
     * 节点类，表示后缀树中的一个节点
     */
    private static class Node {
        // 子节点映射，键为字符，值为对应的子节点
        Map<Character, Node> children;
        // 后缀链接，用于Ukkonen算法优化
        String suffixLink;
        // 边的起始索引
        int start;
        // 边的结束索引
        int end;
        // 后缀索引，仅对叶节点有效
        int suffixIndex;

        /**
         * 构造函数
         * @param start 边的起始位置
         * @param end 边的结束位置
         */
        Node(int start, int end) {
            this.children = new HashMap<>();
            this.suffixLink = null;
            this.start = start;
            this.end = end;
            this.suffixIndex = -1;
        }
    }

    // 输入文本
    private String text;
    // 根节点
    private Node root;
    // 上一个新建的节点，用于设置后缀链接
    private Node lastNewNode;
    // 当前活跃节点计数
    private int activeNodeCount;

    /**
     * 构造函数，初始化后缀树
     * @param text 输入文本
     */
    public SuffixTree(String text) {
        // 添加终止符$以区分不同的后缀
        this.text = text + "$";
        buildSuffixTree();
    }

    /**
     * 构建后缀树
     */
    private void buildSuffixTree() {
        // 初始化根节点
        root = new Node(-1, -1);
        activeNodeCount = 0;

        // 遍历文本中的每个字符，逐步构建后缀树
        for (int i = 0; i < text.length(); i++) {
            extendSuffixTree(i);
        }
    }

    /**
     * 扩展后缀树以包含新字符
     * @param pos 当前字符在文本中的位置
     */
    private void extendSuffixTree(int pos) {
        // 设置lastNewNode为null，表示没有新创建的内部节点需要设置后缀链接
        lastNewNode = null;

        // 遍历所有已经添加的后缀，确保它们都指向当前字符
        activeNodeCount++;

        // 初始化活动点（active point）
        Node activeNode = root;
        int activeEdge = -1;
        int activeLength = 0;

        // 遍历当前所有的活动后缀
        for (int j = 0; j < activeNodeCount; j++) {
            // 获取活动边的第一个字符
            char activeChar = text.charAt(pos);

            // 检查活动边是否存在于活动节点的子节点中
            if (!activeNode.children.containsKey(activeChar)) {
                // 如果不存在，则创建一个新的叶节点
                Node leaf = new Node(pos, text.length() - 1);
                activeNode.children.put(activeChar, leaf);

                // 如果在上一步中创建了一个新节点，那么为其设置后缀链接
                if (lastNewNode != null) {
                    lastNewNode.suffixLink = activeNode.toString();
                }
                lastNewNode = activeNode;
            } else {
                // 如果存在，检查边上的字符是否与当前字符匹配
                Node next = activeNode.children.get(activeChar);
                int edgeLen = Math.min(next.end, pos) - next.start + 1;

                // 检查边上的字符是否与当前字符匹配
                if (text.charAt(next.start + activeLength) == text.charAt(pos)) {
                    // 如果匹配，设置后缀链接（如果需要）
                    if (lastNewNode != null && activeNode != root) {
                        lastNewNode.suffixLink = activeNode.toString();
                    }
                    break; // 不需要继续遍历，因为字符已匹配
                }

                // 如果不匹配，在边上进行分割
                Node split = new Node(next.start, next.start + activeLength - 1);
                activeNode.children.put(activeChar, split);

                // 将下一个节点作为分裂节点的子节点
                split.children.put(text.charAt(next.start + activeLength), next);

                // 调整下一个节点的起始位置
                next.start += activeLength;

                // 创建一个新的叶节点
                Node leaf = new Node(pos, text.length() - 1);
                split.children.put(text.charAt(pos), leaf);

                // 如果在上一步中创建了一个新节点，那么为其设置后缀链接
                if (lastNewNode != null) {
                    lastNewNode.suffixLink = split.toString();
                }
                lastNewNode = split;
            }

            // 如果活动节点不是根，通过后缀链接移动到下一个节点
            // 否则，减少活动边的长度
            activeNodeCount--;
        }
    }

    /**
     * 搜索给定的模式是否存在于后缀树中
     * @param pattern 要搜索的模式
     * @return 如果找到返回true，否则返回false
     */
    public boolean searchPattern(String pattern) {
        // 从根节点开始搜索
        Node currentNode = root;
        int i = 0;

        // 遍历模式中的每个字符
        while (i < pattern.length()) {
            char c = pattern.charAt(i);

            // 如果当前节点没有对应字符的子节点，则模式不存在
            if (!currentNode.children.containsKey(c)) {
                return false;
            }

            // 获取对应的子节点
            Node child = currentNode.children.get(c);
            int j = child.start;

            // 比较模式字符和边上的字符
            while (i < pattern.length() && j <= child.end &&
                   pattern.charAt(i) == text.charAt(j)) {
                i++;
                j++;
            }

            // 如果模式的所有字符都匹配，则找到了模式
            if (i == pattern.length()) {
                return true;
            }

            // 如果边上的字符没有完全匹配，则模式不存在
            if (j > child.end) {
                currentNode = child;
            } else {
                return false;
            }
        }

        return false;
    }

    /**
     * 打印后缀树的结构
     */
    public void printTree() {
        System.out.println("后缀树结构:");
        printNode(root, "", 0);
    }

    /**
     * 递归打印节点
     * @param node 当前节点
     * @param indent 缩进字符串
     * @param depth 当前深度
     */
    private void printNode(Node node, String indent, int depth) {
        // 如果不是根节点，打印节点信息
        if (node.start != -1) {
            System.out.println(indent + "'--'" + text.substring(node.start, Math.min(node.end + 1, text.length())));
        }

        // 递归打印所有子节点
        for (Map.Entry<Character, Node> entry : node.children.entrySet()) {
            printNode(entry.getValue(), indent + "  ", depth + 1);
        }
    }

    /**
     * 获取文本的所有后缀
     * @return 包含所有后缀的列表
     */
    public List<String> getAllSuffixes() {
        List<String> suffixes = new ArrayList<>();
        collectSuffixes(root, "", suffixes);
        return suffixes;
    }

    /**
     * 收集所有后缀
     * @param node 当前节点
     * @param prefix 当前前缀
     * @param suffixes 存储后缀的列表
     */
    private void collectSuffixes(Node node, String prefix, List<String> suffixes) {
        // 如果不是根节点，将当前边的文本添加到前缀中
        if (node.start != -1) {
            prefix += text.substring(node.start, Math.min(node.end + 1, text.length()));
        }

        // 如果是叶节点，添加后缀（去除终止符$）
        if (node.children.isEmpty()) {
            if (prefix.endsWith("$")) {
                suffixes.add(prefix.substring(0, prefix.length() - 1));
            }
        } else {
            // 递归收集所有子节点的后缀
            for (Node child : node.children.values()) {
                collectSuffixes(child, prefix, suffixes);
            }
        }
    }

    /**
     * 测试方法
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        String text = "abcabxabcd";
        SuffixTree suffixTree = new SuffixTree(text);

        System.out.println("文本: " + text);
        suffixTree.printTree();

        // 测试搜索
        System.out.println("\n搜索测试:");
        System.out.println("搜索 'abc': " + suffixTree.searchPattern("abc"));
        System.out.println("搜索 'bcd': " + suffixTree.searchPattern("bcd"));
        System.out.println("搜索 'xyz': " + suffixTree.searchPattern("xyz"));

        // 显示所有后缀
        System.out.println("\n所有后缀:");
        List<String> suffixes = suffixTree.getAllSuffixes();
        for (String suffix : suffixes) {
            System.out.println(suffix);
        }
    }
}