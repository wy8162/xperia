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
public class LCS
{
    /**
     * Dynamic Programming from bottom up.
     *
     * @param S
     * @param T
     * @return LCS
     */
    public static String lcs(String S, String T)
    {
        int m = S.length();
        int n = T.length();
        int[][] LCS = new int[m+1][n+1]; // Row 0 and column 0 are not used.

        for (int i = 0; i <= m; i++)
        {
            for (int j = 0; j <= n; j++)
            {
                if (i == 0 || j == 0)
                {
                    LCS[i][j] = 0;
                }
                else if (S.charAt(i - 1) == T.charAt(j - 1))
                {
                    LCS[i][j] = 1 + LCS[i - 1][j - 1];
                }
                else
                {
                    LCS[i][j] = Math.max(LCS[i - 1][j], LCS[i][j - 1]);
                }
            }
        }

        return getLCS(LCS, S, T, m, n);
    }


    /**
     * @param LCS the matrix of LCS lengths, where row 0 and column 0 are not used.
     * @param S the String S
     * @param T the String T
     * @param m the length of S
     * @param n the length of T
     * @return
     */
    static String getLCS(int[][] LCS, String S, String T, int m, int n)
    {
        int sizeOfLCS = LCS[m][n];

        char[] lcs = new char[sizeOfLCS];

        int i = m;
        int j = n;
        while (i > 0 && j > 0)
        {
            if (S.charAt(i-1) == T.charAt(j-1))
            {
                lcs[sizeOfLCS-1] = S.charAt(i-1);
                i--; j--; sizeOfLCS--;
            }
            else if (LCS[i-1][j] > LCS[i][j-1])
                i--;
            else
                j--;
        }

        return new String(lcs);
    }
}
