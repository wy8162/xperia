package y.w.j8.j8.future;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Test future
 *
 * @author ywang
 * Date: 6/20/2019
 */
@Slf4j
public class FutureTest
{
    static final private String someString = "someString";

    @Test
    public void testFuture_Blocking()
    {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<String> resultFuture = executorService.submit(new TestFuture<>());

        delay(1000L);
        log.info("Submitted future. The future runs before this line.");

        try
        {
            // Will block before the task finishes or timeout if the task lasts longer
            // than wait time.
            String res = resultFuture.get(4, TimeUnit.SECONDS);
            log.info("It's blocked for sometime spent by the future.");
            assertTrue(res.equals(someString));
        }
        catch (InterruptedException | ExecutionException | TimeoutException e)
        {
            e.printStackTrace();
        }
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
