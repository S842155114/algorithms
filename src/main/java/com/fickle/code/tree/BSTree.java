package com.fickle.code.tree;

import lombok.*;

/**
 * 二叉搜索树的基本结构
 * <p>
 * <a href="https://pdai.tech/md/algorithm/alg-basic-tree-search.html">...</a>
 *
 * @author Administrator
 * @apiNote com.fickle.code.tree
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class BSTree<T extends Comparable<T>> {

    private Node<T> root;

    @Data
    @EqualsAndHashCode
    @ToString(callSuper = true)
    public class Node<T extends Comparable<T>> {
        public T key;
        public Node<T> left;
        public Node<T> right;
        public Node<T> parent;

        public Node(T key, Node<T> left, Node<T> right, Node<T> parent) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

    }

    /**
     * 前序遍历
     * 中左右
     *
     * @param root
     */
    public void front(Node<T> root) {
        // 中左右
        if (root == null) {
            return;
        }
        System.out.println(root.key);
        front(root.left);
        front(root.right);
    }

    /**
     * 中序遍历
     * 左中右
     *
     * @param root
     */
    public void middle(Node<T> root) {
        if (root == null) {
            return;
        }
        middle(root.left);
        System.out.println(root.key);
        middle(root.right);
    }

    /**
     * 后序遍历
     * 左右中
     *
     * @param root
     */
    public void back(Node<T> root) {
        if (root == null) {
            return;
        }
        back(root.left);
        back(root.right);
        System.out.println(root.key);
    }

    /*
     * 前序遍历"二叉树"
     */
    private void preOrder(Node<T> tree) {
        if (tree != null) {
            System.out.print(tree.key + " ");
            preOrder(tree.left);
            preOrder(tree.right);
        }
    }

    public void preOrder() {
        preOrder(root);
    }

    /*
     * 中序遍历"二叉树"
     */
    private void inOrder(Node<T> tree) {
        if (tree != null) {
            inOrder(tree.left);
            System.out.print(tree.key + " ");
            inOrder(tree.right);
        }
    }

    public void inOrder() {
        inOrder(root);
    }


    /*
     * 后序遍历"二叉树"
     */
    private void postOrder(Node<T> tree) {
        if (tree != null) {
            postOrder(tree.left);
            postOrder(tree.right);
            System.out.print(tree.key + " ");
        }
    }

    public void postOrder() {
        postOrder(root);
    }


    /*
     * (递归实现)查找"二叉树x"中键值为key的节点
     */
    private Node<T> search(Node<T> x, T key) {
        if (x == null)
            return x;

        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return search(x.left, key);
        else if (cmp > 0)
            return search(x.right, key);
        else
            return x;
    }

    public Node<T> search(T key) {
        return search(root, key);
    }

    /*
     * (非递归实现)查找"二叉树x"中键值为key的节点
     */
    private Node<T> iterativeSearch(Node<T> x, T key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);

            if (cmp < 0)
                x = x.left;
            else if (cmp > 0)
                x = x.right;
            else
                return x;
        }

        return x;
    }

    public Node<T> iterativeSearch(T key) {
        return iterativeSearch(root, key);
    }

    /*
     * 查找最大结点: 返回tree为根结点的二叉树的最大结点。
     */
    private Node<T> maximum(Node<T> tree) {
        if (tree == null)
            return null;

        while (tree.right != null)
            tree = tree.right;
        return tree;
    }

    public T maximum() {
        Node<T> p = maximum(root);
        if (p != null)
            return p.key;

        return null;
    }

    /*
     * 查找最小结点: 返回tree为根结点的二叉树的最小结点。
     */
    private Node<T> minimum(Node<T> tree) {
        if (tree == null)
            return null;

        while (tree.left != null)
            tree = tree.left;
        return tree;
    }

    public T minimum() {
        Node<T> p = minimum(root);
        if (p != null)
            return p.key;

        return null;
    }

    /*
     * 找结点(x)的前驱结点。即，查找"二叉树中数据值小于该结点"的"最大结点"。
     */
    public Node<T> predecessor(Node<T> x) {
        // 如果x存在左孩子，则"x的前驱结点"为 "以其左孩子为根的子树的最大结点"。
        if (x.left != null)
            return maximum(x.left);

        // 如果x没有左孩子。则x有以下两种可能: 
        // (01) x是"一个右孩子"，则"x的前驱结点"为 "它的父结点"。
        // (01) x是"一个左孩子"，则查找"x的最低的父结点，并且该父结点要具有右孩子"，找到的这个"最低的父结点"就是"x的前驱结点"。
        Node<T> y = x.parent;
        while ((y != null) && (x == y.left)) {
            x = y;
            y = y.parent;
        }

        return y;
    }

    /*
     * 找结点(x)的后继结点。即，查找"二叉树中数据值大于该结点"的"最小结点"。
     */
    public Node<T> successor(Node<T> x) {
        // 如果x存在右孩子，则"x的后继结点"为 "以其右孩子为根的子树的最小结点"。
        if (x.right != null)
            return minimum(x.right);

        // 如果x没有右孩子。则x有以下两种可能: 
        // (01) x是"一个左孩子"，则"x的后继结点"为 "它的父结点"。
        // (02) x是"一个右孩子"，则查找"x的最低的父结点，并且该父结点要具有左孩子"，找到的这个"最低的父结点"就是"x的后继结点"。
        Node<T> y = x.parent;
        while ((y != null) && (x == y.right)) {
            x = y;
            y = y.parent;
        }

        return y;
    }

    /*
     * 将结点插入到二叉树中
     *
     * 参数说明:
     *     tree 二叉树的
     *     z 插入的结点
     */
    private void insert(BSTree<T> bst, Node<T> z) {
        int cmp;
        Node<T> y = null; // 代表要插入节点的父节点
        Node<T> x = bst.root;

        // 查找z的插入位置
        while (x != null) {
            y = x;
            cmp = z.key.compareTo(x.key);
            if (cmp < 0)
                x = x.left;
            else
                x = x.right;
        }

        // 指定z的父节点
        z.parent = y;
        if (y == null)
            bst.root = z;
        else {
            cmp = z.key.compareTo(y.key);
            if (cmp < 0)
                y.left = z;
            else
                y.right = z;
        }
    }

    /*
     * 新建结点(key)，并将其插入到二叉树中
     *
     * 参数说明:
     *     tree 二叉树的根结点
     *     key 插入结点的键值
     */
    public void insert(T key) {
        Node<T> z = new Node<T>(key, null, null, null);

        // 如果新建结点失败，则返回。
        if (z != null)
            insert(this, z);
    }

    /*
     * 删除结点(z)，并返回被删除的结点
     *
     * 分不同的情况：
     * 1、被删除的节点没有子节点，直接删除
     * 2、被删除节点只有左或者右子节点，直接删除，用子节点填补
     * 3、被删除的节点有两个子节点，则获取该节点的前驱或后继节点
     *
     * 参数说明:
     *     bst 二叉树
     *     z 删除的结点
     */
    private Node<T> remove(BSTree<T> bst, Node<T> z) {
        // z是逻辑上被删除的节点
        // y是实际上被删除的节点，y有可能是z也有可能是z的后继节点
        // x是替换节点。它是 y 的唯一子节点（如果 y 有子节点的话），将被用来“填补”y被删除后留下的空位
        Node<T> x = null;
        Node<T> y = null;

        // 经过这一步，y 一定是一个最多只有一个子节点的节点，删除它会变得非常简单。
        if ((z.left == null) || (z.right == null))
            // 情况1或情况2：只有一个子节点，删除操作很简单，可以直接删除 z（即便有子，删除z后直接将子节点提上来）。所以，y被赋值为z
            y = z;
        else
            // 情况3：两个字节点都存在的场景
            // 直接删除 z 会很麻烦，因为它的两个子节点无处安放。
            // 此时，算法采用一个巧妙的替换策略：找到 z 的中序后继节点 successor(z)，用这个后继节点来“代替”z。
            // 后继节点是 z 的右子树中值最小的节点，它最多只有一个右子节点（不可能有左子节点，否则它就不是最小的了）。
            // 这样，删除这个后继节点就又回到了情况1或情况2。所以，y 被赋值为 z 的后继节点。
            y = successor(z);

        // 既然 y 最多只有一个子节点，那么它的子节点要么是左孩子，要么是右孩子，要么没有（null）。
        // x 就是 y 唯一的“后代”，它将被用来“继承”y 的位置。
        if (y.left != null) // 左子树存在
            x = y.left;
        else // 右子树存在
            x = y.right;

        // 嫁接的关键步骤1
        // y 马上要被删除了，它的子节点 x 将要被“挂”到 y 的父节点下面。
        // 所以，x 的父指针需要更新，指向 y 以前的父节点 y.parent。
        // 如果 y 是叶子节点，x 为 null，这一步就不需要做。
        if (x != null) // 将被删除节点的父节点赋值给即将替换该节点的父节点
            x.parent = y.parent;

        // 嫁接的关键步骤2
        // 这是最关键的“嫁接”操作，处理 y 被摘除后留下的空缺。
        // 情况A：y 是根节点 (y.parent == null)。如果 y 被删除，那么新的根节点就是 x。
        // 情况B：y 是其父节点的左孩子 (y == y.parent.left)。那么 y 的父节点的左指针需要指向新的孩子 x。
        // 情况C：y 是其父节点的右孩子。那么 y 的父节点的右指针需要指向新的孩子 x。
        // 小结：经过这一步，y 已经从树的结构中被完全移除了，x 成功地占据了 y 的位置。树的连接关系已经更新完毕。
        if (y.parent == null) // 被删除节点是根节点
            bst.root = x;
        else if (y == y.parent.left) // 被删除节点是左子树
            y.parent.left = x;
        else // 被删除节点是右子树
            y.parent.right = x;

        // 这个 if 只在一种情况下为真：目标节点z有两个子节点。
        // 在这种情况下，我们并没有真正删除 z，而是删除了它的后继节点 y。
        // 为了让逻辑上 z 被删除，我们做了一个“偷梁换柱”的操作：把 y 节点的值（key）复制到 z 节点上。
        // 这样，原来 z 位置的值就被替换成了后继节点的值，而原来的后继节点 y 已经在第四步中被物理删除了。
        // 从 BST 的角度看，包含旧 z.key 的节点已经不存在了，删除操作成功。
        // 小结：这一步保证了在删除有两个子节点的节点时，BST 的性质（左子树 < 根 < 右子树）依然能够保持。
        if (y != z) // 被删除节点为
            z.key = y.key;

        return y;
    }

    /*
     * 删除结点(z)，并返回被删除的结点
     *
     * 参数说明:
     *     tree 二叉树的根结点
     *     z 删除的结点
     */
    public void remove(T key) {
        Node<T> z, node;

        if ((z = search(root, key)) != null)
            if ((node = remove(this, z)) != null)
                node = null;
    }

    /*
     * 打印"二叉查找树"
     *
     * key        -- 节点的键值
     * direction  --  0，表示该节点是根节点;
     *               -1，表示该节点是它的父结点的左孩子;
     *                1，表示该节点是它的父结点的右孩子。
     */
    private void print(Node<T> tree, T key, int direction) {

        if (tree != null) {

            if (direction == 0)    // tree是根节点
                System.out.printf("%2d is root\n", tree.key);
            else                // tree是分支节点
                System.out.printf("%2d is %2d's %6s child\n", tree.key, key, direction == 1 ? "right" : "left");

            print(tree.left, tree.key, -1);
            print(tree.right, tree.key, 1);
        }
    }

    public void print() {
        if (root != null)
            print(root, root.key, 0);
    }

    /*
     * 销毁二叉树
     */
    private void destroy(Node<T> tree) {
        if (tree == null)
            return;

        if (tree.left != null)
            destroy(tree.left);
        if (tree.right != null)
            destroy(tree.right);

        tree = null;
    }

    public void clear() {
        destroy(root);
        root = null;
    }


}
