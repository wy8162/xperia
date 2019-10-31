package y.w.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * KMP implementation based CMU https://www.cs.cmu.edu/afs/cs/academic/class/15451-s15/LectureNotes/
 *
 * Also: https://web.stanford.edu/class/cs97si/10-string-algorithms.pdf
 * Also: https://www.ics.uci.edu/~eppstein/161/960227.html
 *
 * Knuth-Morris-Pratt (KMP) Algorithm
 * <li>
 *     <ol>Linear time algorithm that solves the string matching.</ol>
 *     <ol>Need to preprocess pattern in O(m) time. It's purpose is to skip comparisions
 *     done previously.</ol>
 * </li>
 *
 * The pre-processing is to build a table PI: PI[i] is the largest number smaller than i such that
 * P[1]...P[PI[i]] is a suffix of P[1]...P[i].
 *
 * I.e.
 *  i   1 2 3 4 5 6 7 8 9 10  -> index
 *  P   a b a b a b a b c a   -> pattern
 *  PI  0 0 1 2 3 4 5 6 0 1   -> Table
 *
 * Here, for example:
 * <p>
 *    PI[6] = 4, P[1..4]=abab is a suffix of P[1...6]=ababab
 *
 * <p>
 *    PI[1] = 0, there is no suffix for P[1].
 *
 * <p>
 *    PI[10] = 1, P[1]=a which is suffix of P[1..10]
 *
 * <p>
 *    PI[4] = 2, P[1..2]=ab is suffix of P[1..4]=abab
 *
 * Let's walk through a specific case.
 * <pre>
 *         i - points to position in T
 *         12345678901234567890123
 *     T = ABC ABCDAB ABCDABCDABDE
 *     P = ABCDABD
 *         1234567
 *         k - points to the position
 *     PI= (0,0,0,0,1,2,0)
 *
 * </pre>
 *
 */
public class KMP
{
    /**
     * Computes the table of prefix strings.
     *
     * @param pattern
     * @return table of prefix strings
     */
    public static int[] computeLPS(char[] pattern)
    {
        int[] PI = new int[pattern.length];

        int length = 0;
        PI[0] = 0; // always zero in the first place

        for (int i = 1; i < pattern.length; i++) {
            if (pattern[i] == pattern[length]) {
                length++;
                PI[i] = length;
            } else {
                if (length > 0) {
                    length = PI[length - 1];
                    i--;
                } else {
                    PI[i] = 0;
                }
            }
        }

        return PI;
    }

    public static List<Integer> search(String pattern, String contents)
    {
        if (pattern == null || contents == null)
            throw new IllegalArgumentException("Both pattern and string must not be null");

        char[] P = pattern.toCharArray();
        char[] T = contents.toCharArray();

        int[] PI = computeLPS(P);

        List<Integer> result = new ArrayList<>();

        int m = 0; // position in T being tested currently.
        int j = 0; // position in P being tested currently.
        while (m < T.length)
        {
            // Try to match T[m+j] == P[j]
            if (T[m] == P[j])
            {
                // Matched and proceed to next char
                m++;
                j++;

                // Check if the whole pattern is matched.
                if (j == P.length)
                {
                    // The pattern is fully matched.
                    // Add the position to the result list
                    result.add(m - j);

                    j = PI[j - 1];

                    continue;
                }
            }

            if (m < T.length && T[m] != P[j]) {
                // Not a full match and compare the P[j] and T[m]
                // m and j may have been adjusted before.
                if (j != 0)
                    j = PI[j - 1];
                else
                    m++;
            }
        }

        return result;
    }
}
