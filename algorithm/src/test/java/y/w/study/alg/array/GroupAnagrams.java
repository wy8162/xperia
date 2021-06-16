package y.w.study.alg.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

/**
 * Given an array of strings strs, group the anagrams together. You can return the answer in any order.
 *
 * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically
 * using all the original letters exactly once.
 *
 * Constraints:
 * - 1 <= strs.length <= 10^4
 * - 0 <= strs[i].length <= 100
 * - strs[i] consists of lower-case English letters.
 */
public class GroupAnagrams {
    @Test
    public void test() {
        List<List<String>> answers;

        answers = groupAnagramsSortingStrings(new String[]{"are", "bat", "ear", "code", "tab", "era"});
        answers = groupAnagramsSortingStrings(new String[]{"aab", "aba", "baa", "abbcc"});
        answers = groupAnagramsSortingStrings(new String[]{"eat","tea","tan","ate","nat","bat"});

        answers = groupAnagramsCountingChars(new String[]{"are", "bat", "ear", "code", "tab", "era"});
        answers = groupAnagramsCountingChars(new String[]{"aab", "aba", "baa", "abbcc"});

    }

    /**
     * m = number of elements of strs
     * n = total number of chars of all strings
     * k = max length of all strings
     *
     * Time: O(m * klg(k))
     * Space: O(m * k)
     *
     * @param strs
     * @return
     */
    public List<List<String>> groupAnagramsSortingStrings(String[] strs) {
        if (strs == null || strs.length == 0) return new ArrayList<>(); // Time: O(1)

        Map<String, List<String>> answers = new HashMap<>(); // Time: O(1)

        for (String s : strs) { // Time: O(m)
            char[] arr = s.toCharArray(); // Time: O(1), Space: O(k)
            Arrays.sort(arr);             // Time: O(klg(k))
            String temp = String.copyValueOf(arr);

            // Time: O(1), Space: O(1)
            if (!answers.containsKey(temp))
                answers.put(temp, new ArrayList<>());

            answers.get(temp).add(s);
        }

        return new ArrayList<>(answers.values()); // Time: O(m), Space: O(m * k)
    }

    /**
     * Time: O(m * k)
     * Space: (m * k)
     *
     * @param strs
     * @return
     */
    public List<List<String>> groupAnagramsCountingChars(String[] strs) {
        if (strs == null || strs.length == 0) return new ArrayList<>();

        Map<String, List<String>> answers = new HashMap<>();

        int[] charCounts = new int[26];

        for (String s: strs) {
            Arrays.fill(charCounts, 0);

            for (int i = 0; i < s.length(); i++)
                charCounts[s.charAt(i) - 'a']++;

            StringBuilder sb = new StringBuilder();
            for (int i : charCounts) sb.append("@" + i);

            String key = sb.toString();

            answers.computeIfAbsent(key, k -> new ArrayList<>() );

            answers.get(key).add(s);
        }

        return new ArrayList<>(answers.values());
    }
}
