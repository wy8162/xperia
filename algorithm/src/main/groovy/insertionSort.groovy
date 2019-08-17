/**
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
class InsertSort
{
    def DESCENDING = { v1, v2 -> v1 < v2 }
    def ASCENDING = { v1, v2 -> v1 > v2 }

    def insertionSort(int[] array, def order)
    {
        for (int j = 1; j < array.size(); j++)
        {
            def value = array[j]
            def i = j - 1

            while (i >= 0 && order(array[i], value))
            {
                // Swap values
                array[i+1] = array[i]
                i = i - 1
            }

            array[i+1] = value
        }

        return array
    }

    def insertionSortDescending(int [] arrary)
    {
        return insertionSort(arrary, DESCENDING)
    }

    def insertionSortAscending(int [] arrary)
    {
        return insertionSort(arrary, ASCENDING)
    }
}

def insertionSort = new InsertSort()

def a1 = [90,91,60,71,80,20,30,40,50,10] as int[]
def a2 = [90,91,60,71,80,20,30,40,50,10] as int[]


println("----------------")
println(a1)
a1 = insertionSort.insertionSortDescending(a1)
a2 = insertionSort.insertionSortAscending(a2)
println(a1)
println(a2)
