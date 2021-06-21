package y.w.study.alg.math;

/**
 * Computes a^b.
 *
 * Time: O(log(b))
 * Space: O(log(b))
 */
public class ComputePowerOfNumber {
    public int calc(int a, int b) {
        if (a == 0) return 0;
        if (a == 1) return 1;
        if (b == 0) return 1;

        int p = calc(a, b / 2);

        p = p * p;

        if (b % 2 == 1) p = p * a;

        return p;
    }

    public static void main(String[] argvs) {
        ComputePowerOfNumber p = new ComputePowerOfNumber();

        System.out.println("Result...");

        assert 1  == p.calc(1, 3) : "not valid";
        assert 1  == p.calc(2, 0) : "not valid";
        assert 0  == p.calc(0, 3) : "not valid";
        assert 8  == p.calc(2, 3) : "not valid";
        assert 27 == p.calc(3, 3) : "not valid";
        assert 16 == p.calc(2, 4) : "not valid " + p.calc(2, 4);
    }
}
