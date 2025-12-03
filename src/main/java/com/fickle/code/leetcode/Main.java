package com.fickle.code.leetcode;

/**
 *
 * @author Administrator
 * @apiNote com.fickle.code.leetcode
 */
public class Main {

    public static void main(String[] args) {
        ListNode head = new ListNode(0);
        ListNode temp = head;
        for (int i = 1; i < 10; i++) {
            temp.next = new ListNode(i);
            temp = temp.next;
        }

        L328.oddEvenList(head);
        temp = head;
        while(temp != null) {
            System.out.println(temp.val);
            temp = temp.next;
        }

    }


}
