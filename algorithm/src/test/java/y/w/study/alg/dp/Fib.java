package y.w.study.alg.dp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Fib {
    @Test
    public void test() {
        assertEquals(brute(0), topDown(0, new int[12]));
        assertEquals(brute(1), topDown(1, new int[12]));
        assertEquals(brute(2), topDown(2, new int[12]));
        assertEquals(brute(3), topDown(3, new int[12]));
        assertEquals(brute(4), topDown(4, new int[12]));
        assertEquals(brute(5), topDown(5, new int[12]));
        assertEquals(brute(6), topDown(6, new int[12]));
        assertEquals(brute(12), topDown(12, new int[13]));

        assertEquals(brute(0), bottomUp(0, new int[12]));
        assertEquals(brute(1), bottomUp(1, new int[12]));
        assertEquals(brute(2), bottomUp(2, new int[12]));
        assertEquals(brute(3), bottomUp(3, new int[12]));
        assertEquals(brute(4), bottomUp(4, new int[12]));
        assertEquals(brute(5), bottomUp(5, new int[12]));
        assertEquals(brute(6), bottomUp(6, new int[12]));
        assertEquals(brute(12), bottomUp(12, new int[13]));
    }

    public int brute(int n) {
        if (n < 2) return n;

        return brute(n - 1) + brute(n - 2);
    }

    public int topDown(int n, int[] cache) {
        if (n < 2) return n;

        if (cache[n] != 0)  return cache[n];

        cache[n] = topDown(n - 1, cache) + topDown(n - 2, cache);

        return cache[n];
    }

    // non-recursive. Tabulation.
    public int bottomUp(int n, int[] cache) {
        if (n == 0) return n;
        cache[0] = 0;
        cache[1] = 1;

        for (int i = 2; i <= n; i++)
            cache[i] = cache[i - 1] + cache[i - 2];

        return cache[n];
    }
}
