package y.w.effectivejava.skeletal.generics;

import java.util.Arrays;
import java.util.Collection;

/**
 * Stack
 *
 * @author ywang
 * @date 9/19/2019
 */
public class Stack<E>
{
    private E[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    @SuppressWarnings("unchecked") // It's safe to custom Object[] to E[] here.
    public Stack()
    {
        elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(E e)
    {
        ensureCapacity();
        elements[size++] = e;
    }

    public E pop()
    {
        if (size == 0) return null;
        E result = elements[--size];
        elements[size] = null; // Nullify the obsolete reference
        return result;
    }

    public void pushAll(Iterable<? extends E> src) // Producer-extend
    {
        for (E e : src)
            push(e);
    }

    public void popAll(Collection<? super E> dst) // Consumer-super
    {
        while(!isEmpty())
            dst.add(pop());
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    private void ensureCapacity()
    {
        if (elements.length == size)
        {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}
