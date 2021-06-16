package y.w.study.alg.array;

import org.junit.Test;

public class ReadN {

    private char cache[] = null;
    private int size = 0;
    private int startPosition = 0;

    @Test
    public void test() {
        char[] buff = new char[10];

        int size;
        size = read(buff, 1);
        size = read(buff, 1);
        size = read(buff, 1);
        size = read(buff, 1);
    }

    private int read4(char[] buff) {
        buff[0] = 'a';
        buff[1] = 'b';
        buff[2] = 'c';

        return 3;
    }

    public int read(char[] buf, int n) {
        int position = -1;

        if (cache != null) {
            int readSize = 0;
            if (n >= size) {
                n = n - size;
                readSize = size;
                size = 0;
            } else {
                readSize = n;
                size -= n;
                n = 0;
            }
            for (int i = 0; i < readSize; i++)
                buf[++position] = cache[startPosition + i];

            if (size > 0) startPosition = startPosition + readSize;

            if (size == 0) cache = null;

            if (n == 0) return position + 1;
        }

        char[] readBuffer = new char[4];

        while( n > 0) {
            int len = read4(readBuffer);

            if (len == 0) break;

            for (int i = 0; i < Math.min(len, n); i++)
                buf[++position] = readBuffer[i];

            if (len > n) {
                cache = readBuffer;
                size = len - n;
                startPosition = n;
            }

            n -= len;
        }

        return position + 1;
    }
}
