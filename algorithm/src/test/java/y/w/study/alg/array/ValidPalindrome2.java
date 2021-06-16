package y.w.study.alg.array;

import org.junit.Test;

/**
 * Given a string s, return true if the s can be palindrome after deleting at most one character from it.
 */
public class ValidPalindrome2 {
    @Test
    public void test() {
        assert isPalindrome("ebcbbececabbacecbbcbe");
        assert isPalindrome("aba");
        assert isPalindrome("abca");
        assert !isPalindrome("abc");
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
        if (s.length() == 2 ) return true; // whether it is palindrome or it will be after deleting any char of the two.

        int i = 0;
        int j = s.length() - 1;

        while (i <= j) {
            char l = s.charAt(i);
            char r = s.charAt(j);

            if (l != r) break;
            i++;
            j--;
        }

        if (i > j) return true;

        // Now, substring s[i..j], including j is not a palindra.
        // Try to remove the left one
        int ii = i + 1;
        int jj = j;
        while (ii <= jj) {
            char l = s.charAt(ii);
            char r = s.charAt(jj);

            if (l != r) break;
            ii++;
            jj--;
        }
        if (ii > jj) return true;

        // Now try to remove the right one
        ii = i;
        jj = j - 1;
        while (ii <= jj) {
            char l = s.charAt(ii);
            char r = s.charAt(jj);

            if (l != r) break;
            ii++;
            jj--;
        }
        if (ii > jj) return true;


        return false;
    }

    /**
     * From Leetcode
     * @param s
     * @return
     */
    public boolean validPalindrome(String s) {
        int l = 0, r = s.length() - 1;
        while (l < r) {
            if (s.charAt(l) != s.charAt(r)) {
                return isPalindrome(s, l+1, r) || isPalindrome(s, l, r-1);
            }
            l++;
            r--;
        }
        return true;
    }
    public boolean isPalindrome(String s, int l, int r) {
        while (l < r) {
            if (s.charAt(l) != s.charAt(r)) {
                return false;
            }
            l++;
            r--;
        }
        return true;
    }
}
