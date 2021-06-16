package y.w.study.alg.array;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Given two binary strings a and b, return their sum as a binary string.
 */
public class AddBinary {
    @Test
    public void test() {
        assertEquals("", addBinary("", ""));
        assertEquals("1", addBinary("1", ""));
        assertEquals("11", addBinary("00", "11"));
        assertEquals("100", addBinary("11", "1"));
        assertEquals("10101", addBinary("1010", "1011"));
    }

    /**
     * n = a size
     * m = b size
     *
     * Time: O(n + m)
     *
     * @param a
     * @param b
     * @return
     */
    public String addBinary(String a, String b) {
        if (a == null || b == null) return "";

        StringBuilder sb = new StringBuilder();

        String l = a, s = b;
        if (a.length() < b.length()) {
            l = b;
            s = a;
        }

        int carry = 0;
        for (int i = 0; i < s.length(); i++) { // Time: m
            int lc = l.charAt(l.length() - 1 - i) - '0';
            int ls = s.charAt(s.length() - 1 - i) - '0';
            int result = lc + ls + carry;
            carry = result / 2;

            sb.append(result % 2);
        }

        for (int i = s.length(); i < l.length() ; i++) { // Time: n - m
            int lc = l.charAt(l.length() - 1 - i) - '0';
            int result = lc + carry;

            carry = result / 2;
            sb.append(result % 2);
        }

        if (carry > 0) {
            sb.append(carry);
        }

        return sb.reverse().toString(); // Time: O(n)
    }

   public String addBinaryLeetCode(String a, String b) {
        int l1 = a.length();
        int l2 = b.length();

        StringBuilder sb = new StringBuilder();

        int i=l1-1,j=l2-1, carry=0;

        while(i>=0 || j>=0){
            int sum = carry;

            if(i>=0) sum+=a.charAt(i--) - '0';
            if(j>=0) sum+=b.charAt(j--) - '0';

            sb.insert(0,sum%2);
            carry= sum/2;
        }

        if(carry!=0) sb.insert(0,carry);

        return sb.toString();
    }
}
