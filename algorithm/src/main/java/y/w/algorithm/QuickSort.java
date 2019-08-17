package y.w.algorithm;

/**
 * The credit goes to the authors, Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, and Clofford Stein, of the book "Introduction to Algorithms".
 *
 * I studied the book and translated the pseudo codes to Java code.
 *
 * Quicksort, like merge sort, applies the divide-and-conquer paradigm introduced
 * in Section 2.3.1. Here is the three-step divide-and-conquer process for sorting a
 * typical subarray A[p..r].
 * <p>
 * Divide:
 * Partition (rearrange) the array AOEp : : r into two (possibly empty) subarrays
 * A[p..q-1] and A[q+1..r] such that each element of A[p..q+1] is
 * less than or equal to A[q], which is, in turn, less than or equal to each element
 * of A[q+1..r]. Compute the index q as part of this partitioning procedure.
 *
 * Conquer: Sort the two subarrays A[p..q-1] and A[q+1..r] by recursive calls
 * to quicksort.
 *
 * Combine: Because the subarrays are already sorted, no work is needed to combine
 * them: the entire array A[p..r] is now sorted.
 */
public class QuickSort<T extends Comparable<T>> implements Sort<T>
{
    private void sortArrary(T[] array, int p, int r)
    {
        if (p < r) {
            int q = SortHelper.partition(array, p, r);
            sortArrary(array, p, q - 1);
            sortArrary(array, q + 1, r);
        }
    }


    @Override
    public void sort(T[] array)
    {
        if (array == null || array.length < 1)
            return;

        sortArrary(array, 0, array.length - 1);
    }
}