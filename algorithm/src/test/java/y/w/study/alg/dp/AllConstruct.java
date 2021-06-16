package y.w.study.alg.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class AllConstruct {
    @Test
    public void test() {
        // expected: [ab, cd, ef], [ab, c, def], [abc, def], [abcd, ef]
        List<List<String>> result = construct("abcdef", new String[]{"ab", "abc", "cd", "def", "abcd", "ef", "c"}, new HashMap<>());

        // expected: [purpl, le], [p, ur, p, le]
        result = construct("purple", new String[]{"purp", "p", "ur", "le", "purpl"}, new HashMap<>());
    }

    /**
     * m = size of target, n = size of wordBank
     * Brute Force: time O(n^m * m), space O(m^2)
     * Memoized: time(n * m^2), space(n * m)
     *
     * @param target
     * @param wordBank
     * @param memo
     * @return
     */
    public List<List<String>> construct(String target, String[] wordBank, Map<String, List<List<String>>> memo) {
        if (memo.containsKey(target)) return memo.get(target);

        if (target.isBlank()) return Arrays.asList(new ArrayList<>());

        List<List<String>> collection = new ArrayList<>();

        for (String w : wordBank) {
            if (target.indexOf(w) == 0) {
                String suffix = target.substring(w.length());
                List<List<String>> results = construct(suffix, wordBank, memo); // time m to handle substring, space extra copy created

                for (List<String> l : results) {
                    List<String> temp = new ArrayList<>();
                    temp.add(w);
                    temp.addAll(l);
                    collection.add(temp);
                }
            }
        }

        memo.put(target, collection);
        return collection;
    }


    @Test
    public void testTabular() {
        // expected: [ab, cd, ef], [ab, c, def], [abc, def], [abcd, ef]
        List<List<String>> result = constructTabular("abcdef", new String[]{"ab", "abc", "cd", "def", "abcd", "ef", "c"});

        // expected: [purpl, le], [p, ur, p, le]
        result = constructTabular("purple", new String[]{"purp", "p", "ur", "le", "purpl"});
    }

    /**
     * Time complexity: O(n * m^2)
     *      O(m + n * m * m + contants)
     *
     * Space complexity: O(m + n * m)
     *
     * @param target
     * @param wordBank
     * @return
     */
    public List<List<String>> constructTabular(String target, String[] wordBank) {
        List<List<List<String>>> mapping = new ArrayList<>(); // Time: c1 = O(1), Space: O(1)

        // [i] stores lists of list of matched words, the total length
        // of the matched words equals to i.
        for (int i = 0; i <= target.length(); i++) // Time: O(m), Space: O(m)
            mapping.add(new ArrayList<>());

        // Match prefix starting from position 0 to the end of target.
        for (int i = 0; i < target.length(); i++) { // Time: O(m), Space: O(1)

            // Try to match each word of wordBank.
            for (int j = 0; j < wordBank.length; j++) { // Time: O(n), Space: O(1)
                if (target.startsWith(wordBank[j], i)) {
                    int len = wordBank[j].length(); // Time: O(1), Space: O(1)

                    // Time: O(m), Space: O(m)
                    if (mapping.get(i).isEmpty()) {
                        mapping.get(len).add(Arrays.asList(wordBank[j]));
                    } else {
                        for (List<String> l : mapping.get(i)) {
                            // Copy the list of lists of words from mapping[i], adding wordBank[j]
                            // and store these lists into position (i + len), which is the new length
                            // of the matched words.
                            List<String> temp = new ArrayList<>();
                            temp.addAll(l);
                            temp.add(wordBank[j]);
                            mapping.get(i + len).add(temp);
                        }
                    }
                }
            }
        }

        return mapping.get(target.length());
    }
}
