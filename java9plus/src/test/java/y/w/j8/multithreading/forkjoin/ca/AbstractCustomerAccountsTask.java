package y.w.j8.multithreading.forkjoin.ca;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public abstract class AbstractCustomerAccountsTask<T> extends RecursiveTask<T> {
    private static final ForkJoinPool pool = new ForkJoinPool(64);

    protected void delay(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
