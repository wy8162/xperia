package y.w.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * http://www-igm.univ-mlv.fr/~lecroq/string/node8.html#SECTION0080
 *
 * Knuth-Morris-Pratt algorithm
 *
 * Features:
 * <li>
 *     <ol>compare from left to right</ol>
 *     <ol>preprocessing phase in O(m) space and time complexity. Here m is the size of the search pattern</ol>
 *     <ol>searching phase in O(m+n) time complexity</ol>
 * </li>
 *
 * @author ywang
 * @date 10/31/2019
 */
public class KMPImpl
{
    /**
     * Pre-calculate the
     * @param pattern
     * @return table of prefix parameters
     */
    public static int[] preKmp(char[] pattern)
    {
        int[] kmpNext = new int[pattern.length + 1];

        int i = 0;
        int j = kmpNext[0] = -1;

        while (i < pattern.length)
        {
            while (j > -1 && pattern[i] != pattern[j])
                j = kmpNext[j];
            i++;
            j++;
            if (i < pattern.length && pattern[i] == pattern[j])
                kmpNext[i] = kmpNext[j];
            else
                kmpNext[i] = j;
        }

        return kmpNext;
    }

    /**
     * @param pattern the contents to be searched for
     * @param contents the contents to be searched from
     * @return list of positions of matches
     */
    public static List<Integer> search(String pattern, String contents )
    {
        if (pattern == null || contents == null)
            throw new IllegalArgumentException("Both pattern and string must not be null");

        char[] x = pattern.toCharArray();
        char[] y = contents.toCharArray();

        int i, j;

        /* Preprocessing */
        int[] kmpNext = preKmp(x);

        List<Integer> result = new ArrayList<>();

        /* Searching */
        i = j = 0;
        while (j < y.length) {
            while (i > -1 && x[i] != y[j])
                i = kmpNext[i];
            i++;
            j++;
            if (i >= x.length) {
                result.add(j - i);
                i = kmpNext[i];
            }
        }

        return result;
    }
}
