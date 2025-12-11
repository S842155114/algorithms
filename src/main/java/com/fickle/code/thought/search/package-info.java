/**
 * 搜索算法 主要包含BFS，DFS
 * <p>
 * 深度优先搜索和广度优先搜索广泛运用于树和图中，但是它们的应用远远不止如此。
 * <p>
 * 在程序实现 BFS 时需要考虑以下问题:
 * 队列: 用来存储每一轮遍历得到的节点；
 * 标记: 对于遍历过的节点，应该将它标记，防止重复遍历。
 * <p>
 * 在程序实现 DFS 时需要考虑以下问题:
 * 栈: 用栈来保存当前节点信息，当遍历新节点返回时能够继续遍历当前节点。可以使用递归栈。
 * 标记: 和 BFS 一样同样需要对已经遍历过的节点进行标记。
 *
 * @author Administrator
 * @apiNote com.fickle.code.thought.search
 */
package com.fickle.code.thought.search;