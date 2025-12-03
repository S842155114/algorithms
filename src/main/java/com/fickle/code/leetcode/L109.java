package com.fickle.code.leetcode;

/**
 * 给定一个单链表的头节点  head ，其中的元素 按升序排序 ，将其转换为 平衡 二叉搜索树。
 * @author Administrator
 * @apiNote com.fickle.code.leetcode
 */
public class L109 {
    public static TreeNode sortedListToBST(ListNode head) {
        return buildTree(head,null);
    }

    private static TreeNode buildTree(ListNode left,ListNode right){
        if (left == right) {
            return null;
        }
        ListNode mid = getMid(left,right);

        TreeNode root = new TreeNode(mid.val);
        root.left = buildTree(left,mid);
        root.right = buildTree(mid.next,right);

        return root;
    }

    private static ListNode getMid(ListNode left,ListNode right){
        ListNode slow = left;
        ListNode fast = left;

        while(fast != right && fast.next != right){
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;

    }


    public static void main(String[] args) {
        int[] arr = new int[]{-10,-3,0,5,9};
        ListNode node = new ListNode();
        ListNode temp = node;
        for (int i : arr) {
            temp.next = new ListNode(i);
            temp = temp.next;
        }
        System.out.println(sortedListToBST(node.next));
    }
}
