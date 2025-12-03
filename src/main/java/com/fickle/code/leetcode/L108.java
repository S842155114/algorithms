package com.fickle.code.leetcode;

import jakarta.annotation.Nullable;

/**
 * 给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵 平衡 二叉搜索树。
 *
 * @author Administrator
 * @apiNote com.fickle.code.leetcode
 */
public class L108 {
    /**
     * nums为从小到大排列，也就是中序遍历
     * 题目要求是一颗平衡二叉搜索树，证明左右子树高度差不能超过1
     * 直接取中间节点作为根节点，递归取中间节点
     *
     * @param nums
     * @return
     */
    public static TreeNode sortedArrayToBST(int[] nums) {
        int len = nums.length;
        return buildTree(null, nums, 0, len);
    }

    /**
     *
     * @param root
     * @param nums
     * @param left 闭
     * @param right 开
     * @return
     */
    private static TreeNode buildTree(@Nullable TreeNode root, int[] nums, int left, int right){
        int mid = (left + right) / 2;
        if (left >= right) {
            return  null;
        }
        if (root == null){
            root = new TreeNode(nums[mid]);
        }
        root.left = buildTree(root.left,nums,left,mid);
        root.right = buildTree(root.right,nums,mid + 1,right);
        return root;
    }


    public static void main(String[] args) {
        int[] arr = new int[]{-10,-3,0,5,9};
        TreeNode treeNode = sortedArrayToBST(arr);
        System.out.println(treeNode.val);
    }
}
