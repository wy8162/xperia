package y.w.concurrency.jcp

import java.util.concurrent.*;

class TestLatch {
    public long timeTasks(int nThreads, final Runnable task) {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate   = new CountDownLatch(nThreads);

        for (int i=0; i<nThreads; i++) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                            println Thread.currentThread().getName() + " finished"
                        }
                    } catch (InterruptedException ignored) {
                    }
                 }
            };
            t.start();
        }
        long start = System.nanoTime();
        startGate.countDown(); // give a signal to start the threads
        endGate.await();       // Wait for all threads to complete
        return System.nanoTime() - start
    }

    class Task implements Runnable {
        void run() {
            println Thread.currentThread().getName()
        }
    }
}



def task = new TestLatch.Task()
def test = new TestLatch()

println test.timeTasks( 5, task )
