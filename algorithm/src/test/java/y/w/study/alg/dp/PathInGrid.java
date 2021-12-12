package y.w.study.alg.dp;

/**
 * Find a path from upper left corner to the lower right corner of an n x n grid A, with
 * the restriction that we may only move down and right. Each cell contains an integer
 * and the path should be constructed so that the sum of te values along the path is
 * as large as possible.
 *
 * Algorithm: let sum(i,j) be the sum when reach cell (i,j).
 *
 * sum(i,j) = max(sum(i - 1, j), sum(i, j - 1)) + A(i,j)
 */
public class PathInGrid {

}
