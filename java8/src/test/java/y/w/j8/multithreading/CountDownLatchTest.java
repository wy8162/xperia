package y.w.j8.multithreading;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest
{
    @Test
    public void countDownLatchTest() throws InterruptedException
    {
        CountDownLatch latch = new CountDownLatch(2);

        new Worker(3000, latch, "Worker-1").start();
        new Worker(4500, latch, "Worker-2").start();

        Server server = new Server("Server", latch);
        server.start();

        latch.await();
        server.join();
    }

    public static class Worker extends Thread
    {
        private int delay;
        private CountDownLatch latch;

        public Worker(int delay, CountDownLatch latch,
                String name)
        {
            super(name);
            this.delay = delay;
            this.latch = latch;
        }

        @Override
        public void run()
        {
            try
            {
                Thread.sleep(delay);
                latch.countDown();
                System.out.println(Thread.currentThread().getName()
                        + " finished");
            }
            catch (InterruptedException e) {}
        }
    }

    public static class Server extends Thread
    {
        private CountDownLatch latch;

        public Server(String name, CountDownLatch latch)
        {
            super(name);
            this.latch = latch;
        }

        @Override
        public void run()
        {
            System.out.println("Server...started");
            try
            {
                latch.await();
            }
            catch (InterruptedException e) {}
            System.out.println("Server...found workers finished");
        }
    }
}
