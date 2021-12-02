package y.w.j8.multithreading.completable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.Test;

/**
 * CompletableFuture by default uses ForkJoinPool.commonPool.
 */
public class TestCompletableFutureScenarios {
    @Test
    public void blockAndWaitForResult() throws ExecutionException, InterruptedException {
        CompletableFuture<Long> res = CompletableFuture.supplyAsync(() -> callService("message"));
        System.out.println("Blocking operation: " + res.get());
    }

    @Test
    public void completeItNotWaitingForItToComplete() throws ExecutionException, InterruptedException {
        CompletableFuture<Long> res = CompletableFuture.supplyAsync(() -> callService("message"));
        assertFalse(res.isDone());
        res.complete(12345L);
        assertTrue(res.isDone());
        System.out.println("Blocking operation: " + res.get());
    }

    @Test
    public void completeInAdvance() throws ExecutionException, InterruptedException {
        CompletableFuture<Long> res = CompletableFuture.completedFuture(1234L);
        System.out.println("Blocking operation: " + res.get());
    }

    // non-blocking
    public static int getnumber() {
        return (int)(Math.random() * 100);
    }

    public static void sendMessage(String message) {
        System.out.println("Message: " + message);
    }

    // Blocking
    public static long callService(String msg) {
        long number = (long) (Math.random() * 1000);

        try {
            Thread.sleep(1000);
        } catch (Exception e) {}

        return number;
    }
}
