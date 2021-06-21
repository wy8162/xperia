package y.w.study.alg.array;

/**
 * The Hamming distance hamming(a, b) between two strins of bits, a and b,
 * of equal length, is the number of positions where the strings differ.
 * I.e., hamming(01101, 1101) = 2.
 *
 * There is a better way to count the different bits:
 *    a ^ b
 *
 * Represents the different bits.
 *
 * Guide to Competitive Programming.
 */

public class HammingDistances {
    static boolean findParity(int x)
    {
        int y = x ^ (x >> 1);
            y = y ^ (y >> 2);
            y = y ^ (y >> 4);
            y = y ^ (y >> 8);
            y = y ^ (y >> 16);
     
        // Rightmost bit of y holds
        // the parity value
        // if (y&1) is 1 then parity
        // is odd else even
        if ((y & 1) > 0)
            return true;
        return false;
    }

    /* Function to get no of set
        bits in binary representation
        of positive integer n
     */
    static int countSetBits(int n)
    {
        int count = 0;
        while (n > 0) {
            count += n & 1;
            n >>= 1;
        }
        return count;
    }
}
