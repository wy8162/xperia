package y.w.study.alg.array;

import org.junit.Test;

/**
 * Given a string s, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
 */
public class ValidPalindrome {
    @Test
    public void test() {
        assert isPalindrome("A man, a plan, a canal: Panama");
        assert !isPalindrome("race a car");
    }

    /**
     * Time: O(n)
     * Space: O(2n)
     *
     * @param s
     * @return
     */
    public boolean isPalindrome(String s) {
        if (s == null) return false;
        if (s.length() == 1 && Character.isLetterOrDigit(s.charAt(0))) return true;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c))
                sb.append(c);
        }

        String filtered = sb.toString();
        String filteredReversed = sb.reverse().toString();

        return filtered.equalsIgnoreCase(filteredReversed);
    }
}
