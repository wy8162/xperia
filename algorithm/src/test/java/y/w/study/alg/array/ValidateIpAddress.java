package y.w.study.alg.array;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ValidateIpAddress {
    @Test
    public void test() {
        assertEquals(validIPAddress("01.01.01.01"), "Neither");
        assertEquals(validIPAddress("12.33.4."), "Neither");
        assertEquals(validIPAddress("12..33.4"), "Neither");
        assertEquals(validIPAddress("172.16.254.1"), "IPv4");
        assertEquals(validIPAddress("172.16.254.1."), "Neither");
        assertEquals(validIPAddress("2001:0db8:85a3:0:0:8A2E:0370:7334"), "IPv6");
        assertEquals(validIPAddress("256.256.256.256"), "Neither");
        assertEquals(validIPAddress("2001:0db8:85a3:0:0:8A2E:0370:7334:"), "Neither");
        assertEquals(validIPAddress("i1e1.4.5.6"), "Neither");
    }

    public String validIPAddress(String IP) {
        final String NEITHER = "Neither";
        final String IPv4 = "IPv4";
        final String IPv6 = "IPv6";
        final char IPv4_SEPARATOR = '.';
        final char IPv6_SEPARATOR = ':';

        if (IP == null) return NEITHER;

        if (validCharSet(IP, "0123456789", IPv4_SEPARATOR, 4)) {
            if (validIPv4(IP)) return IPv4;
        } else if (validCharSet(IP, "01234567890ABCDEFabcdef", IPv6_SEPARATOR, 8)) {
            if (validIPv6(IP)) return IPv6;
        }

        return NEITHER;
    }

    private boolean validCharSet(String IP, String validCharSet, char separator, int expectedNums) {
        int countSeps = 0;
        int countNums = 1;
        int numDigits = 0;

        for (int i = 0; i < IP.length(); i++) {
            char c = IP.charAt(i);
            if (c == separator) {
                countSeps++;


                if (numDigits == 0) return false;

                numDigits = 0;
            }
            else if ((validCharSet.indexOf(c) >= 0)) numDigits++;
            else return false;
        }

        return numDigits > 0 && countNums == expectedNums && countSeps == expectedNums - 1;
    }

    private boolean validIPv4(String IP) {
        String[] segs = IP.split("\\.");

        for (String s : segs) {
            // Check leading zero
            if (s.charAt(0) == '0' && s.length() > 1) return false;

            // Check the value range
            if (s.length() > 3) return false;
            int num = Integer.parseInt(s);

            if (num > 255) return false;
        }

        return true;
    }

    private boolean validIPv6(String IP) {
        String[] segs = IP.split(":");

        if (segs.length != 8) return false;

        for (String s : segs)
            if (s.length() > 4) return false;

        return true;
    }
}
