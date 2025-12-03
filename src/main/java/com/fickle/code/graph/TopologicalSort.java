package com.fickle.code.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 当且仅当一个有向图为有向无环图(directed acyclic graph，或称DAG)时，才能得到对应于该图的拓扑排序。
 * 要想完成拓扑排序，我们每次都应当从入度为0的结点开始遍历。因为只有入度为0的结点才能够成为拓扑排序的起点。
 *
 * @author Administrator
 * @apiNote com.fickle.code.graph
 */
public class TopologicalSort {
    /**
     * 获取输入有向图的拓扑排序
     *
     * @param n     图中节点的数量
     * @param edges 输入有向图的边列表表示，每条边的格式为 {from, to}
     * @return 图的拓扑排序，存储在List<Integer>中
     */
    public List<Integer> topologicalSort(int n, int[][] edges) {
        List<Integer> topoRes = new ArrayList<>();
        int[] inDegree = new int[n];

        // 计算每个节点的入度
        for (int[] edge : edges) {
            int to = edge[1];
            inDegree[to]++;
        }

        Deque<Integer> deque = new ArrayDeque<>();

        // 从入度为0的节点开始
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) deque.offer(i);
        }

        while (!deque.isEmpty()) {
            int curr = deque.poll();
            topoRes.add(curr);

            // 减少所有邻居节点的入度
            for (int[] edge : edges) {
                int from = edge[0];
                int to = edge[1];
                // 入度顶点为当前节点则处理
                if (from == curr) {
                    inDegree[to]--;
                    // 将入度为0节点加入队列
                    if (inDegree[to] == 0) {
                        deque.offer(to);
                    }
                }
            }
        }

        return topoRes.size() == n ? topoRes : new ArrayList<>();
    }

    /**
     * 测试拓扑排序算法
     */
    public static void main(String[] args) {
        TopologicalSort ts = new TopologicalSort();

        // 测试用例1：简单的有向无环图
        // 边：0 -> 1, 1 -> 2, 0 -> 3
        int[][] edges1 = {{0, 1}, {1, 2}, {0, 3}};
        List<Integer> result1 = ts.topologicalSort(4, edges1);
        System.out.println("测试用例1结果: " + result1); // 期望: [0, 3, 1, 2] 或类似的拓扑排序

        // 测试用例2：另一个有向无环图
        // 边：3 -> 1, 3 -> 2, 2 -> 4, 1 -> 4
        int[][] edges2 = {{3, 1}, {3, 2}, {2, 4}, {1, 4}};
        List<Integer> result2 = ts.topologicalSort(5, edges2);
        System.out.println("测试用例2结果: " + result2); // 期望: [0, 3, 1, 2, 4] 或 [3, 1, 2, 4, 0] 或类似的拓扑排序

        // 测试用例3：有环的图（应返回空列表）
        // 边：0 -> 1, 1 -> 2, 2 -> 0
        int[][] edges3 = {{0, 1}, {1, 2}, {2, 0}};
        List<Integer> result3 = ts.topologicalSort(3, edges3);
        System.out.println("测试用例3结果（有环图）: " + result3); // 期望: []
    }
}
