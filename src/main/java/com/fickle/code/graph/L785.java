package com.fickle.code.graph;

/**
 * 存在一个 无向图 ，图中有 n 个节点。其中每个节点都有一个介于 0 到 n - 1 之间的唯一编号。给你一个二维数组 graph ，
 * 其中 graph[u] 是一个节点数组，由节点 u 的邻接节点组成。形式上，对于 graph[u] 中的每个 v ，都存在一条位于节点 u 和节点 v 之间的无向边。
 * 该无向图同时具有以下属性：
 * 不存在自环（graph[u] 不包含 u）。
 * 不存在平行边（graph[u] 不包含重复值）。
 * 如果 v 在 graph[u] 内，那么 u 也应该在 graph[v] 内（该图是无向图）
 * 这个图可能不是连通图，也就是说两个节点 u 和 v 之间可能不存在一条连通彼此的路径。
 * <p>
 * 二分图 定义：如果能将一个图的节点集合分割成两个独立的子集 A 和 B ，并使图中的每一条边的两个节点一个来自 A 集合，一个来自 B 集合，就将这个图称为 二分图 。
 * <p>
 * 如果图是二分图，返回 true ；否则，返回 false 。
 *
 * @author Administrator
 * @apiNote com.fickle.code.graph
 */
public class L785 {
    /**
     * 对节点进行染色，保证每条边的两个节点是不同的颜色
     * 只对未染色的节点染色，对已染色的节点进行校验
     * @param graph
     * @return
     */
    public static boolean isBipartite(int[][] graph) {
        if (graph == null) {
            return false;
        }

        // 0 未着色，1  2
        int[] colors = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            if (colors[i] == 0) {
                if (!color(colors, i, 1, graph)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean color(int[] colors, int index, int color, int[][] graph) {
        colors[index] = color;
        int next = color == 1 ? 2 : 1;
        for (int i : graph[index]) {
            if (colors[i] == 0) {
                if (!color(colors, i, next, graph)) {
                    return false;
                }
            } else if (colors[i] != next) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        System.out.println(isBipartite(new int[][]{{1}, {0, 3}, {3}, {1, 2}}));
    }
}
