package com.fickle.code.tree;

/**
 * 哈夫曼树
 * 哈夫曼又称最优二叉树, 是一种带权路径长度最短的二叉树。注意带权路径WPL是指叶子节点
 *
 * 路径与路径长度: 从树中一个节点到另一个节点之间的分支构成了两个节点之间的路径，路径上的分支数目称作路径长度。
 *          若规定根节点位于第一层，则根节点到第H层的节点的路径长度为H-1。如到40 的路径长度为1；30的路径长度为2；20的路径长度为3。
 * 节点的权: 将树中的节点赋予一个某种含义的数值作为该节点的权值，该值称为节点的权；
 * 带权路径长度: 从根节点到某个节点之间的路径长度与该节点的权的乘积。例如节点10的路径长度为3,它的带权路径长度为10 * 3 = 30；
 * 树的带权路径长度: 树的带权路径长度为所有叶子节点的带权路径长度之和，称为WPL。
 *              WPL = 1x40+2x30+3x10+3x20 = 190，而哈夫曼树就是树的带权路径最小的二叉树
 *
 * @author Administrator
 * @apiNote com.fickle.code.tree
 */
public class Huffman {

    private HuffmanNode mRoot;	// 根结点

    /*
     * 创建Huffman树
     *
     * @param 权值数组
     */
    public Huffman(int a[]) {
        HuffmanNode parent = null;
        MinHeap heap;

        // 建立数组a对应的最小堆
        heap = new MinHeap(a);

        for(int i=0; i<a.length-1; i++) {
            HuffmanNode left = heap.dumpFromMinimum();  // 最小节点是左孩子
            HuffmanNode right = heap.dumpFromMinimum(); // 其次才是右孩子

            // 新建parent节点，左右孩子分别是left/right；
            // parent的大小是左右孩子之和
            parent = new HuffmanNode(left.key+right.key, left, right, null);
            left.parent = parent;
            right.parent = parent;

            // 将parent节点数据拷贝到"最小堆"中
            heap.insert(parent);
        }

        mRoot = parent;

        // 销毁最小堆
        heap.destroy();
    }

    /*
     * 前序遍历"Huffman树"
     */
    private void preOrder(HuffmanNode tree) {
        if(tree != null) {
            System.out.print(tree.key+" ");
            preOrder(tree.left);
            preOrder(tree.right);
        }
    }

    public void preOrder() {
        preOrder(mRoot);
    }

    /*
     * 中序遍历"Huffman树"
     */
    private void inOrder(HuffmanNode tree) {
        if(tree != null) {
            inOrder(tree.left);
            System.out.print(tree.key+" ");
            inOrder(tree.right);
        }
    }

    public void inOrder() {
        inOrder(mRoot);
    }


    /*
     * 后序遍历"Huffman树"
     */
    private void postOrder(HuffmanNode tree) {
        if(tree != null)
        {
            postOrder(tree.left);
            postOrder(tree.right);
            System.out.print(tree.key+" ");
        }
    }

    public void postOrder() {
        postOrder(mRoot);
    }

    /*
     * 销毁Huffman树
     */
    private void destroy(HuffmanNode tree) {
        if (tree==null)
            return ;

        if (tree.left != null)
            destroy(tree.left);
        if (tree.right != null)
            destroy(tree.right);

        tree=null;
    }

    public void destroy() {
        destroy(mRoot);
        mRoot = null;
    }

    /*
     * 打印"Huffman树"
     *
     * key        -- 节点的键值
     * direction  --  0，表示该节点是根节点;
     *               -1，表示该节点是它的父结点的左孩子;
     *                1，表示该节点是它的父结点的右孩子。
     */
    private void print(HuffmanNode tree, int key, int direction) {

        if(tree != null) {

            if(direction==0)	// tree是根节点
                System.out.printf("%2d is root\n", tree.key);
            else				// tree是分支节点
                System.out.printf("%2d is %2d's %6s child\n", tree.key, key, direction==1?"right" : "left");

            print(tree.left, tree.key, -1);
            print(tree.right,tree.key,  1);
        }
    }

    public void print() {
        if (mRoot != null)
            print(mRoot, mRoot.key, 0);
    }
}
