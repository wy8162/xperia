package x.y.stud.alg.recursion;

public class RevereString {
    public static String reverseString(String s) {
        if (s == null || s.length() <= 1) return s;

        char[] res = s.toCharArray();

        helper(0, s.length() -1, res);

        return new String(res);
    }

    private static void helper(int start, int end, char[] s) {
        if (start >= end) return;

        char c = s[start];
        s[start] = s[end];
        s[end] = c;

        helper(start + 1, end - 1, s);
    }

    public static void main(String[] args) {
        System.out.println(reverseString("123456789"));
    }
}

