/**
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
class QuickSort
{
    private void exchange(int[] array, int i, int j)
    {
        int temp = array[i]
        array[i] = array[j]
        array[j] = temp
    }

    /**
     * See Divide above
     * @param array
     * @param p
     * @param r
     * @return
     */
    int partition(int[] array, int p, int r)
    {
        int x = array[r]
        int i = p - 1
        for (int j = p; j < r; j++) {
            if (array[j] <= x) {
                i++
                exchange(array, i, j)
            }
        }
        exchange(array, i + 1, r)

        return i + 1
    }

    void sort(int[] array, int p, int r)
    {
        if (p < r) {
            int q = partition(array, p, r)
            sort(array, p, q - 1)
            sort(array, q + 1, r)
        }
    }
}

def quickSort = new QuickSort();

a1 = [20,30,40,90,91,60,71,80,50,10, 3, 2, 77, 25] as int[]
println(a1)

quickSort.sort(a1, 0, a1.length - 1)

println(a1)