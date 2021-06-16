package y.w.study.alg.dp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class CanConstruct {
    @Test
    public void test() {
       assertTrue(canConstruct("abcdef", new String[]{"ab", "abc", "cd", "def", "abcd"}, new HashMap<>()));
       assertFalse(canConstruct("skateboard", new String[]{"bo", "rd", "ate", "t", "sk", "boar"}, new HashMap<>()));
       assertFalse(canConstruct("eeeeeeeeeeeeeeeeeeeeeeeeeeef", new String[]{"e", "ee", "eee", "eeee", "eeeee", "eeeeee"}, new HashMap<>()));
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
    public boolean canConstruct(String target, String[] wordBank, Map<String, Boolean> memo) {
        if (memo.containsKey(target)) return memo.get(target);

        if (target == null || target.isBlank()) return true;
        if (wordBank == null || wordBank.length <= 0) return false;

        for (String w : wordBank) {
            if (target.indexOf(w) == 0 && canConstruct(target.substring(w.length()), wordBank, memo)) { // time m to handle substring, space extra copy created
                memo.put(target, true);
                return true;
            }
        }

        memo.put(target, false);
        return false;
    }
}
