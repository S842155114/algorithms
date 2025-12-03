package com.fickle.code.thought.divideConquer;

import com.fickle.code.leetcode.ListNode;

import java.util.Arrays;

/**
 * 排序链表
 * 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
 * <p>
 * 输入：head = [-1,5,3,4,0]
 * 输出：[-1,0,3,4,5]
 * <p>
 * 你可以在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序吗？
 *
 * @author Administrator
 * @apiNote com.fickle.code.thought.divideConquer
 */
public class L148 {

    /**
     * 用一个辅助数组进行排序，然后迭代链表赋值
     * O(n log n) 时间复杂度
     * O(n) 空间复杂度
     *
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        int len = 0;
        ListNode cur = head;
        while (cur != null) {
            len++;
            cur = cur.next;
        }
        int[] assistArr = new int[len];
        cur = head;
        int index = 0;
        while (cur != null) {
            assistArr[index++] = cur.val;
            cur = cur.next;
        }
        // assistArr进行快排
        Arrays.sort(assistArr);
        cur = head;
        index = 0;
        while (cur != null) {
            cur.val = assistArr[index++];
            cur = cur.next;
        }
        return head;
    }

    /**
     * leetcode中时间消耗最小的解法
     * 用桶排序，如果数值范围大且比较稀疏，空间消耗大
     * @param head
     * @return
     */
    public ListNode sortList1(ListNode head) {
        // 初始化 min 为整数最大值，max 为整数最小值（方便后续更新）
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        //遍历指针，初始指向表头
        ListNode pre = head;
        // 第一次遍历：找链表中所有节点值的 min 和 max
        while(pre != null){
            if(pre.val > max){
                max = pre.val;
            }
            if(pre.val < min){
                min = pre.val;
            }
            pre = pre.next;
        }
        // 计算数值范围：range = 最大值 - 最小值 + 1（包含 min 和 max 本身）
        int range = max - min + 1;
        // 计数数组，初始值全为 0
        int[] count = new int[range];
        // 重置指针，重新遍历链表
        pre = head;
        // 第二次遍历：统计每个数值的出现次数
        while(pre != null){
            count[pre.val - min]++;
            pre = pre.next;
        }
        // 重置指针，指向链表头
        pre = head;
        // 遍历计数数组：从索引 0 到 range-1（对应数值从 min 到 max）
        for(int i = 0; i < range; i++){
            // 当 count[i] > 0 时，说明数值（i+min）需要出现 count[i] 次
            while(count[i] != 0){
                count[i]--;
                pre.val = i + min;
                pre = pre.next;
            }
        }
        return head;
    }

    /**
     * leetcode中空间消耗最小的解法
     * 采用了双指针法，分别进行左排和右排
     * @param head
     * @return
     */
    public ListNode sortList2(ListNode head) {
        // merge sort
        if (head == null || head.next == null) {
            return head;
        }
        // 双指针获取中间值
        ListNode middle = findMiddle(head);
        ListNode right = sortList2(middle.next);
        middle.next = null;
        ListNode left = sortList2(head);
        return merge(left, right);
    }

    private ListNode findMiddle(ListNode head) {
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    private ListNode merge(ListNode head1, ListNode head2) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (head1 != null && head2 != null) {
            if (head1.val < head2.val) {
                tail.next = head1;
                head1 = head1.next;
            } else {
                tail.next = head2;
                head2 = head2.next;
            }
            tail = tail.next;
        }
        if (head1 != null) {
            tail.next = head1;
        } else {
            tail.next = head2;
        }

        return dummy.next;
    }
}
