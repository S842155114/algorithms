package com.fickle.code.leetcode;

/**
 * 给你二叉搜索树的根节点 root ，同时给定最小边界low 和最大边界 high。通过修剪二叉搜索树，使得所有节点的值在[low, high]中。
 * 修剪树 应该改变保留在树中的元素的相对结构 (即，如果没有被移除，原有的父代子代关系都应当保留)。 可以证明，存在唯一的答案 。
 * <p>
 * 所以结果应当返回修剪好的二叉搜索树的新的根节点。注意，根节点可能会根据给定的边界发生改变。
 *
 * @author Administrator
 * @apiNote com.fickle.code.leetcode
 */
public class L669 {
    public static TreeNode trimBST(TreeNode root, int low, int high) {
        while (root != null && (root.val < low || root.val > high)) { // 节点不满足要求
            if (root.val < low) { // 节点值不满足左区间
                root = root.right; // 返回其右子节点
            } else { // 节点值不满足右区间
                root = root.left; // 返回其左子节点
            }
        }
        if (root == null) { // 不满足
            return null;
        }
        for (TreeNode node = root; node.left != null; ) { // 左子节点不为null
            if (node.left.val < low) { // 左子节点不满足左区间
                node.left = node.left.right;
            } else {
                node = node.left;
            }
        }
        for (TreeNode node = root; node.right != null; ) {
            if (node.right.val > high) { // 右子节点不满足区间
                node.right = node.right.left;
            } else {
                node = node.right;
            }
        }
        return root;
    }

    /**
     * 1、当前节点没有子，直接删除
     * 2、当前节点有一个子：删除后直接由子继承
     * 3、有两个子：获取后继节点，设置为当前节点的值，然后删除后继节点
     * @param root
     * @param val
     */
    private static void remove(TreeNode root, int val) {

    }

    public static void main(String[] args) {
        Integer[] arr = new Integer[]{3, 0, 4, null, 2, null, null, 1};
        TreeNode node = new TreeNode();
        node.initTree(arr);
        node = trimBST(node, 1, 3);
        System.out.println(node.val);
    }
}
