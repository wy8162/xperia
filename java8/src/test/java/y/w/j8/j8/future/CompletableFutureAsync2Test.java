package y.w.j8.j8.future;

import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import static java.util.stream.Collectors.toList;

/**
 * Test CompletableFuture
 *
 * @author ywang
 * Date: 6/20/2019
 */
@Log4j
public class CompletableFutureAsync2Test
{
    private static String prodName = "prod";

    private static String DISCOUNT_CODE_NONE     = "NONE";
    private static String DISCOUNT_CODE_SILVER   = "SILVER";
    private static String DISCOUNT_CODE_GOLD     = "GOLD";
    private static String DISCOUNT_CODE_PLATINUM = "PLATINUM";
    private static String DISCOUNT_CODE_DIAMOND  = "DIAMOND";

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
        ExecutorService executor = getExecutor();

        long startTime = System.nanoTime();

        String prod = prodName + "1";

        // Uses CompletableFuture
        List<CompletableFuture<String>> priceFutures = shops
                .stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(prod), executor)) // Use our executor for much better performance
                // parse is local computation. So do it sequentially
                .map(future -> future.thenApply(Quote::parse))
                // thenCompose allows you to pipeline two asynchronous operations, passing the result of the first
                // operation to the second operation when it becomes available
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))
                .collect(toList());

        // join method waits and returns the result, not a future.
        // Second stream should not be chained to the first one. Otherwise, the whole
        // thing will become sequential because of stream's laziness.
        prices = priceFutures.stream().map(CompletableFuture::join).collect(toList());

        log.info("Prices found for " + prod + " are:\n" + StringUtils.join(prices, "\n"));
        log.info("===> Total time spent - CompletableFuture.supplyAsync: " + (System.nanoTime() - startTime)/1_000_000);

//        executor.shutdownNow();
//
//        if (!executor.awaitTermination(100, TimeUnit.MICROSECONDS)) {
//            System.out.println("Still waiting...");
//            System.exit(0);
//        }
//        System.out.println("Exiting normally...");
    }

    static class Product
    {
        public String productName;
        public Discount.Code discountCode;
        public BigDecimal price;

        public Product(String product, Discount.Code discountCode, BigDecimal price)
        {
            this.productName = product;
            this.discountCode = discountCode;
            this.price = price;
        }

        @Override
        public String toString()
        {
            return String.format("%.2f:%s:%s", price, discountCode, productName);
        }
    }

    static class Shop
    {
        private String storeName;

        private Map<String, Product> products = new HashMap<>();

        public Shop(String storeName)
        {
            List<Discount.Code> discounts = Arrays.asList(
                    Discount.Code.NONE,
                    Discount.Code.SILVER,
                    Discount.Code.GOLD,
                    Discount.Code.PLATINUM,
                    Discount.Code.DIAMOND);

            Collections.shuffle(discounts);

            this.storeName = storeName;
            for (int i = 1; i<5; i++)
            {
                String name = "prod" + i;

                Product p = new Product(
                        name,
                        discounts.get(new RandomDataGenerator().nextInt(0, discounts.size() - 1)),
                        new BigDecimal(new RandomDataGenerator().nextLong(1L, 100L)));
                products.put(name, p);

                System.out.println(p.toString());
            }
        }

        public String getStoreName()
        {
            return storeName;
        }

        /**
         * Blocking, old fashioned, method call.
         *
         * @param productName
         * @return "storeName:price:discountCode"
         */
        public String getPrice(String productName)
        {
            log.info(storeName + "----> Inside future task...begin, to wait for random time");

            Product prod = products.get(productName);

            delayRandomly();
            log.info(storeName + "----> Inside future task...finished");

            // storeName:price:discountCode:productName
            return String.format("%s:%.2f:%s:%s", storeName, prod.price, prod.discountCode, prod.productName);
        }

        /* Different ways to make getPrice async. */
        /**
         * Make the getPrice asynchronous.
         *
         * @param productName
         * @return
         */
        public Future<String> getPriceAsync(String productName)
        {
            CompletableFuture<String> futurePrice = new CompletableFuture<>();

            new Thread( () -> {
                String price = getPrice(productName);
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
         * @param productName
         * @return
         */
        public Future<String> getPriceAsyncCompletableFuture(String productName)
        {
            return CompletableFuture.supplyAsync(() -> getPrice(productName));
        }
    }


    static class Quote
    {
        private final String storeName;
        private final String productName;
        private final BigDecimal price;
        private final Discount.Code discountCode;

        public Quote(String storeName, String productName, BigDecimal price, Discount.Code discountCode)
        {
            this.storeName = storeName;
            this.productName = productName;
            this.price = price;
            this.discountCode = discountCode;
        }

        public static Quote parse(String s)
        {
            String[] split = s.split(":");
            String shopName = split[0];
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble(split[1]));
            Discount.Code discountCode = Discount.Code.valueOf(split[2]);
            String productName = split[3];
            return new Quote(shopName, productName, price, discountCode);
        }

        public String getStoreName()
        {
            return storeName;
        }

        public String getProductName()
        {
            return productName;
        }

        public BigDecimal getPrice()
        {
            return price;
        }

        public Discount.Code getDiscountCode()
        {
            return discountCode;
        }
    }


    static class Discount
    {
        public enum Code
        {
            NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

            private final int percentage;

            Code(int percentage)
            {
                this.percentage = percentage;
            }
        }

        public static String applyDiscount(Quote quote)
        {
            return String.format("%s price is %.2f", quote.getStoreName(), Discount.apply(quote.getPrice(), quote.getDiscountCode()));
        }

        private static BigDecimal apply(BigDecimal price, Code code)
        {
            delayRandomly();

            BigDecimal discountedPrice = price.multiply(new BigDecimal((100.0-code.percentage)/100.0));
            //System.out.println(String.format("Price : %.2f Discount: %d, discounted price %.2f", price, code.percentage, discountedPrice));

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


    private final ExecutorService getExecutor()
    {
        ExecutorService executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });

        return executor;
    }
}
