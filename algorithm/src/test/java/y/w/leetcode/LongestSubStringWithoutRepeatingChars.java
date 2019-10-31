package y.w.leetcode;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Given a string, find the length of the longest substring without
 * repeating characters.
 *
 * Example 1:
 * Input: "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3.
 *
 * Example 2:
 * Input: "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 *
 * Example 3:
 * Input: "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 *              Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 */
public class LongestSubStringWithoutRepeatingChars
{
    /**
     * There could be multiple sub-strings which satisfy the criteria. We will
     * always return the first one.
     *
     * @param s
     * @return
     */
    private String calculateLSS(String s)
    {
        if (s == null || s.length() == 0)
            return "";

        int lengthOfLongest = 0;
        int positionOfLongest = 0;

        int i = 0;
        while (i < s.length())
        {
            Map<Character, Integer> log = new HashMap<>();

            int startPos = i;

            log.put(s.charAt(i), i);

            boolean foundDuplicateChar = false;
            for (int j = i + 1; j < s.length(); j++) {
                final char c = s.charAt(j);
                if (log.containsKey(c)) {
                    // Found a repeating char
                    if (log.size() > lengthOfLongest) {
                        lengthOfLongest = log.size();
                        positionOfLongest = startPos;
                    }

                    // We found a sub string which has no repeating chars.
                    // Break and continue a new search starting from the next positon
                    // following the last occurance of s.charAt(j).
                    i = log.get(c) + 1;
                    foundDuplicateChar = true;
                    break;
                } else {
                    log.put(c, j);
                }
            }
            if (!foundDuplicateChar) {
                // From the startPos to the end of the string is a LSS.
                if (log.size() > lengthOfLongest) {
                    lengthOfLongest = log.size();
                    positionOfLongest = startPos;
                }
                break;
            }
        }
        return s.substring(positionOfLongest, positionOfLongest + lengthOfLongest);
    }

    @Test
    public void testLongestSubStringWithoutRepeatingChars()
    {
        assertEquals("abc", calculateLSS("abcabcbb"));
        assertEquals("b", calculateLSS("bbbbb"));
        assertEquals("wke", calculateLSS("pwwkew"));
        assertEquals("EKSFORG", calculateLSS("GEEKSFORGEEKS"));
        assertEquals("ABDEFG", calculateLSS("ABDEFGABEF"));
    }
}
