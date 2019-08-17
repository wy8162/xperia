package y.w.j8.multithreading;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest
{
    @Test
    public void cyclicBarrierTest() throws InterruptedException, BrokenBarrierException
    {
        CyclicBarrier barrier = new CyclicBarrier(3);

        Worker w1 = new Worker(barrier, "Worker-1");
        Worker w2 = new Worker(barrier, "Worker-2");

        w1.start();
        w2.start();

        System.out.println("The workers above are waiting");
        Thread.sleep(5000);
        barrier.await();
        System.out.println("All can start now");

        w1.join();
    }

    public static class Worker extends Thread
    {
        private CyclicBarrier barrier;

        public Worker(CyclicBarrier barrier,
                String name)
        {
            super(name);
            this.barrier = barrier;
        }

        @Override
        public void run()
        {
            try
            {
                System.out.println(Thread.currentThread().getName() + " waiting");
                barrier.await();
                System.out.println(Thread.currentThread().getName() + " finished");
            }
            catch (InterruptedException | BrokenBarrierException e) {}
        }
    }
}
