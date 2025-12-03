package com.fickle.code.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在英语中，我们有一个叫做 词根(root) 的概念，可以词根 后面 添加其他一些词组成另一个较长的单词——我们称这个词为 衍生词 (derivative)。
 * 例如，词根 help，跟随着 继承词 "ful"，可以形成新的单词 "helpful"。
 * <p>
 * 现在，给定一个由许多 词根 组成的词典 dictionary 和一个用空格分隔单词形成的句子 sentence。你需要将句子中的所有 衍生词 用 词根 替换掉。
 * 如果 衍生词 有许多可以形成它的 词根，则用 最短 的 词根 替换它。
 * <p>
 * 你需要输出替换之后的句子。
 * <p>
 * 输入：dictionary = ["cat","bat","rat"], sentence = "the cattle was rattled by the battery"
 * 输出："the cat was rat by the bat"
 *
 * @author Administrator
 * @apiNote com.fickle.code.graph
 */
public class L648 {

    public String replaceWords(List<String> dictionary, String sentence) {
        Trie trie = new Trie();
        for (String word : dictionary) {
            Trie cur = trie;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                cur.children.putIfAbsent(c, new Trie());
                cur = cur.children.get(c);
            }
            cur.children.put('#', new Trie());
        }
        String[] words = sentence.split(" ");
        for (int i = 0; i < words.length; i++) {
            words[i] = findRoot(words[i], trie);
        }
        return String.join(" ", words);
    }

    public String findRoot(String word, Trie trie) {
        StringBuffer root = new StringBuffer();
        Trie cur = trie;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.children.containsKey('#')) {
                return root.toString();
            }
            if (!cur.children.containsKey(c)) {
                return word;
            }
            root.append(c);
            cur = cur.children.get(c);
        }
        return root.toString();
    }

    public static void main(String[] args) {
        List<String> dictionary = new ArrayList<String>();
        dictionary.add("a");
        dictionary.add("b");
        dictionary.add("c");
        System.out.println(new L648().replaceWords(dictionary, "aadsfasf absbs bbab cadsfafs"));
    }

    class Trie {
        Map<Character, Trie> children;

        public Trie() {
            children = new HashMap<>();
        }
    }
}
