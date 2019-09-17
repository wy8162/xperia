/**
 * Copied from Java Concurrency In Practice
 * 
 * Yang Wang, Feb 26, 2012
 */
package y.w.j8.multithreading;

import java.util.concurrent.ArrayBlockingQueue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class BoundedBlockingQueue<E> {
	@GuardedBy("this") private ArrayBlockingQueue<E> queue = null;
	
	public BoundedBlockingQueue(int capacity) {
		queue = new ArrayBlockingQueue<E>(capacity);
	}
	
	public boolean isEmpty() {
		return queue.size() ==0;
	}
	
	public boolean isFull() {
		return queue.remainingCapacity() == 0;
	}
	
	public synchronized void put(E x) throws InterruptedException {
		queue.put(x);
	}
	
	public synchronized E take() throws InterruptedException {
		return queue.take();
	}
}
