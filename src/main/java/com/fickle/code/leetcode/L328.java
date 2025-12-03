package com.fickle.code.leetcode;

/**
 * 给定单链表的头节点 head ，将所有索引为奇数的节点和索引为偶数的节点分别分组，保持它们原有的相对顺序，然后把偶数索引节点分组连接到奇数索引节点分组之后，返回重新排序的链表。
 * 第一个节点的索引被认为是 奇数 ， 第二个节点的索引为 偶数 ，以此类推。
 * 请注意，偶数组和奇数组内部的相对顺序应该与输入时保持一致。
 * 你必须在 O(1) 的额外空间复杂度和 O(n) 的时间复杂度下解决这个问题。
 * <a href="https://leetcode.cn/problems/odd-even-linked-list/description/">...</a>
 *
 * @author Administrator
 * @apiNote com.fickle.code.leetcode
 */
public class L328 {
    /**
     * 直接双指针遍历
     *
     * @param head
     * @return
     */
    public static ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // 定义两个节点，一个是奇数一个是偶数
        ListNode odd = head;
        ListNode even = head.next;
        ListNode oddHeader = odd;
        ListNode evenHeader = even;
        head = head.next.next;

        while (head != null && head.next != null) { // 迭代获取
            odd.next = head;
            even.next = head.next;
            odd = odd.next;
            even = even.next;
            head = head.next.next;
        }
        if (head != null) {
            odd.next = head;
            odd = odd.next;
        }
        even.next = null;

        odd.next = evenHeader;

        return oddHeader;
    }

    /**
     * 空间复杂度低，而且更简洁
     *
     * @param head
     * @return
     */
    public ListNode oddEvenList1(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode evenHead = head.next;
        ListNode odd = head, even = evenHead;
        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }
}
