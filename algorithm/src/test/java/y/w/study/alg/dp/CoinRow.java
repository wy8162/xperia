package y.w.study.alg.dp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.extractProperty;
import static org.assertj.core.api.Assertions.in;

import org.junit.Test;

/**
 * There is a row of n coins whose values are some positive integers c1, c2, ..., cn,
 * not necessarily distinct. The goal is to pick up the maximum amount of money subject
 * to the constraint that no two coins adjacent in the initial row can be picked up.
 *
 * Algorithm (DP):
 * - F(n) = max {cn + F(n-2), F(n-1)}, for n > 1
 * - F(0) = 0, F(1) c1
 *
 * where
 *   cn + F(n-2) - the amount include the current cn
 *   F(n-1)      - the amount exclude the current cn
 *
 */
public class CoinRow {
    public static int coinRowDP(int[] C) {
        int[]F = new int[C.length + 1];
        F[0] = 0;
        F[1] = C[0];

        for (int i = 2; i <= C.length; i++) {
            F[i] = Math.max(C[i - 1] + F[i - 2], F[i - 1]); // Note: here, C starts from 0.
        }

        return F[C.length];
    }

    public static int coinRowRecursive(int[] C, int i) {
        if (i >= C.length) return 0;

        int includeAmount = C[i] + coinRowRecursive(C, i + 2);
        int withoutAmount = coinRowRecursive(C, i + 1);

        return Math.max(includeAmount, withoutAmount);
    }

    @Test
    public void t() {
        assertThat(coinRowDP(new int[]{5,1,2,10,6,2})).isEqualTo(17);
        assertThat(coinRowRecursive(new int[]{5,1,2,10,6,2}, 0)).isEqualTo(17);
    }
}
