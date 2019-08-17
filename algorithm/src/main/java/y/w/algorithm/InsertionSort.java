package y.w.algorithm;

/**
 * The credit goes to the authors, Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, and Clofford Stein, of the book "Introduction to Algorithms".
 *
 * I studied the book and translated the pseudo codes to Java code.
 *
 * Best case: O(n) - the array is already sorted
 * Average case: O(n**2)
 * Worst case: O(n**2)
 *
 * Summary of algorithms
 *
 * 1. The sub-array of A[0..j-1] is sorted
 * 2. When iterate i=j-1 through 0, stop whenever find A[i] < A[j]. No need to compare further
 *    because A[0..j-1] is already sorted.
 */
public class InsertionSort<T extends Comparable<T>> implements Sort<T>
{
    private <T extends Comparable<T>> boolean lessThan(T v1, T v2)
    {
        return v1.compareTo(v2) < 0;
    }

    private <T extends Comparable<T>> boolean greaterThan(T v1, T v2)
    {
        return v1.compareTo(v2) > 0;
    }

    private <T extends Comparable<T>> void insertionSort(T[] array)
    {
        for (int j = 1; j < array.length; j++)
        {
            T value = array[j];
            int i = j - 1;

            while (i >= 0 && lessThan(value, array[i]))
            {
                // Swap values
                array[i+1] = array[i];
                i = i - 1;
            }

            array[i+1] = value;
        }
    }


    private <T extends Comparable<T>> void insertionSortAscending(T [] arrary)
    {
        insertionSort(arrary);
    }

    @Override
    public void sort(T[] array)
    {
        insertionSortAscending(array);
    }
}
