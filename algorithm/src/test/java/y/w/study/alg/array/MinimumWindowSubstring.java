package y.w.study.alg.array;

import static org.junit.Assert.assertEquals;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

/**
 * Given two strings s and t of lengths m and n respectively, return the minimum window substring of
 * s such that every character in t (including duplicates) is included in the window. If there is no
 * such substring, return the empty string "".
 *
 * The testcases will be generated such that the answer is unique.
 *
 * A substring is a contiguous sequence of characters within the string.
 *
 * Constraints:
 * m == s.length
 * n == t.length
 * 1 <= m, n <= 10^5
 * s and t consist of uppercase and lowercase English letters.
 */
public class MinimumWindowSubstring {

    @Test
    public void test() {
        assertEquals("baca", minWindow("acbbaca", "aba"));
        assertEquals("aa", minWindow("aa", "aa"));
        assertEquals("BANC", minWindow("ADOBECODEBANC", "ABC"));
        assertEquals("a", minWindow("a", "a"));
        assertEquals("", minWindow("a", "aa"));
    }

    /**
     * m = s.length
     * n = t.length
     *
     * Time: O(m + n)
     * Space: O(512 + n)
     *
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) return ""; // Time: O(1)

        // Stores positions for the corresponding chars.
        Deque<Integer> positions = new ArrayDeque<>(); // Time: O(1), Space: O(n)

        // Stores counts of recurrences of chars
        int[] counts = new int[256]; // Time: O(1), Space: O(256)

        // Stores the expected counts of the corresponding chars.
        int[] expected = new int[256]; // Time: O(1), Space: O(256)

        for (int i = 0; i < t.length(); i++) expected[t.charAt(i)]++; // Time: O(1)

        // Time: O(1)
        // result[0] = the minimum length of substring, initialized with -1 meaning no solution yet,
        // result[1] = start index, result[2] = end index inclusive.
        int[] result = new int[3];
        result[0] = -1;

        int r = 0, numCharsMatched = 0;

        while (true) { // Break the loop only when there is nothing to be contracted. Time: O(m + n)
            if (numCharsMatched == t.length() && positions.size() >= t.length()) { // Time: O(1), Space: O(1)
                // Calculate result
                int newWindowLength = r - positions.getFirst();

                if (result[0] < 0 || newWindowLength < result[0]) {
                    result[0] = newWindowLength;
                    result[1] = positions.getFirst();
                    result[2] = r;
                }
            }

            char c;

            // If the current window has < t.length chars matched, expand.
            if (numCharsMatched < t.length() && r < s.length()) {
                c = s.charAt(r);
                if (t.indexOf(c) >= 0) {
                    positions.addLast(r);
                    counts[c]++;
                    if (counts[c] <= expected[c]) numCharsMatched++;
                }
                r++;
            // Contract the window
            } else if (positions.size() > 0) {
                int l = positions.removeFirst();
                c = s.charAt(l);
                counts[c]--;
                if (counts[c] < expected[c]) {
                    numCharsMatched--;
                }
            } else {
                break;
            }
        }

        if (result[0] < 0) return "";

        return s.substring(result[1], result[2]);
    }

    /**
     * Time: O(|S| + |T|)
     * Space: O(|S| + |T|)
     *
     * @param s
     * @param t
     * @return
     */
    public String minWindowLeetCode1(String s, String t) {

        if (s.length() == 0 || t.length() == 0) {
            return "";
        }

        // Dictionary which keeps a count of all the unique characters in t.
        Map<Character, Integer> dictT = new HashMap<Character, Integer>();
        for (int i = 0; i < t.length(); i++) {
            int count = dictT.getOrDefault(t.charAt(i), 0);
            dictT.put(t.charAt(i), count + 1);
        }

        // Number of unique characters in t, which need to be present in the desired window.
        int required = dictT.size();

        // Left and Right pointer
        int l = 0, r = 0;

        // formed is used to keep track of how many unique characters in t
        // are present in the current window in its desired frequency.
        // e.g. if t is "AABC" then the window must have two A's, one B and one C.
        // Thus formed would be = 3 when all these conditions are met.
        int formed = 0;

        // Dictionary which keeps a count of all the unique characters in the current window.
        Map<Character, Integer> windowCounts = new HashMap<Character, Integer>();

        // ans list of the form (window length, left, right)
        int[] ans = {-1, 0, 0};

        while (r < s.length()) {
            // Add one character from the right to the window
            char c = s.charAt(r);
            int count = windowCounts.getOrDefault(c, 0);
            windowCounts.put(c, count + 1);

            // If the frequency of the current character added equals to the
            // desired count in t then increment the formed count by 1.
            if (dictT.containsKey(c) && windowCounts.get(c).intValue() == dictT.get(c).intValue()) {
                formed++;
            }

            // Try and contract the window till the point where it ceases to be 'desirable'.
            while (l <= r && formed == required) {
                c = s.charAt(l);
                // Save the smallest window until now.
                if (ans[0] == -1 || r - l + 1 < ans[0]) {
                    ans[0] = r - l + 1;
                    ans[1] = l;
                    ans[2] = r;
                }

                // The character at the position pointed by the
                // `Left` pointer is no longer a part of the window.
                windowCounts.put(c, windowCounts.get(c) - 1);
                if (dictT.containsKey(c) && windowCounts.get(c).intValue() < dictT.get(c).intValue()) {
                    formed--;
                }

                // Move the left pointer ahead, this would help to look for a new window.
                l++;
            }

            // Keep expanding the window once we are done contracting.
            r++;
        }

        return ans[0] == -1 ? "" : s.substring(ans[1], ans[2] + 1);
    }


    public String minWindowLeetcode2(String s, String t) {

        if (s.length() == 0 || t.length() == 0) {
            return "";
        }

        Map<Character, Integer> dictT = new HashMap<Character, Integer>();

        for (int i = 0; i < t.length(); i++) {
            int count = dictT.getOrDefault(t.charAt(i), 0);
            dictT.put(t.charAt(i), count + 1);
        }

        int required = dictT.size();

        // Filter all the characters from s into a new list along with their index.
        // The filtering criteria is that the character should be present in t.
        List<Pair<Integer, Character>> filteredS = new ArrayList<Pair<Integer, Character>>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (dictT.containsKey(c)) {
                filteredS.add(Pair.of(i, c));
            }
        }

        int l = 0, r = 0, formed = 0;
        Map<Character, Integer> windowCounts = new HashMap<Character, Integer>();
        int[] ans = {-1, 0, 0};

        // Look for the characters only in the filtered list instead of entire s.
        // This helps to reduce our search.
        // Hence, we follow the sliding window approach on as small list.
        while (r < filteredS.size()) {
            char c = filteredS.get(r).getValue();
            int count = windowCounts.getOrDefault(c, 0);
            windowCounts.put(c, count + 1);

            if (dictT.containsKey(c) && windowCounts.get(c).intValue() == dictT.get(c).intValue()) {
                formed++;
            }

            // Try and contract the window till the point where it ceases to be 'desirable'.
            while (l <= r && formed == required) {
                c = filteredS.get(l).getValue();

                // Save the smallest window until now.
                int end = filteredS.get(r).getKey();
                int start = filteredS.get(l).getKey();
                if (ans[0] == -1 || end - start + 1 < ans[0]) {
                    ans[0] = end - start + 1;
                    ans[1] = start;
                    ans[2] = end;
                }

                windowCounts.put(c, windowCounts.get(c) - 1);
                if (dictT.containsKey(c) && windowCounts.get(c).intValue() < dictT.get(c).intValue()) {
                    formed--;
                }
                l++;
            }
            r++;
        }
        return ans[0] == -1 ? "" : s.substring(ans[1], ans[2] + 1);
    }
}
