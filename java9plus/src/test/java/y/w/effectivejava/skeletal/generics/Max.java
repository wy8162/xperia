package y.w.effectivejava.skeletal.generics;

import java.util.Iterator;
import java.util.List;

/**
 * Reducer
 *
 * @author ywang
 * @date 9/19/2019
 */
public class Max
{
    // Generic method
    // The method is consumer while the parameter is producer.
    // PECS - Producer-extend, Consumer-super
    public static <T extends Comparable<? super T>> T max(List<? extends T> list)
    {
        Iterator<? extends T> it = list.iterator();

        T result = it.next();
        while (it.hasNext())
        {
            T t = it.next();
            if (t.compareTo(result) > 0)
                result = t;
        }
        return result;
    }
}
