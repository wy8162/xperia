package y.w.algorithm;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * LCSTest
 *
 * @author ywang
 * @date 9/18/2019
 */
public class LCSTest
{
    @Test
    public void testLCS()
    {
        String lcs = LCS.lcs("ABAZDC", "BACBAD");
        assertTrue(lcs.equals("ABAD"));

        lcs = LCS.lcs("WYANG", "AWYXYZ");
        assertTrue(lcs.equals("WY"));
    }

    @Test
    public void testLCSRecusive()
    {
        LCSRecursive lcs = new LCSRecursive();

        String lcsString = lcs.lcs("ABAZDC", "BACBAD");
        assertTrue(lcsString.equals("ABAD"));

        lcsString = lcs.lcs("WYANG", "AWYXYZ");
        assertTrue(lcsString.equals("WY"));
    }
}
