class MergeSort
{
    /**
     * Merge sort's worst case complexity is O(n log n)
     *
     * Merge sub-array A[p..q] and A[q+1..r], which are all sorted.
     *
     * @param array
     * @param p - first element of the first sub-array
     * @param q - last element of the first sub-array. The first element of the
     *            second sub-arrary is q+1
     * @param r - last element of the second sub-array
     */
    def void merge(int [] array, int p, int q, int r)
    {
        int i, j, k;
        int n1 = q - p + 1;
        int n2 = r - q;

        def L = new int[n1];
        def R = new int[n2];

        for (i=0; i<n1; i++) L[i] = array[p+i];
        for (i=0; i<n2; i++) R[i] = array[q+1+i];

        i = 0;
        j = 0;
        k = p;
        while (i < n1 && j < n2)
        {
            if (L[i] <= R[j])
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
        while (i<n1)
        {
            array[k] = L[i];
            i++;
            k++;
        }
        while (j<n2)
        {
            array[k] = R[j];
            j++;
            k++;
        }
    }

    def void mergeSort(int[] array, int p, int r)
    {
        if (p >= r) // One element array is always sorted
            return;

        int q = (p+r) / 2;
        mergeSort(array, p, q);
        mergeSort(array, q+1, r);
        merge(array, p, q, r);
    }
}

def mergeSort = new MergeSort();

a1 = [1] as int[];
a2 = [2,1] as int[];
a3 = [3,1,2] as int[];
a4 = [90,91,60,71,80,20,30,40,50,10] as int[]

mergeSort.mergeSort(a1, 0, a1.length - 1)
mergeSort.mergeSort(a2, 0, a2.length - 1)
mergeSort.mergeSort(a3, 0, a3.length - 1)
mergeSort.mergeSort(a4, 0, a4.length - 1)

println(a1)
println(a2)
println(a3)
println(a4)