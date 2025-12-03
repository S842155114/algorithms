package com.fickle.code.leetcode;

/**
 * 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
 * <p>
 * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
 * <p>
 * 例如，给定如下二叉搜索树:  root = [6,2,8,0,4,7,9,null,null,3,5]
 *
 * @author Administrator
 * @apiNote com.fickle.code.leetcode
 */
public class L235 {

    /**
     * p<= 目标节点 >=q
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        // 先确定q <= p
        if (p.val < q.val) {
            TreeNode temp = p;
            p = q;
            q = temp;
        } else if (p.val == q.val) {
            return p;
        }
        TreeNode result = root;
        while (result.val > p.val || result.val < q.val) {
            if (result.val > p.val) {
                result = result.left;
            } else {
                result = result.right;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Integer[] arr = new Integer[]{6, 2, 8, 0, 4, 7, 9, null, null, 3, 5};
        TreeNode node = new TreeNode();
        node.initTree(arr);
    }
}
