package y.w.study.alg.array;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MultiplyStrings {
    @Test
    public void test() {
        assertEquals("0",     multiplySigned("", ""));
        assertEquals("0",     multiplySigned("1", "0"));
        assertEquals("2",     multiplySigned("1", "2"));
        assertEquals("24",    multiplySigned("2", "12"));
        assertEquals("100",   multiplySigned("10", "10"));
        assertEquals("6",     multiplySigned("2", "3"));
        assertEquals("-6",    multiplySigned("-2", "3"));
        assertEquals("6",     multiplySigned("-2", "-3"));
        assertEquals("56088", multiplySigned("123", "456"));
        assertEquals("2118187521397235888154583183918321221520083884298838480662480", multiply("1235421415454545454545454544", "1714546546546545454544548544544545"));

        assertEquals("0",     multiply("", ""));
        assertEquals("0",     multiply("1", "0"));
        assertEquals("2",     multiply("1", "2"));
        assertEquals("24",    multiply("2", "12"));
        assertEquals("100",   multiply("10", "10"));
        assertEquals("6",     multiply("2", "3"));
        assertEquals("56088", multiply("123", "456"));
        assertEquals("2118187521397235888154583183918321221520083884298838480662480", multiply("1235421415454545454545454544", "1714546546546545454544548544544545"));

        assertEquals("0",     multiply2("", ""));
        assertEquals("0",     multiply2("1", "0"));
        assertEquals("2",     multiply2("1", "2"));
        assertEquals("24",    multiply2("2", "12"));
        assertEquals("100",   multiply2("10", "10"));
        assertEquals("6",     multiply2("2", "3"));
        assertEquals("56088", multiply2("123", "456"));
        assertEquals("2118187521397235888154583183918321221520083884298838480662480", multiply("1235421415454545454545454544", "1714546546546545454544548544544545"));

    }

    /**
     * n - size of num1
     * m - size of num2
     *
     * Time: O(n * m)
     * Space: O(n+m)
     *
     * @param num1
     * @param num2
     * @return
     */
    public String multiply(String num1, String num2) {
        if (num1 == null || num1.isEmpty() || num2 == null || num2.isEmpty()) return "0";

        // Stores computation result in reverse order from 0 to n.
        // Initialized to 0 by Java
        int[] calcResult = new int[num1.length() + num2.length()]; // Time: O(1), Space: O(n+m)

        for (int j = 0; j < num2.length(); j++) { // Time: O(m)
            int carry = 0;
            int c2 = num2.charAt(num2.length() - 1 - j) - '0';
            if (c2 == 0 ) {
                continue; // increase j++ and do nothing.
            } else if (c2 > 0 && c2 <= 9) {
                int carryPos = 0;
                for (int i = 0; i < num1.length(); i++) { // Time: O(n)
                    int c1 = num1.charAt(num1.length() - 1 - i) - '0';

                    if (c1 < 0 || c1 > 9) return "0"; // Invalid character.

                    int prod = c1 * c2 + calcResult[j + i] + carry;

                    carry = prod / 10;

                    calcResult[j + i] = prod % 10;
                    carryPos = i;
                }
                if (carry > 0)
                    calcResult[j + carryPos + 1] += carry;
            } else {
                return "0"; // Invalid non-digit chars. Return 0.
            }
        }

        int i = calcResult.length - 1;
        while (i >= 0 && calcResult[i] == 0)
            i--; // Skip zeros

        if (i < 0) return "0";

        StringBuilder sb = new StringBuilder();

        while (i >= 0)
            sb.append(calcResult[i--]);

        return sb.toString();
    }


    /**
     * n - size of num1
     * m - size of num2
     *
     * Time: O(n * m)
     * Space: O(n+m)
     *
     * @param num1
     * @param num2
     * @return
     */
    public String multiplySigned(String num1, String num2) {
        if (num1 == null || num1.isEmpty() || num2 == null || num2.isEmpty()) return "0";

        // Stores computation result in reverse order from 0 to n.
        // Initialized to 0 by Java
        int[] calcResult = new int[num1.length() + num2.length()]; // Time: O(1), Space: O(n+m)

        // Time: O(1), Space: O(1)
        int num1Sign = 1;
        int skilSign1 = 0;
        if (num1.charAt(0) == '-' || num1.charAt(0) == '+') {
            num1Sign = num1.charAt(0) == '-' ? -1 : 1;
            skilSign1++;

            if (num1.length() == 1) return "0";
        }

        // Time: O(1), Space: O(1)
        int num2Sign = 1;
        int skipSign2 = 0;
       if (num2.charAt(0) == '-' || num2.charAt(0) == '+') {
            num2Sign = num2.charAt(0) == '-' ? -1 : 1;
            skipSign2++;

            if (num2.length() == 1) return "0";
        }

        for (int j = 0; j < num2.length() - skipSign2; j++) { // Time: O(m)
            int carry = 0;
            int c2 = num2.charAt(num2.length() - 1 - j) - '0';
            if (c2 == 0 ) {
                continue; // increase j++ and do nothing.
            } else if (c2 > 0 && c2 <= 9) {
                int carryPos = 0;
                for (int i = 0; i < num1.length() - skilSign1; i++) { // Time: O(n)
                    int c1 = num1.charAt(num1.length() - 1 - i) - '0';

                    if (c1 < 0 || c1 > 9) return "0"; // Invalid character.

                    int prod = c1 * c2 + calcResult[j + i] + carry;

                    carry = prod / 10;

                    calcResult[j + i] = prod % 10;
                    carryPos = i;
                }
                if (carry > 0)
                    calcResult[j + carryPos + 1] += carry;
            } else {
                return "0"; // Invalid non-digit chars. Return 0.
            }
        }

        int i = calcResult.length - 1;
        while (i >= 0 && calcResult[i] == 0)
            i--; // Skip zeros

        if (i < 0) return "0";

        StringBuilder sb = new StringBuilder();
        if (num1Sign * num2Sign < 0)
            sb.append("-");

        while (i >= 0)
            sb.append(calcResult[i--]);

        return sb.toString();
    }

    /**
     * From Leetcode. It can't handle signed numbers.
     *
     * Better performance.
     *
     * @param num1
     * @param num2
     * @return
     */
    public String multiply2(String num1, String num2) {
        int n1 = num1.length();
        int n2 = num2.length();
        int[] prodcuts = new int[n1+n2];
        for(int i = n1-1 ; i >= 0 ; i--){
            for(int j = n2-1 ; j >= 0; j--){
                int d1 = num1.charAt(i) -'0';
                int d2 = num2.charAt(j)- '0';
                prodcuts[i+j+1] += d1 * d2;
            }
        }
        int carry =0;
        for(int i = prodcuts.length -1; i >=0 ; i--){
            int tmp = carry + prodcuts[i];
            prodcuts[i] = tmp % 10;
            carry = tmp/10;
        }

        StringBuilder sb = new StringBuilder();

        for(int digit : prodcuts){
            sb.append(digit);
        }

        while(sb.length() >0 && sb.charAt(0) =='0'){
            sb.deleteCharAt(0);
        }

        return sb.length() ==0 ? "0" : sb.toString();
    }
}
