package y.w.study.alg.array;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

/**
 * Given a string s and an integer k, return the length of the longest substring of s that contains at most k distinct characters.
 */
public class LongestSubstringWithAtMostKDistinctCharacters {
    @Test
    public void test() {
        assertEquals(3, lengthOfLongestSubstringKDistinct("abacc", 2));
        assertEquals(2, lengthOfLongestSubstringKDistinct("abee", 1));
        assertEquals(3, lengthOfLongestSubstringKDistinct("eceba", 2));
        assertEquals(2, lengthOfLongestSubstringKDistinct("aa", 2));
        assertEquals(1, lengthOfLongestSubstringKDistinct("a", 2));
        assertEquals(2, lengthOfLongestSubstringKDistinct("aa", 1));
    }

    /**
     * Time: O(n)
     * Space: O(n)
     *
     * @param s
     * @param k
     * @return
     */
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s == null) return 0;

        int maxLen = 0;

        // Stores the positions of distinct chars
        Map<Character, Integer> CM = new HashMap<>();

        int l = 0, r = 0;
        while (r < s.length()) {
            char c = s.charAt(r);

            CM.put(c, r); // updated the location if it exists.

            if (CM.size() == k + 1) {
                maxLen = Math.max(maxLen, r - l);

                int pos = Collections.min(CM.values());
                CM.remove(s.charAt(pos));

                l = pos + 1; // Move the sliding window to the right, to the left most distinct char.
            }

            if (r == s.length() - 1)
                maxLen = Math.max(maxLen, s.length() - l);

            r++;
        }

        return maxLen;
    }


    /**
     * Time: O(n^3)
     * Space: O(n)
     *
     * @param s
     * @param k
     * @return
     */
    public int lengthOfLongestSubstringKDistinctBruteForce(String s, int k) {
        if (s == null) return 0;

        int maxLen = 0;
        int setSize = k + 1;

        for (int i = 0; i < s.length(); i++) {
            Set<Character> set = new HashSet<>();

            for (int j = i; j < s.length();) {
                char c = s.charAt(j);
                set.add(c);

                setSize = set.size();
                if (set.size() == k) {
                    while (j < s.length() && set.contains(s.charAt(j)) ) j++;

                    maxLen = Math.max(maxLen, j - i);
                    break; // Done. There
                }

                j++;
            }
        }

        // If the whole string has fewer distinct chars than k.
        if (maxLen == 0 && setSize < k) return s.length();

        return maxLen;
    }
}
