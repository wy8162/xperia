package y.w.datastructure;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Stack<E> implements Iterable<E>
{
    private Deque<E> deque = new ArrayDeque<>();

    public boolean isEmpty() { return deque.isEmpty(); }
    public E pop() { return  deque.pop(); }
    public void push(E e) { deque.push(e); }
    public int size() { return deque.size(); }

    @Override
    public Iterator<E> iterator()
    {
        return deque.iterator();
    }

    @Override
    public void forEach(Consumer<? super E> action)
    {
        deque.forEach(action);
    }

    @Override
    public Spliterator<E> spliterator()
    {
        return deque.spliterator();
    }
}
