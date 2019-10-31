package y.w.algorithm;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class KMPTest
{
    /**
     * Test case from http://www-igm.univ-mlv.fr/~lecroq/string/node8.html#SECTION0080
     */
    @Test
    public void testPreKMP_Case1()
    {
        char[] x = "GCAGAGAG".toCharArray();
        int[] kmpNext = KMPImpl.preKmp(x);

        assertThat(kmpNext).containsExactly(new int[]{-1, 0, 0, -1, 1, -1, 1, -1, 1});
    }

    @Test
    public void testKMP_Matching1()
    {
        List<Integer> r = KMPImpl.search("abc", "1234abc567abc890");

        assertThat(r.size()).isEqualTo(2);
        assertThat(r).contains(4, 10);

        r = KMPImpl.search("AAAB", "AAAABAAAAABBBAAAAB");
        assertThat(r.size()).isEqualTo(3);
        assertThat(r).contains(1, 7, 14);

        r = KMPImpl.search("ababb", "aaaabaaaaababbaaab");
        assertThat(r.size()).isEqualTo(1);
        assertThat(r).contains(9);
    }

    /**
     * CMU https://www.cs.cmu.edu/afs/cs/academic/class/15451-s15/LectureNotes/
     */
    @Test
    public void testKMP2_Case1()
    {
        char[] x = "ababb".toCharArray();
        int[] PI = KMP.computeLPS(x);

        assertThat(PI).containsExactly(new int[]{0, 0, 1, 2, 0});

        PI = KMP.computeLPS("ababababca".toCharArray());
        assertThat(PI).containsExactly(new int[]{0, 0, 1, 2, 3, 4, 5, 6, 0, 1});

        PI = KMP.computeLPS("ABCDABD".toCharArray());
        assertThat(PI).containsExactly(new int[]{0,0,0,0,1,2,0});
    }

    @Test
    public void testKMP2_Matching1()
    {
        List<Integer> r = KMP.search("abc", "1234abc567abc890");

        assertThat(r.size()).isEqualTo(2);
        assertThat(r).contains(4, 10);

        r = KMP.search("AAAB", "AAAABAAAAABBBAAAAB");
        assertThat(r.size()).isEqualTo(3);
        assertThat(r).contains(1, 7, 14);

        r = KMP.search("ababb", "aaaabaaaaababbaaab");
        assertThat(r.size()).isEqualTo(1);
        assertThat(r).contains(9);
    }
}
