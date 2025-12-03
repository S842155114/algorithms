package com.fickle.code.leetcode;

/**
 * 给定一个二叉搜索树的根节点 root ，和一个整数 k ，请你设计一个算法查找其中第 k 小的元素（从 1 开始计数）。
 *
 * 进阶：如果二叉搜索树经常被修改（插入/删除操作）并且你需要频繁地查找第 k 小的值，你将如何优化算法？
 *
 * @author Administrator
 * @apiNote com.fickle.code.leetcode
 */
public class L230 {
    /**
     * 中序遍历拿第k个值
     * @param root
     * @param k
     * @return
     */
    public static int kthSmallest(TreeNode root, int k) {
        int[] a = new int[2];
        midOrder(root,a,k);
        return a[1];
    }

    private static void midOrder(TreeNode root, int[] a, int target) {
        if (root == null || a[0] == target) return;
        midOrder(root.left,a,target);
        a[0] = a[0] + 1;
        if (a[0] == target){
            a[1] = root.val;
        }
        midOrder(root.right,a,target);

    }

    /**
     * 递归方式
     * @param root
     * @param k
     * @return
     */
    public int kthSmallest1(TreeNode root, int k) {
        int leftCnt = count(root.left);
        if (leftCnt == k - 1) return root.val;
        if (leftCnt > k - 1) return kthSmallest1(root.left, k);
        return kthSmallest1(root.right, k - leftCnt - 1);
    }

    private int count(TreeNode node) {
        if (node == null) return 0;
        return 1 + count(node.left) + count(node.right);
    }

    public static void main(String[] args) {
        Integer[] arr = new Integer[]{5,3,6,2,4,null,null,1};
        TreeNode node = new TreeNode();
        node.initTree(arr);
        System.out.println(kthSmallest(node, 3));
    }

}
