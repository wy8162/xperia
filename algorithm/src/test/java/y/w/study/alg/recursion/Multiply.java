package y.w.stud.alg.recursion;

public class Multiply {
    public static void main(String[] args) {
        System.out.println(multiply(20, 13));
        System.out.println(multiply(9, 3));
        System.out.println(multiply(9, 9));
    }

    /**
     * https://www.geeksforgeeks.org/multiplication-two-numbers-shift-operator/?ref=rp
     *
     */
    public static int multiply(int n, int m) {
        int ans = 0, count = 0;

        while (m > 0) {
            if (m % 2 == 1)
                ans += n << count;

            count++;

            m = m >> 1;
        }

        return ans;
    }
}
