package y.w.study.alg.array;

import java.util.Arrays;
import java.util.Map;
import org.junit.Test;

/**
 * Given a string s, find the length of the longest substring without repeating characters.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3.
 * Example 2:
 *
 * Input: s = "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 * Example 3:
 *
 * Input: s = "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 * Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
 * Example 4:
 *
 * Input: s = ""
 * Output: 0
 */
public class LongestSubStringWithoutRepeatingChars {
    @Test
    public void test() {
        assert 3 == lengthOfLongestSubstring("abcabcbb");
        assert 1 == lengthOfLongestSubstring("bbbbb");
        assert 3 == lengthOfLongestSubstring("pwwkew");
    }


   public int lengthOfLongestSubstring(String s) {
        int start = 0;
        int length = 0;
        int[] postions = new int[256];
        Arrays.fill(postions, -1);

        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i);

            // Disregard those before start position.
            if (postions[c] >= start) {
                // c was found before.
                length = Math.max(length, i - start);
                start = postions[c] + 1; // Start moved to the next position, skipping the previous duplicate char.
            }
            postions[c] = i;
        }

        return Math.max(length, s.length() - start);
   }
}
