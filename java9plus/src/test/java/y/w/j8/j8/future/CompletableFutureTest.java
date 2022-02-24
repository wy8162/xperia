package y.w.j8.j8.future;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.junit.Before;
import org.junit.Test;

/**
 * Test CompletableFuture
 *
 * @author ywang
 * Date: 6/20/2019
 */
@Slf4j
public class CompletableFutureTest
{
    private static String prodName = "prod";
    private static String prod1 = prodName + "1";
    private static String prod2 = prodName + "2";
    private static String prod3 = prodName + "3";
    private static String prod4 = prodName + "4";
    private static String prodNonexitent = prodName + "Nonexistent";

    private static BigDecimal price1 = new BigDecimal(1);
    private static BigDecimal price2 = new BigDecimal(2);
    private static BigDecimal price3 = new BigDecimal(3);
    private static BigDecimal price4 = new BigDecimal(4);


    @Before
    public void setUp()
    {

    }

    @Test(expected = ExecutionException.class)
    public void testAsyncMethodCall() throws InterruptedException, ExecutionException, TimeoutException
    {
        log.info("......>Preparing tests");
        Shop shop = new Shop();

        long startTime = System.nanoTime();
        Future<BigDecimal> price = shop.getPriceAsync(prod1);
        log.info("......>Got future price");

        assertEquals(price.get(), price1);

        log.info("......>Elapsed time in seconds: " + (System.nanoTime() - startTime) / 1_000_000_000.0);

        price = shop.getPriceAsync(prodNonexitent);
        price.get(5, TimeUnit.SECONDS);
    }

    static class Shop
    {
        static Map<String, BigDecimal> products = new HashMap<>();
        static {
            products.put(prod1, price1);
            products.put(prod2, price2);
            products.put(prod3, price3);
            products.put(prod4, price4);
        }

        /**
         * Asynchronous method.
         *
         * @param product
         * @return
         */
        public Future<BigDecimal> getPriceAsync(String product)
        {
            CompletableFuture<BigDecimal> futurePrice = new CompletableFuture<>();

            Executors.newCachedThreadPool().submit( () -> {
                long time = new RandomDataGenerator().nextLong(1000L, 10000L);
                log.info("----> Inside future task...begin, to wait for " + time);

                try
                {
                    BigDecimal price = products.get(product);

                    if (price != null)
                    {
                        futurePrice.complete(price);
                    }
                    else
                    {
                        throw new Exception();
                    }
                }
                catch (Exception e)
                {
                    // This will throw ExecutionException
                    futurePrice.completeExceptionally(e);
                }
                delay(time);
                log.info("----> Inside future task...finished");
            });

            return futurePrice;
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
}
