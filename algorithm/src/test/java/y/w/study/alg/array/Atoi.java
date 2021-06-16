package y.w.study.alg.array;

import static org.junit.Assert.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;

public class Atoi {
    @Test
    public void test() {
        assertEquals(atoi("42"), 42);
        assertEquals(atoi("-"), 0);
        assertEquals(atoi("+"), 0);
        assertEquals(atoi("+-"), 0);
        assertEquals(atoi("-+"), 0);
        assertEquals(atoi("    -42"), -42);
        assertEquals(atoi("  + 42"), 0);
        assertEquals(atoi("4193 with words"), 4193);
        assertEquals(atoi("words and 987"), 0);
        assertEquals(atoi("-91283472332"), -2147483648);
        assertEquals(atoi("9223372036854775808"), 2147483647);
        assertEquals(atoi("10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000522545459"), 2147483647);
        assertEquals(atoi("3.14159"), 3);


        assertEquals(atoi2("42"), 42);
        assertEquals(atoi2("-"), 0);
        assertEquals(atoi2("+"), 0);
        assertEquals(atoi2("+-"), 0);
        assertEquals(atoi2("-+"), 0);
        assertEquals(atoi2("    -42"), -42);
        assertEquals(atoi2("  + 42"), 0);
        assertEquals(atoi2("4193 with words"), 4193);
        assertEquals(atoi2("words and 987"), 0);
        assertEquals(atoi2("-91283472332"), -2147483648);
        assertEquals(atoi2("9223372036854775808"), 2147483647);
        assertEquals(atoi2("10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000522545459"), 2147483647);
        assertEquals(atoi2("3.14159"), 3);
    }

    public int atoi(String s) {
        if (s == null || s.isBlank()) return 0;

        int sign = 1;
        boolean signFound = false;

        int i = 0;

        // Skip spaces. Terminate early if there is non-space, stop if encounters '-', '+' or digits.
        for (; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == ' ' && !signFound) continue;

            if (Character.isDigit(c))
                break;

            if (c == '+' || c == '-') {
                sign = c == '-' ? -1 : 1;
                i++;
                break;
            }

            return 0;
        }

        // Skipp leading zeros. Terminate if non-digit
        for (; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isDigit(c)) {
                if (c == '0')
                    continue;
                else
                    break;
            }

            // Found non-digit again.
            return 0;
        }

        if (i >= s.length()) return 0;

        long value = 0;

        for (; i < s.length(); i++) {
            int c = s.charAt(i);

            if (!Character.isDigit(c)) {
                // Ignore anything after this non-digit chars.
                break;
            }

            value = value * 10 + (c - '0');

            if (value >= Integer.MAX_VALUE)
                break;
        }

        value = value * sign;

        value = value > Integer.MAX_VALUE ? Integer.MAX_VALUE :
                value < Integer.MIN_VALUE ? Integer.MIN_VALUE : value;

        return (int)(value);
    }

    public int atoi2(String s) {
        if (s == null) return 0;
        s = s.trim();

        if (s.isEmpty()) return 0;

        int i = 0;
        int sign = 1;

        if (s.charAt(0) == '-' || s.charAt(0) == '+') {
            i = 1;
            sign = -1;
        }

        int value = 0;
        for (; i < s.length(); i++) {
            int c = s.charAt(i) - '0';

            if (c < 0 || c > 9) break;
            if (value > Integer.MAX_VALUE / 10) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }

            value = value * 10 + c;
        }

        return value * sign;
    }

    @Test
    public void test3() {
        assertEquals(atoi3("42"), 42);
        assertEquals(atoi3("-"), 0);
        assertEquals(atoi3("+"), 0);
        assertEquals(atoi3("+-"), 0);
        assertEquals(atoi3("-+"), 0);
        assertEquals(atoi3("    -42"), -42);
        assertEquals(atoi3("  + 42"), 0);
        assertEquals(atoi3("4193 with words"), 4193);
        assertEquals(atoi3("words and 987"), 0);
        assertEquals(atoi3("-91283472332"), -2147483648);
        assertEquals(atoi3("9223372036854775808"), 2147483647);
        assertEquals(atoi3("10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000522545459"), 2147483647);
        assertEquals(atoi3("3.14159"), 3);
    }

    public int atoi3(String s) {
        Pattern pattern = Pattern.compile("^ *(?<num>[-+]?[0-9]+).*$");

        Matcher m = pattern.matcher(s);

        if (!m.matches() || m.groupCount() != 1) return 0;

        s = m.group("num");

        int sign = 1;
        int pos = 0;

        if (s.charAt(0) == '-' || s.charAt(0) == '+') {
            sign = s.charAt(0) == '-' ? -1 : 1;
            pos = 1;
        }

        int value = 0;
        for (; pos < s.length(); pos++) {
            int c = s.charAt(pos) - '0';

            if (c < 0 || c > 9)
                break;
            if (value > Integer.MAX_VALUE / 10) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }

            value = value * 10 + c;
        }

        return value * sign;
    }
}
