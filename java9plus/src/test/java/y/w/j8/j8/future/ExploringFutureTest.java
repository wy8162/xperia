package y.w.j8.j8.future;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * https://www.nurkiewicz.com/2013/05/java-8-definitive-guide-to.html
 *
 * @author ywang
 * Date: 6/20/2019
 */
@Slf4j
public class ExploringFutureTest
{
    static final private String someString = "someString";

    @Test
    public void whatIsCompletableFutureTest() throws InterruptedException
    {
        // We can create a CompletableFuture, which there might not be any jobs under it
        CompletableFuture<String> jmsEventFuture = new CompletableFuture<>();

        // Let's create a task to call get or join of the future.
        Thread task = startTask(jmsEventFuture);
        task.start();

        delay(1000L);

        System.out.println("The task is blocked awaiting for future to complete.");

        // Now, let's pretend that there is a JMS message received
        jmsEventFuture.complete("You got a JMS message");

        task.join();
    }

    @Test
    public void completeExceptionTest() throws InterruptedException
    {
        // We can create a CompletableFuture, which there might not be any jobs under it
        CompletableFuture<String> jmsEventFuture = new CompletableFuture<>();

        // Let's create a task to call get or join of the future.
        Thread task = startTask(jmsEventFuture);
        task.start();

        delay(1000L);

        System.out.println("The task is blocked awaiting for future to complete.");

        // Now, let's pretend that there is exception
        jmsEventFuture.completeExceptionally(new Exception("Something wrong"));

        task.join();
    }

    @Test
    public void handleExceptionTest() throws InterruptedException
    {
        // We can create a CompletableFuture, which there might not be any jobs under it
        CompletableFuture<String> jmsEventFuture = CompletableFuture.supplyAsync(() -> {
            if (true)
            {
                throw new IllegalArgumentException("Test exception");
            }
            return "some value";
        }).handle((res, ex) ->{ // To recover from exceptions. It is called whether or not an exception occurs.
            if (ex != null) {
                System.out.println("Got exception: " + ex.getMessage());
                return "unknown";
            }
            return res;
        });

        String result = jmsEventFuture.join();
        assertEquals("unknown", result);
        System.out.println("Result: " + result);
    }

    @Test
    public void exceptionallyTest() throws InterruptedException
    {
        // We can create a CompletableFuture, which there might not be any jobs under it
        CompletableFuture<String> jmsEventFuture = CompletableFuture.supplyAsync(() -> {
            if (true)
            {
                throw new IllegalArgumentException("Test exception");
            }
            return "some value";
        }).exceptionally(ex ->{
            System.out.println("Got exception: " + ex.getMessage());
            return "unknown";
        });

        String result = jmsEventFuture.join();
        assertEquals("unknown", result);
        System.out.println("Result: " + result);
    }

    @Test
    public void chainOfActionsTest()
    {
        // Transform from CompletableFuture<String> to CompletableFuture<Integer> to CompletableFuture<Double>
        CompletableFuture<Double> value = CompletableFuture
            .supplyAsync(() -> "123")          // supplyAsync(Supplier<U> supplier) - Creates a CompletableFuture<String>
            .thenApplyAsync(Integer::parseInt) // thenApplyAsync(Function<? super T,? extends U> fn) - acting on CompletableFuture
            .thenApplyAsync(r->r*r*Math.PI);

        System.out.println(value.join());
    }

    private Thread startTask(CompletableFuture<String> jmsEventFuture)
    {
        return new Thread(() -> {
            System.out.println("Calling future.get() will block...");
            try
            {
                String result = jmsEventFuture.get();
                System.out.println("The result is : " + result);
            }
            catch (InterruptedException | ExecutionException e)
            {
                System.out.println("Got exception: " + e.getMessage());
            }
        });
    }

    static abstract class GenericFuture<String> implements Callable<String>
    {
        protected void delay(Long timeInMillis)
        {
            try
            {
                Thread.sleep(timeInMillis);
            }
            catch (InterruptedException e)
            {
                ;
            }
        }
    }

    static class TestFuture<String> extends GenericFuture<String>
    {

        @Override
        public String call() throws Exception
        {
            // Do something lasting long
            log.info("Inside future...before delay some time");
            delay(2000L);
            log.info("Inside future...after delay some time");
            // Returns result
            return (String) someString;
        }
    }

    private void delay(Long timeInMillis)
    {
        try
        {
            Thread.sleep(timeInMillis);
        }
        catch (InterruptedException e)
        {
            ;
        }
    }
}
