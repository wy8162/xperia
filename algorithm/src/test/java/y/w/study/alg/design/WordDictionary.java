package y.w.study.alg.design;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Design a data structure that supports adding new words and finding if a string matches any
 * previously added string.
 *
 * Implement the WordDictionary class:
 *
 * - WordDictionary() Initializes the object.
 * - void addWord(word) Adds word to the data structure, it can be matched later.
 * - bool search(word) Returns true if there is any string in the data structure
 *   that matches word or false otherwise. word may contain dots '.' where dots
 *   can be matched with any letter.
 */
public class WordDictionary {
    private CharNode root;

    private class CharNode {
        private final Map<Character, CharNode> children = new HashMap<>();
        private boolean isWord;

        public Map<Character, CharNode> getChildren() {
            return children;
        }

        public boolean isWord() {
            return isWord;
        }

        public void setWord(boolean word) {
            isWord = word;
        }
    }

    public WordDictionary() {
        this.root = new CharNode();
    }

    public void addWord(String word) {
        if (word == null || word.isEmpty()) return;

        CharNode current = root;

        for (char c : word.toCharArray()) {
            current = current.getChildren().computeIfAbsent(c, cc -> new CharNode());
        }

        current.setWord(true);
    }

    public boolean search(String word) {
        return searchRecursively(word.toCharArray(), 0, root);
    }

    public boolean searchRecursively(char[] word, int start, CharNode node) {
        for (int i = start; i < word.length; i++) {
            if (node.getChildren().containsKey(word[i])) {
                node = node.getChildren().get(word[i]);
            } else {
                if (word[i] == '.') {
                    for (Entry<Character, CharNode> entry : node.getChildren().entrySet()) {
                        if (searchRecursively(word, i + 1, entry.getValue()))
                            return true;
                    }
                }

                return false;
            }
        }

        return node.isWord();
    }
}
