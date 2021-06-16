package y.w.study.alg.dp;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class CountConstruct {
    @Test
    public void test() {
       assertEquals(canConstruct("purple", new String[]{"purp", "p", "ur", "le", "purpl"}, new HashMap<>()), 2);
       assertEquals(canConstruct("skateboard", new String[]{"bo", "rd", "ate", "t", "sk", "boar"}, new HashMap<>()), 0);
       assertEquals(canConstruct("eeeeeeeeeeeeeeeeeeeeeeeeeeef", new String[]{"e", "ee", "eee", "eeee", "eeeee", "eeeeee"}, new HashMap<>()), 0);
    }

    /**
     * m = size of target, n = size of wordBank
     * Brute Force: time O(n^m * m), space O(m^2)
     * Memoized: time(n * m^2), space(n * m)
     *
     * @param target
     * @param wordBank
     * @param memo
     * @return
     */
    public int canConstruct(String target, String[] wordBank, Map<String, Integer> memo) {
        if (memo.containsKey(target)) return memo.get(target);

        if (target == null || target.isBlank()) return 1;
        if (wordBank == null || wordBank.length <= 0) return 0;

        int totalCount = 0;

        for (String w : wordBank) {
            if (target.indexOf(w) == 0) {
                totalCount += canConstruct(target.substring(w.length()), wordBank, memo);// time m to handle substring, space extra copy created
            }
        }

        memo.put(target, totalCount);
        return totalCount;
    }
}
