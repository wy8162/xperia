package y.w.study.alg.recursion;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class BacktrackNQueen {
    private int size;

    public int totalNQueens(int n) {
        this.size = n;

        return backtrack(0, new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    /**
     * https://leetcode.com/problems/n-queens-ii/solution/
     *
     * @param row
     * @param diagonals
     * @param antiDiagonals
     * @param cols
     * @return
     */
    private int backtrack(int row, Set<Integer> diagonals, Set<Integer> antiDiagonals, Set<Integer> cols) {
        // Base Case - N queens have been placed
        if (row == size) {
            return 1;
        }

        int solutions = 0;
        for (int col = 0; col < size; col++) {
            int currentDiagonal = row - col;
            int currentAntiDiagonal = row + col;

            // Check if queen is placeable
            if (cols.contains(col) || diagonals.contains(currentDiagonal) || antiDiagonals.contains(currentAntiDiagonal))
                continue;

            // Add the queen to the board
            cols.add(col);
            diagonals.add(currentDiagonal);
            antiDiagonals.add(currentAntiDiagonal);

            // Move on to the next row with the updated board state
            solutions += backtrack(row + 1, diagonals, antiDiagonals, cols);

            // Remove the queen from the board since we already explored all the valid paths.
            cols.remove(col);
            diagonals.remove(currentDiagonal);
            antiDiagonals.remove(currentAntiDiagonal);
        }

        return solutions;
    }

    /**
     * https://jeffe.cs.illinois.edu/teaching/algorithms/book/02-backtracking.pdf
     *
     * Gauss and Laquiere's backtracking algorithms
     *
     * @param Q
     * @param r
     * @return
     */
    private int placeQueens(int[]Q, int r) {
        if (r == Q.length) {
            System.out.println("\nSolution: ");
            for (int i = 0; i < Q.length; i++) {
                System.out.print(String.format("(%d, %d)", i, Q[i]));
            }
            return 0;
        }

        for (int j = 0; j < Q.length; j++) {
            boolean legal = true;
            for (int i = 0; i <= r - 1; i++) {
                if (Q[i] == j || Q[i] == j + r - i || Q[i] == j - r + i)
                    legal = false;
            }

            if (legal) {
                Q[r] = j;
                placeQueens(Q, r + 1);
            }
        }

        return 0;
    }

    @Test
    public void test() {
        System.out.println(String.format("n = 4, expect 2, actual %d", totalNQueens(4)));
        System.out.println(String.format("n = 8, expect 92, actual %d", totalNQueens(8)));
    }

    @Test
    public void test2() {
        placeQueens(new int[4], 0);
        //placeQueens(new int[8], 0);
    }
}
