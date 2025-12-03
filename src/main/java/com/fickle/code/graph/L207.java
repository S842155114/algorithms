package com.fickle.code.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 经典的拓扑排序问题
 * <p>
 * 你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。
 * <p>
 * 在选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，
 * 其中 prerequisites[i] = [ai, bi]，表示如果要学习课程 ai 则 必须 先学习课程  bi 。
 * <p>
 * 例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
 * 请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
 * <p>
 * 输入：numCourses = 2, prerequisites = [[1,0],[0,1]]
 * 输出：false
 * 解释：总共有 2 门课程。学习课程 1 之前，你需要先完成课程 0 ；并且学习课程 0 之前，你还应先完成课程 1 。这是不可能的。
 *
 * @author Administrator
 * @apiNote com.fickle.code.graph
 */
public class L207 {
    /**
     * 根据条件先构造有向图，根据出度或入度两个维度对每个节点进行操作
     *
     * @param numCourses    课程数
     * @param prerequisites 前置条件
     * @return
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 构造数组存储节点入度
        int[] indeg = new int[numCourses];

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] prerequisite : prerequisites) {
            graph.get(prerequisite[1]).add(prerequisite[0]); // prerequisite=[1,0]  0->1
            indeg[prerequisite[0]]++;
        }
        // 通过不同标识，标识该节点是否已经遍历过，0未遍历、1遍历中、2已遍历
        int[] visited = new int[numCourses];

        // 使用深度优先
        //for (int i = 0; i < numCourses; i++) {
        //    if (!dfs(graph, visited, i)) {
        //        return false;
        //    }
        //}

        // 使用广度优先
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indeg[i] == 0) {
                queue.add(i);
            }
        }
        // 标识处理的节点数
        int handleCount = 0;
        // 循环队列
        while (!queue.isEmpty()) {
            handleCount++;
            // 取队列头节点，并将其指向的所有节点的入度-1
            int course = queue.poll();
            for (int i : graph.get(course)) {
                indeg[i]--;
                if (indeg[i] == 0) {
                    queue.add(i);
                }
            }
            if (handleCount == numCourses){
                return true;
            }
        }
        return false;
    }

    /**
     * 深度优先遍历，按图方向递归遍历每一个节点，遍历完成后置为2并返回，如果在遍历过程中发现需要遍历的节点是1，
     * 也就是遍历中，那么就说明被依赖的节点正在查找依赖，说明成环了
     *
     * @param graph
     * @param visited
     * @param index
     * @return
     */
    private boolean dfs(List<List<Integer>> graph, int[] visited, int index) {
        visited[index] = 1;
        for (int next : graph.get(index)) {
            if (visited[next] == 0 && !dfs(graph, visited, next)) {
                return false;
            } else if (visited[next] == 1) {
                return false;
            }
        }
        visited[index] = 2;
        return true;
    }
}
