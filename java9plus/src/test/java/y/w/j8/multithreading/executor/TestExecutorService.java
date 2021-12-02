package y.w.j8.multithreading.executor;

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
            TimeUnit.MILLISECONDS.sleep(300);

            return "Task finished";
        };

        List<Callable<String>> callables = Arrays.asList(callable, callable, callable);

        executor.execute(task);
        Future<String> result = executor.submit(callable);
        System.out.println(result.get());

        String s = executor.invokeAny(callables);
        System.out.println(s);

        List<Future<String>> ss = executor.invokeAll(callables);
    }

}
