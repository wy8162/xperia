package y.w.j8.j8.future;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class CompletableFutureAsyncTest
{
    private static String prodName = "prod";
    private static String prod1 = prodName + "1";
    private static String prod2 = prodName + "2";
    private static String prod3 = prodName + "3";
    private static String prod4 = prodName + "4";
    private static String prodNonexitent = prodName + "Nonexistent";

    List<String> prices;
    List<Shop> shops;

    @Before
    public void setUp()
    {
        shops = Arrays.asList(
            new Shop("Shop1"),
            new Shop("Shop2"),
            new Shop("Shop3"),
            new Shop("Shop4"),
            new Shop("Shop5"),
            new Shop("Shop6"),
            new Shop("Shop7"),
            new Shop("Shop8"),
            new Shop("Shop9"),
            new Shop("Shop10"),
            new Shop("Shop11"),
            new Shop("Shop12"),
            new Shop("Shop13")
        );
    }

    @Test
    public void testCompletableAsync() throws InterruptedException, ExecutionException, TimeoutException
    {
        long startTime = System.nanoTime();

        // Uses CompletableFuture
        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price is %.2f", shop.getStoreName(), shop.getPrice(prod1))
                        , getExecutor())) // Use our executor for much better performance
                .collect(toList());

        // join method waits and returns the result, not a future.
        // Second stream should not be chained to the first one. Otherwise, the whole
        // thing will become sequential because of stream's laziness.
        prices = priceFutures.stream().map(CompletableFuture::join).collect(toList());

        log.info("Prices found for " + prod1 + " are:\n" + StringUtils.join(prices, "\n"));
        log.info("===> Total time spent - CompletableFuture.supplyAsync: " + (System.nanoTime() - startTime)/1_000_000);
    }

    @Test
    public void testParallelStream() throws InterruptedException, ExecutionException, TimeoutException
    {
        long startTime = System.nanoTime();
        // Parallelize it another way
        prices = shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getStoreName(), shop.getPrice(prod1)))
                .collect(toList());

        log.info("Prices found for " + prod1 + " are:\n" + StringUtils.join(prices, "\n"));
        log.info("===> Total time spent - parallelStream: " + (System.nanoTime() - startTime)/1_000_000);
    }

    static class Shop
    {
        private String storeName;

        private Map<String, BigDecimal> products = new HashMap<>();

        public Shop(String storeName)
        {
            this.storeName = storeName;
            products.put(prod1, new BigDecimal(new RandomDataGenerator().nextLong(1L, 100L)));
            products.put(prod2, new BigDecimal(new RandomDataGenerator().nextLong(1L, 100L)));
            products.put(prod3, new BigDecimal(new RandomDataGenerator().nextLong(1L, 100L)));
            products.put(prod4, new BigDecimal(new RandomDataGenerator().nextLong(1L, 100L)));
        }

        public String getStoreName()
        {
            return storeName;
        }

        /**
         * Blocking, old fashioned, method call.
         *
         * @param product
         * @return
         */
        public BigDecimal getPrice(String product)
        {
            BigDecimal price;

            log.info(storeName + "----> Inside future task...begin, to wait for random time");

            price = products.get(product);

            delayRandomly();
            log.info(storeName + "----> Inside future task...finished");

            return price;
        }

        /* Different ways to make getPrice async. */
        /**
         * Make the getPrice asynchronous.
         *
         * @param product
         * @return
         */
        public Future<BigDecimal> getPriceAsync(String product)
        {
            CompletableFuture<BigDecimal> futurePrice = new CompletableFuture<>();

            new Thread( () -> {
                BigDecimal price = getPrice(product);
                futurePrice.complete(price);
            }).start();

            return futurePrice;
        }

        /**
         * Using the handy factory methods provided by CompletableFuture instead. Much simpler.
         *
         * The supplyAsync method accepts a Supplier as argument and returns a Completable-Future that
         * will be asynchronously completed with the value obtained by invoking that Supplier. This
         * Supplier will be run by one of the Executors in the ForkJoinPool, but you can specify a different
         * Executor by passing it as a second argument to the overloaded version of this method
         *
         * @param product
         * @return
         */
        public Future<BigDecimal> getPriceAsyncCompletableFuture(String product)
        {
            return CompletableFuture.supplyAsync(() -> getPrice(product));
        }
    }

    static class DiscountService
    {
        private static final BigDecimal discountPercentage = new BigDecimal(0.2);

        public static BigDecimal getDiscountedPrice(BigDecimal price)
        {
            BigDecimal discountedPrice = price.multiply(discountPercentage);

            log.info(String.format("Calculate discounted price (%.2f): %.2f", price, discountedPrice));
            delayRandomly();

            return discountedPrice;
        }
    }

    static class ForexService
    {
        private static BigDecimal forexRate = new BigDecimal(1.2);

        public static BigDecimal fromUSDtoEUR(BigDecimal price)
        {
            BigDecimal exchangedPrice = price.divide(forexRate);

            log.info(String.format("Foreign exchange (%.2f): %.2f", price, exchangedPrice));
            delayRandomly();

            return exchangedPrice;
        }
    }

    public static void delayRandomly()
    {
        try
        {
            long time = new RandomDataGenerator().nextLong(500L, 5000L);
            Thread.sleep(time);
        }
        catch (InterruptedException e)
        {
        }
    }

    private final Executor getExecutor()
    {
        Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });

        return executor;
    }
}
