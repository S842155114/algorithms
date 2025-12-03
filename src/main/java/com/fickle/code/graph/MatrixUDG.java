package com.fickle.code.graph;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * 邻接矩阵实现无向图
 *
 * @author Administrator
 * @apiNote com.fickle.code.graph
 */
public class MatrixUDG {
    private char[] mVexs;       // 顶点集合
    private int[][] mMatrix;    // 邻接矩阵

    private final static int INF = Integer.MAX_VALUE;

    /*
     * 创建图(自己输入数据)
     */
    public MatrixUDG() {

        // 输入"顶点数"和"边数"
        System.out.printf("input vertex number: ");
        int vlen = readInt();
        System.out.printf("input edge number: ");
        int elen = readInt();
        if (vlen < 1 || elen < 1 || (elen > (vlen * (vlen - 1)))) {
            System.out.printf("input error: invalid parameters!\n");
            return;
        }

        // 初始化"顶点"
        mVexs = new char[vlen];
        for (int i = 0; i < mVexs.length; i++) {
            System.out.printf("vertex(%d): ", i);
            mVexs[i] = readChar();
        }

        // 初始化"边"
        mMatrix = new int[vlen][vlen];
        for (int i = 0; i < elen; i++) {
            // 读取边的起始顶点和结束顶点
            System.out.printf("edge(%d):", i);
            char c1 = readChar();
            char c2 = readChar();
            int p1 = getPosition(c1);
            int p2 = getPosition(c2);

            if (p1 == -1 || p2 == -1) {
                System.out.printf("input error: invalid edge!\n");
                return;
            }

            mMatrix[p1][p2] = 1;
            mMatrix[p2][p1] = 1;
        }
    }

    /*
     * 创建图(用已提供的矩阵)
     *
     * 参数说明：
     *     vexs  -- 顶点数组
     *     edges -- 边数组
     */
    public MatrixUDG(char[] vexs, char[][] edges) {

        // 初始化"顶点数"和"边数"
        int vlen = vexs.length;
        int elen = edges.length;

        // 初始化"顶点"
        mVexs = new char[vlen];
        for (int i = 0; i < mVexs.length; i++)
            mVexs[i] = vexs[i];

        // 初始化"边"
        mMatrix = new int[vlen][vlen];
        for (int i = 0; i < elen; i++) {
            // 读取边的起始顶点和结束顶点
            int p1 = getPosition(edges[i][0]);
            int p2 = getPosition(edges[i][1]);

            mMatrix[p1][p2] = 1;
            mMatrix[p2][p1] = 1;
        }
    }

    /*
     * 返回ch位置
     */
    private int getPosition(char ch) {
        for (int i = 0; i < mVexs.length; i++)
            if (mVexs[i] == ch)
                return i;
        return -1;
    }

    /*
     * 读取一个输入字符
     */
    private char readChar() {
        char ch = '0';

        do {
            try {
                ch = (char) System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')));

        return ch;
    }

    /*
     * 读取一个输入字符
     */
    private int readInt() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    /*
     * 返回顶点v的第一个邻接顶点的索引，失败则返回-1
     */
    private int firstVertex(int v) {

        if (v < 0 || v > (mVexs.length - 1))
            return -1;

        for (int i = 0; i < mVexs.length; i++)
            if (mMatrix[v][i] == 1)
                return i;

        return -1;
    }

    /*
     * 返回顶点v相对于w的下一个邻接顶点的索引，失败则返回-1
     */
    private int nextVertex(int v, int w) {

        if (v < 0 || v > (mVexs.length - 1) || w < 0 || w > (mVexs.length - 1))
            return -1;

        for (int i = w + 1; i < mVexs.length; i++)
            if (mMatrix[v][i] == 1)
                return i;

        return -1;
    }

    /*
     * 深度优先搜索遍历图的递归实现
     */
    private void DFS(int i, boolean[] visited) {

        visited[i] = true;
        System.out.printf("%c ", mVexs[i]);
        // 遍历该顶点的所有邻接顶点。若是没有访问过，那么继续往下走
        for (int w = firstVertex(i); w >= 0; w = nextVertex(i, w)) {
            if (!visited[w])
                DFS(w, visited);
        }
    }

    /*
     * 深度优先搜索遍历图
     */
    public void DFS() {
        boolean[] visited = new boolean[mVexs.length];       // 顶点访问标记

        // 初始化所有顶点都没有被访问
        for (int i = 0; i < mVexs.length; i++)
            visited[i] = false;

        System.out.printf("DFS: ");
        for (int i = 0; i < mVexs.length; i++) {
            if (!visited[i])
                DFS(i, visited);
        }
        System.out.printf("\n");
    }

    /*
     * 广度优先搜索（类似于树的层次遍历）
     */
    public void BFS() {
        int head = 0;
        int rear = 0;
        int[] queue = new int[mVexs.length];            // 辅组队列
        boolean[] visited = new boolean[mVexs.length];  // 顶点访问标记

        for (int i = 0; i < mVexs.length; i++)
            visited[i] = false;

        System.out.printf("BFS: ");
        for (int i = 0; i < mVexs.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                System.out.printf("%c ", mVexs[i]);
                queue[rear++] = i;  // 入队列
            }

            while (head != rear) {
                int j = queue[head++];  // 出队列
                for (int k = firstVertex(j); k >= 0; k = nextVertex(j, k)) { //k是为访问的邻接顶点
                    if (!visited[k]) {
                        visited[k] = true;
                        System.out.printf("%c ", mVexs[k]);
                        queue[rear++] = k;
                    }
                }
            }
        }
        System.out.printf("\n");
    }

    /*
     * 打印矩阵队列图
     */
    public void print() {
        System.out.printf("Martix Graph:\n");
        for (int i = 0; i < mVexs.length; i++) {
            for (int j = 0; j < mVexs.length; j++)
                System.out.printf("%d ", mMatrix[i][j]);
            System.out.printf("\n");
        }
    }

    public static void main(String[] args) {
        char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        char[][] edges = new char[][]{
                {'A', 'C'},
                {'A', 'D'},
                {'A', 'F'},
                {'B', 'C'},
                {'C', 'D'},
                {'E', 'G'},
                {'F', 'G'}};
        MatrixUDG pG;

        // 自定义"图"(输入矩阵队列)
        //pG = new MatrixUDG();
        // 采用已有的"图"
        pG = new MatrixUDG(vexs, edges);

        pG.print();   // 打印图
        pG.DFS();     // 深度优先遍历
        pG.BFS();     // 广度优先遍历
    }

    /**
     * Dijkstra算法最短路径。  可以使用最小堆进行优化    时间复杂度0(n2)
     * @param vs 起始顶点(start vertex)。即计算"顶点vs"到其它顶点的最短路径。
     * @param edgecost 构造的图的邻接矩阵
     */
    public void dijkstra(int vs,int[][] edgecost) {
        int length = mVexs.length;
        int[] prev = new int[length];
        int[] path2Vertex = new int[length];
        // flag[i]=true表示"顶点vs"到"顶点i"的最短路径已成功获取，通过此数组将节点分为已选择和未选择
        boolean[] flag = new boolean[length];
        // 需要构造连接图，此处并没有构造
        // edgecost[0][1] = 5;
        // 顶点i的最短路径为"顶点vs"到"顶点i"的权。
        System.arraycopy(edgecost[vs], 0, path2Vertex, 0, length);
        // 对"顶点vs"自身进行初始化
        flag[vs] = true;
        path2Vertex[vs] = 0;
        // 遍历length-1次；每次找出一个顶点的最短路径。
        int k = 0;
        for (int i = 1; i < length; i++) {
            // 寻找当前最小的路径；
            // 即，在未获取最短路径的顶点中，找到离vs最近的顶点(k)。
            int min = INF;
            for (int j = 0; j < length; j++) {
                if (!flag[j] && path2Vertex[j] < min) {
                    min = path2Vertex[j];
                    k = j;
                }
            }
            // 标记"顶点k"为已经获取到最短路径
            flag[k] = true;
            // 修正当前最短路径和前驱顶点
            // 即，当已经"顶点k的最短路径"之后，更新"未获取最短路径的顶点的最短路径和前驱顶点"。
            for (int j = 0; j < length; j++) {
                // 将顶点k关联的所有节点的权值与min相加作为  k后续节点到顶点的最小距离
                int minNext2Vx = (edgecost[k][j] == INF ? INF : (min + edgecost[k][j]));
                // 对k关联的节点到顶点的距离进行更新，并修改prev顶点
                if (!flag[j] && (minNext2Vx < path2Vertex[j])) {
                    path2Vertex[j] = minNext2Vx;
                    prev[j] = k;
                }
            }
        }
        // 打印dijkstra最短路径的结果
        System.out.printf("dijkstra(%c): \n", mVexs[vs]);
        for (int i = 0; i < length; i++)
            System.out.printf("  shortest(%c, %c)=%d\n", mVexs[vs], mVexs[i], path2Vertex[i]);
        printPath(prev,mVexs,0,mMatrix.length - 1);
    }

    // 在调用方或结果类中添加一个辅助方法用于打印dijkstra的路径
    public static void printPath(int[] predecessors, char[] vexs, int start, int end) {
        if (start == end) {
            System.out.print(vexs[start]);
            return;
        }
        if (predecessors[end] == -1) { // 假设不可达的前驱为-1
            System.out.printf("No path from %c to %c\n", vexs[start], vexs[end]);
            return;
        }
        // 使用栈或递归来反向打印路径
        LinkedList<Integer> stack = new LinkedList<>();
        int current = end;
        while (current != start) {
            stack.push(current);
            current = predecessors[current];
        }
        stack.push(start);

        while (!stack.isEmpty()) {
            System.out.print(vexs[stack.pop()]);
            if (!stack.isEmpty()) {
                System.out.print(" -> ");
            }
        }
    }



    /**
     * floyd算法求最短路径
     * 时间复杂度 O(n3)
     * @param path 路径。path[i][j]=k表示，"顶点i"到"顶点j"的最短路径会经过最后的顶点k。
     * @param dist 长度数组。即，dist[i][j]=sum表示，"顶点i"到"顶点j"的最短路径的长度是sum。
     * @param edgecost 构造的图的邻接矩阵
     */
    public void floyd(int[][] path, int[][] dist, int[][] edgecost) {

        // 初始化
        for (int i = 0; i < mVexs.length; i++) {
            for (int j = 0; j < mVexs.length; j++) {
                dist[i][j] = edgecost[i][j];    // "顶点i"到"顶点j"的路径长度为"i到j的权值"。
                path[i][j] = j;                // "顶点i"到"顶点j"的最短路径经过的最后一个顶点j。
            }
        }
        // 计算最短路径
        for (int k = 0; k < mVexs.length; k++) {
            for (int i = 0; i < mVexs.length; i++) {
                for (int j = 0; j < mVexs.length; j++) {
                    // 如果经过下标为k顶点路径比原两点间路径更短，则更新dist[i][j]和path[i][j]
                    int tmp = (dist[i][k] == INF || dist[k][j] == INF) ? INF : (dist[i][k] + dist[k][j]);
                    if (dist[i][j] > tmp) {
                        // "i到j最短路径"对应的值设，为更小的一个(即经过k)
                        dist[i][j] = tmp;
                        // "i到j最短路径"对应的路径，经过k
                        path[i][j] = path[i][k];
                    }
                }
            }
        }
        // 打印floyd最短路径的结果
        System.out.printf("floyd: \n");
        for (int i = 0; i < mVexs.length; i++) {
            for (int j = 0; j < mVexs.length; j++)
                System.out.printf("%2d  ", dist[i][j]);
            System.out.printf("\n");
        }
    }
}
