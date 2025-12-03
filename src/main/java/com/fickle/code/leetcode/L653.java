package com.fickle.code.leetcode;

import java.util.*;

/**
 * 给定一个二叉搜索树 root 和一个目标结果 k，如果二叉搜索树中存在两个元素且它们的和等于给定的目标结果，则返回 true。
 *
 * @author Administrator
 * @apiNote com.fickle.code.leetcode
 */
public class L653 {
    /**
     * 找到 小于等于k~大于 k/2 的所有节点，将其作为根节点，遍历子树
     *
     * @param root
     * @param k
     * @return
     */
    static TreeNode root;
    public static boolean findTarget(TreeNode root, int k) {
        // 边界情况：如果树为空或只有一个节点，不可能找到两个节点
        L653.root = root;
        if (root == null || (root.left == null && root.right == null)) {
            return false;
        }

        // 按照思路，开始寻找候选节点并检查其配对
        return findCandidatesAndCheck(root, k);
    }

    /**
     * 递归辅助函数：在树中寻找所有值在 [k/2, k] 范围内的节点，并检查其配对
     * @param node 当前遍历的节点
     * @param k 目标和
     * @return 如果找到一对则返回true
     */
    private static boolean findCandidatesAndCheck(TreeNode node, int k) {
        if (node == null) {
            return false;
        }

        // 利用BST特性进行剪枝，提高效率
        // 如果当前节点值小于 k/2，那么它和它的左子树都不可能是候选节点
        if (node.val < k / 2.0) {
            return findCandidatesAndCheck(node.right, k);
        }
        // 如果当前节点值大于 k，那么它和它的右子树都不可能是候选节点
        if (node.val > k) {
            return findCandidatesAndCheck(node.left, k);
        }

        // 当前节点值在 [k/2, k] 范围内，是一个候选节点
        // 我们需要查找的目标值是 k - node.val
        int target = k - node.val;

        // 在整棵树中搜索目标值，但要排除当前节点自身
        if (searchInTree(root, target, node)) {
            return true; // 找到了！
        }

        // 如果在当前节点没找到配对，继续在其左右子树中寻找其他候选节点
        // 注意：这里需要遍历左右子树，因为当前节点的子节点也可能在 [k/2, k] 范围内
        return findCandidatesAndCheck(node.left, k) || findCandidatesAndCheck(node.right, k);
    }

    /**
     * 在BST中搜索特定值，并允许排除一个特定节点
     * @param node 搜索的起始节点（通常是root）
     * @param target 要搜索的目标值
     * @param nodeToExclude 需要排除的节点（防止节点与自身配对）
     * @return 如果找到目标值（且不是被排除的节点）则返回true
     */
    private static boolean searchInTree(TreeNode node, int target, TreeNode nodeToExclude) {
        if (node == null) {
            return false;
        }

        // 如果找到了目标值，并且这个节点不是我们要排除的那个，就成功
        if (node.val == target && node != nodeToExclude) {
            return true;
        }

        // 根据BST特性决定搜索方向
        if (target < node.val) {
            return searchInTree(node.left, target, nodeToExclude);
        } else { // target > node.val
            return searchInTree(node.right, target, nodeToExclude);
        }
    }


    /**
     * 官方解法  深度优先搜索 + 哈希表
     *
     * @param root
     * @param k
     * @return
     */
    Set<Integer> set = new HashSet<Integer>();
    public boolean findTarget1(TreeNode root, int k) {
        if (root == null) {
            return false;
        }
        if (set.contains(k - root.val)) {
            return true;
        }
        set.add(root.val);
        return findTarget1(root.left, k) || findTarget1(root.right, k);
    }

    /**
     * 官方  广度优先
     *
     * @param root
     * @param k
     * @return
     */
    public boolean findTarget2(TreeNode root, int k) {
        Set<Integer> set = new HashSet<Integer>();
        Queue<TreeNode> queue = new ArrayDeque<TreeNode>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (set.contains(k - node.val)) {
                return true;
            }
            set.add(node.val);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return false;
    }


    /**
     * 深度优先搜索 + 中序遍历 + 双指针
     *
     * @param root
     * @param k
     * @return
     */
    public boolean findTarget3(TreeNode root, int k) {
        List<Integer> list = new ArrayList<Integer>();
        inorderTraversal(root, list);
        int left = 0, right = list.size() - 1;
        while (left < right) {
            if (list.get(left) + list.get(right) == k) {
                return true;
            }
            if (list.get(left) + list.get(right) < k) {
                left++;
            } else {
                right--;
            }
        }
        return false;
    }

    public void inorderTraversal(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        inorderTraversal(node.left, list);
        list.add(node.val);
        inorderTraversal(node.right, list);
    }


    /**
     * 迭代 + 中序遍历 + 双指针
     * @param root
     * @param k
     * @return
     */
    public boolean findTarget4(TreeNode root, int k) {
        TreeNode left = root, right = root;
        Deque<TreeNode> leftStack = new ArrayDeque<TreeNode>();
        Deque<TreeNode> rightStack = new ArrayDeque<TreeNode>();
        leftStack.push(left);
        while (left.left != null) {
            leftStack.push(left.left);
            left = left.left;
        }
        rightStack.push(right);
        while (right.right != null) {
            rightStack.push(right.right);
            right = right.right;
        }
        while (left != right) {
            if (left.val + right.val == k) {
                return true;
            }
            if (left.val + right.val < k) {
                left = getLeft(leftStack);
            } else {
                right = getRight(rightStack);
            }
        }
        return false;
    }

    public TreeNode getLeft(Deque<TreeNode> stack) {
        TreeNode root = stack.pop();
        TreeNode node = root.right;
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
        return root;
    }

    public TreeNode getRight(Deque<TreeNode> stack) {
        TreeNode root = stack.pop();
        TreeNode node = root.left;
        while (node != null) {
            stack.push(node);
            node = node.right;
        }
        return root;
    }
}
