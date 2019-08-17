package y.w.j8.j8.future;

import lombok.extern.log4j.Log4j;
import org.junit.Test;

import java.util.concurrent.*;

import static org.junit.Assert.assertTrue;

/**
 * Test future
 *
 * @author ywang
 * Date: 6/20/2019
 */
@Log4j
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
