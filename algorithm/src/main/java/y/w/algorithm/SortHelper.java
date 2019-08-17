package y.w.algorithm;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class SortHelper
{
    public static <T> void exchange(T[] array, int i, int j)
    {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * Helper methods for Heap Sort
     */
    public static int p(int i)
    {
        return (i - 1) / 2;
    }   // Returns the index number of the parent node

    public static int left(int i)
    {
        return 2 * i + 1;
    }   // Return the left child node of node i

    public static int right(int i)
    {
        return 2 * i + 2;
    }   // Return the right child node of node i

    public static <T extends Comparable<T>> void maxHeapify(int heapSize, T[] array, int i) {
        int l = SortHelper.left(i);
        int r = SortHelper.right(i);
        int largest = i;

        if (l < heapSize && array[l].compareTo(array[i]) > 0 )
            largest = l;

        if (r < heapSize && array[r].compareTo(array[largest]) > 0)
            largest = r;

        if (largest != i) {
            // Exchange the values
            SortHelper.exchange(array, i, largest);
            maxHeapify(heapSize, array, largest);
        }
    }

    /**
     * Help method for partition for QuickSort
     */
    public static <T extends Comparable<T>> int partition(T[] array, int p, int r)
    {
        T x = array[r];
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (array[j].compareTo(x) <= 0) {
                i++;
                SortHelper.exchange(array, i, j);
            }
        }
        SortHelper.exchange(array, i + 1, r);

        return i + 1;
    }

    //
    // The following two methods were copied from Java
    //
    /**
     * Returns x's Class if it is of the form "class C implements
     * Comparable<C>", else null.
     */
    static Class<?> comparableClassFor(Object x) {
        if (x instanceof Comparable) {
            Class<?> c; Type[] ts, as; Type t; ParameterizedType p;
            if ((c = x.getClass()) == String.class) // bypass checks
                return c;
            if ((ts = c.getGenericInterfaces()) != null) {
                for (int i = 0; i < ts.length; ++i) {
                    if (((t = ts[i]) instanceof ParameterizedType) &&
                            ((p = (ParameterizedType)t).getRawType() ==
                                    Comparable.class) &&
                            (as = p.getActualTypeArguments()) != null &&
                            as.length == 1 && as[0] == c) // type arg is c
                        return c;
                }
            }
        }
        return null;
    }

    /**
     * Returns k.compareTo(x) if x matches kc (k's screened comparable
     * class), else 0.
     */
    @SuppressWarnings({"rawtypes","unchecked"}) // for cast to Comparable
    static int compareComparables(Class<?> kc, Object x, Object k) {
        return (k == null || k.getClass() != kc ? 0 :
                ((Comparable)x).compareTo(k));
    }
}