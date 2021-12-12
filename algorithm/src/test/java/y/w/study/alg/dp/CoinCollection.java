package y.w.study.alg.dp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Several coins are placed in cells of an n x m board, no more than one coin per cell.
 * A robot, located in the upper left cell of the board, needs to collect as many of the
 * coins as possible and bring them to the bottom right cell. One each cell, the robot
 * can move either one cell to the right or one cell down from its current location.
 *
 * Design an algorithm to find the maximum number of coins the robot can collect and a path
 * it needs to follow to do this.
 *
 * Let F(i,j) be the largest number of coins the robot can collect and bring to the cell
 * (i,j) in the ith row and jth column. It can reach this cell either from adjacent cell
 * (i-1,j) or (i,j-1). The largest number of coins that can be brought to these cells are
 * F(i-1,j) and F(i,j-1) respectively.
 *
 * F(i,j) = max {F(i-1,j), F(i,j-1)} + cij for 1 <= i <= n, 1 <= j <= m
 * F(0,j) = 0 for j=1..m, F(i,0) = 0 for i=1..n
 */
public class CoinCollection {
    public static int coinCollectionRecursive(int[][] C, int i, int j) {
        if (i >= C.length || j >= C[0].length) return 0;

        int d = coinCollectionRecursive(C, i + 1, j);
        int r = coinCollectionRecursive(C, i, j + 1);

        return C[i][j] + Math.max(r, d);
    }

    public static int coinCollectionDP(int[][]C) {
        int[][]F = new int[C.length + 1][C[0].length + 1];

        F[0][0] = C[0][0];
        for (int j = 1; j < C[0].length; j++) F[0][j] = F[0][j - 1] + C[0][j];
        for (int i = 1; i < C.length; i++) F[i][0] = F[i -1][0] + C[i][0];
        for (int i = 1; i < C.length; i++) {
            for (int j = 1; j < C[0].length; j++) {
                F[i][j] = Math.max(F[i-1][j], F[i][j -1]) + C[i][j];
            }
        }

        return F[C.length - 1][C[0].length - 1];
    }

    @Test
    public void t() {
        int[][]C = new int[][] {
            {0,0,0,0,1,0},
            {0,1,0,1,0,0},
            {0,0,0,1,0,1},
            {0,0,1,0,0,1},
            {1,0,0,0,1,0},
        };


        assertThat(coinCollectionRecursive(C, 0, 0)).isEqualTo(5);
        assertThat(coinCollectionDP(C)).isEqualTo(5);
    }
}
