package com.fickle.code.leetcode;

import java.util.Stack;

/**
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
 * <p>
 * 有效字符串需满足：
 * <p>
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 每个右括号都有一个对应的相同类型的左括号。
 *
 * @author Administrator
 * @apiNote com.fickle.code.leetcode
 */
public class L20 {
    public static boolean isValid(String s) {
        if (s.length() % 2 != 0) {
            return false;
        }
        boolean res = true;
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            }
            if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) {
                    return false;
                }
                if (c == ')' && stack.pop() != '(') {
                    return false;
                }
                if (c == ']' && stack.pop() != '[') {
                    return false;
                }
                if (c == '}' && stack.pop() != '{') {
                    return false;
                }
            }
        }
        if (!stack.isEmpty()) {
            return false;
        }

        return res;
    }

    /**
     * 开局直接判断是否是偶数
     * 遍历字符数组，每遇到一个左括号，就将数组的位置置为右括号
     * 遍历完左后，遍历右时直接反向匹配
     *
     * @param s
     * @return
     */
    public boolean isValid1(String s) {
        if (s.length() % 2 != 0) {
            return false;
        }

        char[] charArray = s.toCharArray();
        int top = 0;
        for (char c : charArray) {
            if (c == '(') {
                charArray[top++] = ')';
            } else if (c == '{') {
                charArray[top++] = '}';
            } else if (c == '[') {
                charArray[top++] = ']';
            } else {
                if (top == 0 || charArray[--top] != c) {
                    return false;
                }
            }
        }
        return top == 0;
    }


    public static void main(String[] args) {
        System.out.println(isValid("([])"));
    }
}
