package y.w.concurrency.jcp

import java.util.concurrent.*;

class TaskImpl implements Runnable {
    private final CountDownLatch countDown

    TaskImpl(CountDownLatch countDown) { this.countDown = countDown; }
    public void run() {
        countDown.await();
        println "${Thread.currentThread().getName()} - ${System.currentTimeMillis()}";
        Thread.sleep(500L);
    }
}

class ThreadPerTaskExecutor implements java.util.concurrent.Executor {
    public void execute(Runnable r) {
        new Thread(r).start();
    }
}

class DirectExecutor implements java.util.concurrent.Executor { // Execute it in the caller's thread
    public void execute(Runnable r) {
        r.run();
    }
}

int NTHREADS = 10;
java.util.concurrent.Executor exec = Executors.newFixedThreadPool( NTHREADS );
def countDown = new CountDownLatch(1);
def tpe = new ThreadPerTaskExecutor();
def de  = new DirectExecutor();

println "Main thread: ${Thread.currentThread().getName()} - ${System.currentTimeMillis()}"
(0..20).each {
    exec.execute( new TaskImpl(countDown) );
}

countDown.countDown(); // Give a signal for all the threads to start

tpe.execute( new TaskImpl(countDown) );
de.execute( new TaskImpl(countDown) );
