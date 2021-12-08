package y.w.j8.multithreading.executor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

public class TestExecutorService {
    @Test
    public void testExecutorService() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        Runnable task = () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Callable<String> callable = () -> {
            TimeUnit.MILLISECONDS.sleep(1000);

            System.out.println("Done - " + Thread.currentThread().getName());

            return "Task finished";
        };

        List<Callable<String>> callables = new ArrayList<>();
        for (int i = 0; i < 20; i++) callables.add(callable);

        //executor.execute(task);
        //Future<String> result = executor.submit(callable);
        //System.out.println(result.get());

        // String s = executor.invokeAny(callables);
        // System.out.println(s);

        List<Future<String>> ss = executor.invokeAll(callables);

        System.out.println("This thread: " + Thread.currentThread().getName());

        for (Future<String> r : ss) {
            if (r.isDone()) {
                System.out.println("Task done: " + r.get());
            } else {
                System.out.println("Task not done yet, waiting... " + r.get());
            }
        }

        executor.shutdown();
    }

}
