package y.w.study.alg.recursion;

public class DivideWithoutDivision {
    public static void main(String[] args) {
        System.out.println(div(10, 3));
        System.out.println(divByShiftingBits(10, 3));
    }

    public static int div(int a, int b) {
        if (a < b) return 0;

        return 1 + div(a - b, b);
    }

    public static int divByShiftingBits(int dividend, int divisor) {
        /**
         * https://www.geeksforgeeks.org/divide-two-integers-without-using-multiplication-division-mod-operator/
         *
         * Any number can be represented in base 2 to the power 0 or 1.
         *
         * The following equals to compute
         *      dividend = divisor * (2^0 + 2^1 + 2^2 + ... 2^n) + remainder
         * where quotient = sum(2^0 + 2^1 + ... + 2^n)
         *
         */
        long quotient = 0, temp = 0;

        for (int i = 31; i >= 0; i--) { // Integer.MAX_VALUE = 2^31 - 1
            if (temp + (divisor << i) <= divisor) {
                System.out.print(String.format("(temp, Q) %d %d", temp, quotient));

                temp += divisor << i;
                quotient |= 1l << i;

                System.out.println(String.format(" ==> (temp, Q) %d %d", temp, quotient));
            }
        }

        return (int)quotient;
    }
}
