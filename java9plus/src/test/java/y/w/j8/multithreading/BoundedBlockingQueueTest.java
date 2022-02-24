/**
 * Yang Wang, Feb 26, 2012
 */
package y.w.j8.multithreading;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author yangwang
 *
 */
@Slf4j
public class BoundedBlockingQueueTest {
	private static final long LOCKOUT_DETECT_TIMEOUT = 1000;

	@Test public void testIsEmptyWhenConstructed() {
		BoundedBlockingQueue<Integer> bb = new BoundedBlockingQueue<Integer>(10);
		assertTrue(bb.isEmpty());
		assertFalse(bb.isFull());
	}
	
	@Test public void testIsFullAfterPuts() throws InterruptedException {
		BoundedBlockingQueue<Integer> bb = new BoundedBlockingQueue<Integer>(10);
		for (int i=0; i<10; i++)
			bb.put(i);
		assertTrue(bb.isFull());
		assertFalse(bb.isEmpty());
	}
	
	@Test public void testTakeBlocksWhenEmpty() {
		final BoundedBlockingQueue<Integer> bb = new BoundedBlockingQueue<Integer>(10);
		
		Thread taker = new Thread() {
			public void run() {
				try {
					int unused = bb.take();
					fail(); // Fail if we get here.
				} catch (InterruptedException success) {
					log.debug("Received Intterupt - success that the method was blocked.");
				}
			}
		};
		try {
			taker.start();
			Thread.sleep(LOCKOUT_DETECT_TIMEOUT);
			taker.interrupt();
			taker.join(LOCKOUT_DETECT_TIMEOUT);
			assertFalse(taker.isAlive());
		} catch (Exception unexpected) {
			fail();
		}
	}
	
	@Test public void testPutBlocksWhenFull() {
		final BoundedBlockingQueue<Integer> bb = new BoundedBlockingQueue<Integer>(10);
		for (int i=0; i<10; i++) {
			try {
				bb.put(i);
			} catch (InterruptedException e) {}
		}
		
		Thread putter = new Thread() {
			public void run() {
				try {
					bb.put(11);
					fail(); // Fail if we get here.
				} catch (InterruptedException success) {
					log.debug("Received Intterupt - success that the method was blocked.");
				}
			}
		};
		try {
			putter.start();
			Thread.sleep(LOCKOUT_DETECT_TIMEOUT);
			putter.interrupt();
			putter.join(LOCKOUT_DETECT_TIMEOUT);
			assertFalse(putter.isAlive());
		} catch (Exception unexpected) {
			fail();
		}
	}
}
