package com.fickle.code.leetcode;

/**
 * 给你一个头结点为 head 的单链表和一个整数 k ，请你设计一个算法将链表分隔为 k 个连续的部分。
 * 每部分的长度应该尽可能的相等：任意两部分的长度差距不能超过 1 。这可能会导致有些部分为 null 。
 * 这 k 个部分应该按照在链表中出现的顺序排列，并且排在前面的部分的长度应该大于或等于排在后面的长度。
 * 返回一个由上述 k 部分组成的数组。
 * <a href="https://leetcode.cn/problems/split-linked-list-in-parts/description">...</a>
 *
 * @author Administrator
 * @apiNote com.fickle.code.leetcode
 */
public class L725 {
    public ListNode[] splitListToParts(ListNode head, int k) {
        ListNode[] res = new ListNode[k];
        if (head == null || head.next == null) {
            for (int i = 0; i < k; i++) {
                res[i] = null;
            }
        }
        int max = 0;
        ListNode temp = head;
        while (temp != null) {
            max++;
            temp = temp.next;
        }

        int col = max / k; // 正常数组的长度
        int count = max % k; // 超出部分的个数，这些数组长度需要col + 1

        for (int i = 0; i < k; i++) {
            int len = col;
            if (count > 0) {
                len++;
                count--;
            }
            if (head == null) {
                res[i] = null;
                continue;
            }

            res[i] = new ListNode(head.val);
            head = head.next;
            temp = res[i];
            for (int j = 0; j < len - 1; j++) {
                temp.next = new ListNode(head.val);
                temp = temp.next;
                head = head.next;
            }

        }

        return res;
    }

    /**
     * 空间复杂度为O(1)的解法
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode[] splitListToParts1(ListNode head, int k) {
        int n = 0;
        ListNode temp = head;
        while (temp != null) {
            n++;
            temp = temp.next;
        }
        int quotient = n / k, remainder = n % k;

        ListNode[] parts = new ListNode[k];
        ListNode curr = head;
        for (int i = 0; i < k && curr != null; i++) {
            parts[i] = curr;
            int partSize = quotient + (i < remainder ? 1 : 0);
            for (int j = 1; j < partSize; j++) {
                curr = curr.next;
            }
            ListNode next = curr.next;
            curr.next = null;
            curr = next;
        }
        return parts;
    }

}
