package com.fickle.code.leetcode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 树节点结构
 *
 * @author Administrator
 * @apiNote com.fickle.code.leetcode
 */
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public void initTree(Integer[] arr) {
        Deque<TreeNode> seq = new LinkedList<>();
        this.val = arr[0];
        seq.add(this);
        int index = 1;
        Integer temp;
        while (!seq.isEmpty() && index < arr.length) {
            TreeNode node = seq.poll();
            if ((temp = arr[index++]) != null) {
                node.left = new TreeNode(temp);
                seq.add(node.left);
            }
            if (index < arr.length && (temp = arr[index++]) != null) {
                node.right = new TreeNode(temp);
                seq.add(node.right);
            }
        }
    }

    /**
     * 最右节点或中节点
     * @return 最大值
     */
    public TreeNode max(){
        TreeNode temp = this;
        while(temp.right != null){
            temp = temp.right;
        }
        return temp;
    }



}
