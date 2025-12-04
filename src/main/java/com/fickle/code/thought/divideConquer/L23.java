package com.fickle.code.thought.divideConquer;

import com.fickle.code.leetcode.ListNode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 合并 K 个升序链表
 * 给你一个链表数组，每个链表都已经按升序排列。
 * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
 * <p>
 * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
 * 输出：[1,1,2,3,4,4,5,6]
 * 解释：链表数组如下：
 * [
 * 1->4->5,
 * 1->3->4,
 * 2->6
 * ]
 * 将它们合并到一个有序链表中得到。
 * 1->1->2->3->4->4->5->6
 *
 * @author Administrator
 * @apiNote com.fickle.code.thought.divideConquer
 */
public class L23 {
    /**
     * 局部合并两个列表，依次合并
     *
     * 改进方案：凡局部，必可归并
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode head = null;
        for (ListNode node : lists) {
            head = mergeNode(head, node);
        }

        return head;
    }

    private ListNode mergeNode(ListNode first, ListNode second) {
        ListNode result = new ListNode();
        ListNode curr = result;
        while (first != null && second != null) {
            if (first.val < second.val) {
                curr.next = first;
                first = first.next;
            } else {
                curr.next = second;
                second = second.next;
            }
            curr = curr.next;
        }
        // 处理剩余的数据
        if (first == null) {
            curr.next = second;
        } else {
            curr.next = first;
        }

        return result.next;
    }


    /**
     * leetcode 时间复杂度最低解法
     * 对传入的列表进行归并合并
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists1(ListNode[] lists) {
        return merge(lists, 0, lists.length - 1);
    }

    private ListNode merge(ListNode[] lists, int l, int r) {
        if (l > r) return null;
        if (l == r) return lists[l];
        int mid = (l + r) / 2;
        return mergeTwoLists(merge(lists, l, mid), merge(lists, mid + 1, r));
    }

    public ListNode mergeTwoLists(ListNode a, ListNode b) {
        if (a == null || b == null) {
            return a != null ? a : b;
        }
        ListNode head = new ListNode(0);
        ListNode tail = head, aPtr = a, bPtr = b;
        while (aPtr != null && bPtr != null) {
            if (aPtr.val < bPtr.val) {
                tail.next = aPtr;
                aPtr = aPtr.next;
            } else {
                tail.next = bPtr;
                bPtr = bPtr.next;
            }
            tail = tail.next;
        }
        tail.next = (aPtr != null ? aPtr : bPtr);
        return head.next;
    }

    /**
     * leetcode 空间复杂度最低解法
     * 构造一个优先级队列，对所有节点使用堆排序
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists2(ListNode[] lists) {
        PriorityQueue<ListNode> q = new PriorityQueue<>(Comparator.comparingInt(node -> node.val));
        for (ListNode list : lists) {
            ListNode head = list;
            while (head != null) {
                q.add(head);
                head = head.next;
            }
        }

        ListNode sentinel = new ListNode();
        // 此处的巧思在于，使用了java中引用传递
        sentinel.next = q.peek();

        while (!q.isEmpty()) {
            ListNode curr = q.poll();
            curr.next = q.peek();
        }

        return sentinel.next;
    }


    public static void main(String[] args) {
        L23 l23 = new L23();

        // 测试用例: lists = [[1,4,5],[1,3,4],[2,6]]
        ListNode[] lists = new ListNode[3];
        lists[0] = ListNode.createList(new int[]{1, 4, 5});
        lists[1] = ListNode.createList(new int[]{1, 3, 4});
        lists[2] = ListNode.createList(new int[]{2, 6});

        System.out.println("输入的链表数组:");
        for (int i = 0; i < lists.length; i++) {
            System.out.print("链表 " + (i + 1) + ": ");
            ListNode.printList(lists[i]);
        }

        ListNode mergedHead = l23.mergeKLists(lists);

        System.out.println("合并后的链表:");
        ListNode.printList(mergedHead);
    }
}
