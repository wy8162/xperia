package y.w.study.alg.recursion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class BacktrackStringCombination {
    @Test
    public void test() {
        List<String> result = generateCombinations("23");
    }

    private List<String> combinations = new ArrayList<>();
    private StringBuilder sb;

    private Map<Character, String> letters = Map.of(
        '2', "abc", '3', "def", '4', "ghi", '5', "jkl",
        '6', "mno", '7', "pqrs", '8', "tuv", '9', "wxyz");

    private String digits;

    public List<String> generateCombinations(String digits) {
        if (digits == null || digits.length() == 0) return combinations;

        this.digits = digits;
        this.sb = new StringBuilder();

        backtracking(0);

        return combinations;
    }

    private void backtracking(int i) {
        if (i == digits.length()) {
            combinations.add(sb.toString());
            return;
        }

        for (char c : letters.get(digits.charAt(i)).toCharArray()) {
            sb.append(c);
            backtracking(i + 1); // process the next char
            sb.deleteCharAt(sb.length() - 1); // Backtrack - removing the last char just appended before.
        }
    }
}
