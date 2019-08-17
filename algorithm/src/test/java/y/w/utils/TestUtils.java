package y.w.utils;

import static org.junit.Assert.assertTrue;

public class TestUtils
{
    public static <T extends Comparable<T>> void assertArraySortedAscending(T[] array)
    {
        assertArraySorted(array, true);
    }

    public static <T extends Comparable<T>> void assertArraySortedDescending(T[] array)
    {
        assertArraySorted(array, false);
    }

    public static <T extends Comparable<T>> void assertArraySorted(T[] array, boolean ascending)
    {
        boolean sorted = true;
        if ( array != null && array.length > 1)
        {
            for (int i=1; i<array.length; i++)
            {
                if (ascending)
                {
                    if (array[i - 1].compareTo(array[i]) > 0)
                    {
                        sorted = false;
                        break;
                    }
                } else {
                    if (array[i - 1].compareTo(array[i]) < 0)
                    {
                        sorted = false;
                        break;
                    }
                }
            }
        }
        assertTrue("The array is not sorted.", sorted);
    }
}
