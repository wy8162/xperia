package y.w.j8.multithreading.completable;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.junit.Test;

public class TestCompletableFuture {
    @Test
    public void testCompletableFuture() {
        CompletableFuture<String> task = new CompletableFuture<>();
        CompletableFuture<String> done = CompletableFuture.completedFuture("Task done");

        assertTrue(done.isDone());
    }

    @Test
    public void testFireAndForget() throws InterruptedException {
        TestCompletableFuture t = new TestCompletableFuture();

        // By default, using thread from global ForJoinPool.commonPool.
        CompletableFuture.runAsync(() -> t.doSomething("JoinForkPool"));

        // Using executor
        Executor executor = Executors.newFixedThreadPool(5);
        CompletableFuture.runAsync(() -> t.doSomething("executor"), executor);

        Thread.sleep(1000);
    }

    @Test
    public void testTaskReturningResult() throws ExecutionException, InterruptedException {
        TestCompletableFuture t = new TestCompletableFuture();

        CompletableFuture<String> res = CompletableFuture.supplyAsync(() -> t.doSomething("supplySync"));

        res.join();
        assertTrue(res.isDone());

        System.out.println(res.get());

        Thread.sleep(1000);
    }

     @Test
    public void testTaskWithCallback() throws ExecutionException, InterruptedException {
        TestCompletableFuture t = new TestCompletableFuture();

        // Returning result
        CompletableFuture<String> res = CompletableFuture
            .supplyAsync(() -> t.doSomething("supply something"))
            .thenApply(t::doCallBack); // Callback

        // VOID - returning no result
        CompletableFuture
            .supplyAsync(() -> t.doSomething("supply something"))
            .thenAccept(t::doCallBack); // Callback

        Thread.sleep(1000);

        System.out.println(res.get());
    }

    @Test
    public void testTaskWithCallbackHandleException() throws ExecutionException, InterruptedException {
        TestCompletableFuture t = new TestCompletableFuture();

        // Returning result
        CompletableFuture<String> res = CompletableFuture
            .supplyAsync(() -> t.doSomething("1. supply something"))
            .thenApplyAsync(t::doCallBack) // Callback
            .exceptionally(e -> {
                System.out.println(e.getMessage());

                return "error";
            });

        CompletableFuture
            .supplyAsync(() -> t.doSomething("2. supply something"))
            .thenApplyAsync(t::doCallBack) // Callback
            .handle((r, e) -> {
                if (e == null) {
                    System.out.println("Handling - " + r);
                    return r;
                }

                return e.getMessage();
            });

        Thread.sleep(1000);

        System.out.println(res.get());
    }

    public String doSomething(String message) {
        System.out.println(message + ": Done doing something");

        return "result computed: " + message;
    }

    public String doCallBack(String message) {
        System.out.println(message + ": Done doing callback");

        return "callback result computed: " + message;
    }
}
