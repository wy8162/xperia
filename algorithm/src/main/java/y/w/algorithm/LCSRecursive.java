package y.w.algorithm;

/**
 * CMU Algorithm Analysis and Design.
 *
 * Let LCS[i,j] be the length of the LCS of S[0..i-1] with T[0..j-1]:
 *
 * Case 1: if S[i] != T[j], then, the desired subsequence has to ignore one of S[i] or T[j]
 * so we have:
 *      LCS[i; j] = max(LCS[i 􀀀 1; j]; LCS[i; j 􀀀 1]):
 * Case 2: if S[i] = T[j], then the LCS of S[0..i-1] and T[0..j-1] might match them up.
 *      LCS[i; j] = 1 + LCS[i 􀀀 1; j 􀀀 1]:
 *
 */
public class LCSRecursive
{
    int[][] lcs;

    /**
     * Dynamic Programming from bottom up recursively.
     */
    public String lcs(String S, String T)
    {
        lcs = new int[S.length()+1][T.length()+1];

        calculateLCS(S, S.length(), T, T.length());

        return LCS.getLCS(lcs, S, T, S.length(), T.length());
    }

    private int calculateLCS(String S, int n, String T, int m)
    {
        int result;

        if (n == 0 || m == 0) return 0;
        if (lcs[n][m] != 0) return lcs[n][m]; // re-use the value memoized
        if (S.charAt(n-1) == T.charAt(m-1)) result = 1 + calculateLCS(S, n - 1, T, m - 1);
        else result = Math.max(calculateLCS(S, n - 1, T, m), calculateLCS(S, n, T, m - 1));
        lcs[n][m] = result;

        return result;
    }
}