package y.w.study.alg.array;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class RomanToInteger {
    @Test
    public void test() {
        assertEquals(1,         romanToInt("I"));
        assertEquals(2,         romanToInt("II"));
        assertEquals(3,         romanToInt("III"));
        assertEquals(4,         romanToInt("IV"));
        assertEquals(5,         romanToInt("V"));
        assertEquals(6,         romanToInt("VI"));
        assertEquals(7,         romanToInt("VII"));
        assertEquals(8,         romanToInt("VIII"));
        assertEquals(9,         romanToInt("IX"));
        assertEquals(10,        romanToInt("X"));
        assertEquals(40,        romanToInt("XL"));
        assertEquals(50,        romanToInt("L"));
        assertEquals(90,        romanToInt("XC"));
        assertEquals(100,       romanToInt("C"));
        assertEquals(400,       romanToInt("CD"));
        assertEquals(500,       romanToInt("D"));
        assertEquals(900,       romanToInt("CM"));
        assertEquals(1000,      romanToInt("M"));

        assertEquals(1904,      romanToInt("MCMIV"));
        assertEquals(58,        romanToInt("LVIII"));
        assertEquals(1994,      romanToInt("MCMXCIV"));
        assertEquals(0,         romanToInt("tMCMXCIV"));
    }

    public int romanToInt(String s) {
        if (s == null || s.isEmpty()) return 0;

        s = s.toUpperCase();

        Map<Character, Integer> lookup = new HashMap<>();
        lookup.put('I',        1);
        lookup.put('V',        5);
        lookup.put('X',        10);
        lookup.put('L',        50);
        lookup.put('C',        100);
        lookup.put('D',        500);
        lookup.put('M',        1000);

        try {
            int value = 0;
            for (int i = 0; i < s.length(); i++) {
                int v1 = lookup.get(s.charAt(i));

                if (i + 1 < s.length()) {
                    int v2 = lookup.get(s.charAt(i + 1));
                    if (v1 >= v2)
                        value = value + v1;
                    else {
                        value = value + v2 - v1;
                        i++;
                    }
                } else {
                    value = value + v1;
                }
            }

            return value;
        } catch (Exception e) {
            return 0;
        }
    }
}
