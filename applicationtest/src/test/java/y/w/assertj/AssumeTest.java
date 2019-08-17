package y.w.assertj;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.Assert.fail;

/**
 * AssumeTest
 */
public class AssumeTest
{
    @Test
    public void assumeIsWrong()
    {
        String s1 = "wyang";
        String s2 = "wyang";
        assumeThat(s1).isEqualTo(s2); // This is obviously wrong
        assertThat(s1).isEqualTo(s2);
    }
}
