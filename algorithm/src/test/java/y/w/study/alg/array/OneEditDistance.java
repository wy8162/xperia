package y.w.study.alg.array;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Given two strings s and t, return true if they are both one edit distance apart, otherwise return false.
 *
 * A string s is said to be one distance apart from a string t if you can:
 *
 * - Insert exactly one character into s to get t.
 * - Delete exactly one character from s to get t.
 * - Replace exactly one character of s with a different character to get t.
 */
public class OneEditDistance {
    @Test
    public void test() {
        assertTrue( isOneEditDistance("ab", "abc"));
        assertTrue(!isOneEditDistance("", ""));
        assertTrue( isOneEditDistance("a", ""));
        assertTrue( isOneEditDistance("", "A"));
    }

    public boolean isOneEditDistance(String s, String t) {
        if (s == null || t == null || (s.isEmpty() && t.isEmpty())) return false;

        if (Math.abs(s.length() - t.length()) > 1) return false; // one is at least two chars longer. No way to make it.

        if (s.length() > t.length()) // To make sure s is shorter or equal to t.
            return isOneEditDistance(t, s);

        int i = 0;
        int j = 0;

        while (i < s.length()) {
            if (s.charAt(i) != t.charAt(j)) {
                if (s.length() == t.length())
                    // The remaining substrings determine the final result: true or false.
                    return s.substring(i + 1).equals(t.substring(j + 1));
                else
                    // Lengths not equal. Deleting t[j] will make it equal?
                    return s.substring(i).equals(t.substring(j + 1));
            }
            i++;
            j++;
        }

        // Found no difference. If the two are equal, it makes it false.
        return t.length() - s.length() == 1;
    }
}
