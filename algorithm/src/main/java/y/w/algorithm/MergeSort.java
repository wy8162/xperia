package y.w.algorithm;

import java.lang.reflect.Array;

/**
 * The credit goes to the authors, Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, and Clofford Stein, of the book "Introduction to Algorithms".
 *
 * I studied the book and translated the pseudo codes to Java code.
 */
public class MergeSort<T extends Comparable<T>> implements Sort<T>
{
    /**
     * Merge sort's worst case complexity is O(n log n)
     * <p>
     * Merge sub-array A[p..q] and A[q+1..r], which are all sorted.
     *
     * @param array
     * @param p     - first element of the first sub-array
     * @param q     - last element of the first sub-array. The first element of the
     *              second sub-arrary is q+1
     * @param r     - last element of the second sub-array
     */
    private <T extends Comparable<T>> void merge(T[] array, int p, int q, int r)
    {
        int i, j, k;
        int n1 = q - p + 1;
        int n2 = r - q;

        // This is how to create generic arrays.
        // @SuppressWarnings("unchecked") is needed to avoid error casting to T.
        @SuppressWarnings("unchecked")
        T[] L = (T[]) Array.newInstance(Comparable.class, n1);

        @SuppressWarnings("unchecked")
        T[] R = (T[]) Array.newInstance(Comparable.class, n2);

        for (i = 0; i < n1; i++)
            L[i] = array[p + i];
        for (i = 0; i < n2; i++)
            R[i] = array[q + 1 + i];

        i = 0;
        j = 0;
        k = p;
        while (i < n1 && j < n2)
        {
            if (L[i].compareTo(R[j]) <= 0)
            {
                array[k] = L[i];
                i++;
            }
            else if (j < n2)
            {
                array[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1)
        {
            array[k] = L[i];
            i++;
            k++;
        }
        while (j < n2)
        {
            array[k] = R[j];
            j++;
            k++;
        }
    }

    private <T extends Comparable<T>> void mergeSort(T[] array, int p, int r)
    {
        if (p >= r) // One element array is always sorted
        {
            return;
        }

        int q = (p + r) / 2;
        mergeSort(array, p, q);
        mergeSort(array, q + 1, r);
        merge(array, p, q, r);
    }

    @Override
    public void sort(T[] array)
    {
        if (array == null || array.length < 1)
            return;

        mergeSort(array, 0, array.length - 1);
    }
}
