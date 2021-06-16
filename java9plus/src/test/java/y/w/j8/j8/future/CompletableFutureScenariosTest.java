package y.w.j8.j8.future;

import lombok.extern.log4j.Log4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Various scenarios based on https://dzone.com/articles/20-examples-of-using-javas-completablefuture.
 *
 * Also, see
 * <ul>
 *     <li>Part 1 - https://devleadresource.com/java-completable-future/</li>
 *     <li>Part 2 - https://devleadresource.com/completable-future-java-8-example/</li>
 * </ul>
 *
 * Suppose you have to compute something. We logically divide this computation in many intermediate
 * computations, which we have to execute before arriving to final required result. These intermediate
 * computations can be treated as a standalone tasks that can be executed in isolation.
 *
 * In Java 8 parlance, these intermediate computations are termed as Completion Stages. Java 8 provides
 * mechanisms to execute stages and combine their results.
 *
 * A stage can be thought as, a piece of code that is standalone and it can be executed in isolation.
 *
 * @author ywang
 * Date: 6/21/2019
 * Time: 09:56
 */
@Log4j
public class CompletableFutureScenariosTest
{
    /**
     * CompletableFuture already completed.
     */
    @Test
    public void testCompletedFuture()
    {
        String completedFuture = "completedFuture";
        CompletableFuture<String> cf = CompletableFuture.completedFuture(completedFuture);
        assertTrue(cf.isDone());
        assertEquals(completedFuture, cf.getNow(null));
    }

    /**
     * A CompletableFuture is executed asynchronously when the method typically ends with the keyword Async.
     *
     * By default (when no Executor is specified), asynchronous execution uses the common ForkJoinPool implementation,
     * which uses daemon threads to execute the Runnable task. Note that this is specific to CompletableFuture. Other
     * CompletionStage implementations can override the default behavior.
     */
    @Test
    public void  testSimpleAsyncStage()
    {
        CompletableFuture cf = CompletableFuture.runAsync(
                () -> {
                    assertTrue(Thread.currentThread().isDaemon());
                    delay(2000L);
                }
        );

        assertFalse(cf.isDone());
        delay(3000L);
        assertTrue(cf.isDone());
    }

    /**
     * Providing executor
     */
    @Test
    public void  testSimpleAsyncStageWithOurExecutor()
    {
        ExecutorService executor = Executors.newCachedThreadPool();

        CompletableFuture cf = CompletableFuture.runAsync( // runs async and returns void.
                () -> {
                    assertTrue(Thread.currentThread().isDaemon());
                    delay(2000L);
                }
        , executor); // Uses a provided executor, a thread pool.

        assertFalse(cf.isDone());
        delay(3000L);
        assertTrue(cf.isDone());
    }


    /**
     * Apply a Function on the result from the previous stage
     */
    @Test
    public void testCompletedFutureApplyFunction()
    {
        String completedFuture = "completedFuture";
        CompletableFuture<String> cf = CompletableFuture.completedFuture(completedFuture)
                .thenApply(s -> {
                    log.info("Got result from last stage: " + s);
                    return s.toUpperCase(); // return the result processed.
                });

        assertEquals(completedFuture.toUpperCase(), cf.getNow(null));
    }

    /**
     * Apply a Function asynchronously on the result from the previous stage
     */
    @Test
    public void testCompletedFutureApplyFunctionAsync()
    {
        ExecutorService executor = Executors.newCachedThreadPool();

        String completedFuture = "completedFuture";
        CompletableFuture<String> cf = CompletableFuture.completedFuture(completedFuture)
                .thenApplyAsync(s -> { // Apply function asynchronously
                    log.info("Got result from last stage: " + s + ". Now waiting for sometime...");

                    delay(3000L);

                    return s.toUpperCase(); // return the result processed.
                },
                executor); // Use our own thread pool.

        log.info("The second stage is still running at this moment.");
        assertNull(cf.getNow(null));
        assertEquals(completedFuture.toUpperCase(), cf.join()); // join() returns result and wait if necessary
    }

    /**
     * Consume the result from the previous stage without returning a value.
     */
    @Test
    public void testConsumeResultFromLastStage()
    {
        StringBuilder result = new StringBuilder();

        String completedFuture = "completedFuture";
        CompletableFuture<Void> cf = CompletableFuture.completedFuture(completedFuture)
                .thenAccept( s -> result.append(s)); // thenAccept doesn't return value.

        assertTrue(result.length() > 0);
    }

    /**
     * Consume asynchronously the result from the previous stage without returning a value.
     */
    @Test
    public void testConsumeResultFromLastStageAsync()
    {
        StringBuffer result = new StringBuffer(); // StringBuffer is thread safe

        String completedFuture = "completedFuture";
        CompletableFuture<Void> cf = CompletableFuture.completedFuture(completedFuture)
                .thenAcceptAsync( s -> result.append(s)); // thenAccept doesn't return value.

        cf.join();

        assertTrue(result.length() > 0);
    }

    /**
     * First, we create a CompletableFuture that is already completed with the value "message". Next, we call thenApplyAsync,
     * which returns a new CompletableFuture. This method applies an uppercase conversion in an asynchronous fashion upon
     * completion of the first stage (which is already complete, thus the Function will be immediately executed).
     * This example also illustrates a way to delay the asynchronous task using the delayedExecutor(timeout, timeUnit) method.
     *
     * We then create a separate handler stage, exceptionHandler, that handles any exception by returning another message
     * "message upon cancel".
     *
     * Next, we explicitly complete the second stage with an exception. This makes the join() method on the stage, which is doing
     * the uppercase operation, throw a CompletionException (normally join() would have waited for 1 second to get the uppercase
     * string). It will also trigger the handler stage.
     */
    @Test
    public void testCompletingComputationExceptionally()
    {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message")
                .thenApplyAsync(String::toUpperCase, CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));

        CompletableFuture<String> exceptionHandler = cf.handle((s, th) -> (th != null) ? "message upon cancel" : "" );

        cf.completeExceptionally(new RuntimeException("completed exceptionally"));

        assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
        try
        {
            cf.join();
            fail("Should have thrown an exception");
        }
        catch (CompletionException ex)
        { // just for testing
            assertEquals("completed exceptionally", ex.getCause().getMessage());
        }

        assertEquals("message upon cancel", exceptionHandler.join());
    }

    @Test
    public void cancelComputation()
    {

        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(String::toUpperCase,
                CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));

        CompletableFuture<String> cf2 = cf.exceptionally(throwable -> "canceled message");

        assertTrue("Was not canceled", cf.cancel(true));

        assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());

        assertEquals("canceled message", cf2.join());
    }

    @Test
    public void applyToEitherExample()
    {
        String original = "Message";

        CompletableFuture<String> cf1 = CompletableFuture.completedFuture(original).thenApplyAsync(s -> delayedUpperCase(s));

        // Applys function on result from either cf1 or cf2
        CompletableFuture<String> cf2 = cf1
                .applyToEither(CompletableFuture.completedFuture(original).thenApplyAsync(s -> delayedLowerCase(s)), s -> s + " from applyToEither");

        String result = cf2.join();

        assertTrue(result.endsWith(" from applyToEither"));

        log.info("Result can be upper case or lower case, depending on which one finishes first: " + result);
    }

    @Test
    public void acceptEitherExample()
    {
        String original = "Message";
        StringBuffer result = new StringBuffer();

        CompletableFuture<Void> cf = CompletableFuture.completedFuture(original).thenApplyAsync(s -> delayedUpperCase(s))
            .acceptEither(CompletableFuture.completedFuture(original).thenApplyAsync(s -> delayedLowerCase(s)),
                          s -> result.append(s).append(" acceptEither"));

        cf.join();

        assertTrue(result.toString().endsWith("acceptEither"));
        log.info("Result can be upper case or lower case, depending on which one finishes first: " + result.toString());
    }

    @Test
    public void runAfterBothExample()
    {
        String original = "Message";

        StringBuilder result = new StringBuilder();

        // Run synchronously
        CompletableFuture.completedFuture(original).thenApply(String::toUpperCase)
                .runAfterBoth(CompletableFuture.completedFuture(original).thenApply(String::toLowerCase), () -> result.append("done"));

        assertTrue("Result was empty", result.length() > 0);
    }

    @Test
    public void thenAcceptBothExampleBiConsumer() {
        String original = "Message";
        StringBuilder result = new StringBuilder();

        CompletableFuture
                .completedFuture(original)
                .thenApply(String::toUpperCase)
                .thenAcceptBoth(
                    CompletableFuture
                            .completedFuture(original)
                            .thenApply(String::toLowerCase),
                            (s1, s2) -> result.append(s1 + s2) // BiConsumer
                );

        assertEquals("MESSAGEmessage", result.toString());
    }

    @Test
    public void thenCombineExampleBiFunction() {
        String original = "Message";

        CompletableFuture<String> cf = CompletableFuture
                .completedFuture(original)
                .thenApply(s -> delayedUpperCase(s))
                .thenCombine(
                        CompletableFuture
                                .completedFuture(original)
                                .thenApply(s -> delayedLowerCase(s)),
                        (s1, s2) -> s1 + s2); // BiFunction

        assertEquals("MESSAGEmessage", cf.getNow(null));
    }

    @Test
    public void thenCombineAsyncExample() { // thenCombine combines two independent futures when they are both done
        String original = "Message";

        CompletableFuture<String> cf = CompletableFuture
                .completedFuture(original)
                .thenApplyAsync(s -> delayedUpperCase(s))
                // <U,V> CompletableFuture<V> thenCombine(CompletableFuture<? extends U> other, BiFunction<? super T,? super U,? extends V> fn)
                .thenCombine(
                        CompletableFuture
                                .completedFuture(original)
                                .thenApplyAsync(s -> delayedLowerCase(s)),
                        (s1, s2) -> s1 + s2);

        assertEquals("MESSAGEmessage", cf.join());
    }

    @Test
    public void thenComposeExample() { // thenCompose() is used to chain one future dependent on the other
        String original = "Message";

        CompletableFuture<String> cf = CompletableFuture
                .completedFuture(original)
                .thenApply(s -> delayedUpperCase(s))
                // <U> CompletableFuture<U> thenCompose(Function<? super T,CompletableFuture<U>> fn);
                .thenCompose(
                        upper -> CompletableFuture
                            .completedFuture(original)
                                .thenApply(s -> delayedLowerCase(s))
                                .thenApply(s -> upper + s));

        assertEquals("MESSAGEmessage", cf.join());
    }

    @Test
    public void allOfExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");

        List<CompletableFuture<String>> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> delayedUpperCase(s)))
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((v, th) -> {
            futures.forEach(cf -> assertTrue(cf.getNow(null) != null));
            result.append("done");
        });

        assertTrue("Result was empty", result.length() > 0);
    }


    @Test
    public void allOfAsyncExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");

        List<CompletableFuture<String>> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApplyAsync(s -> delayedUpperCase(s)))
                .collect(Collectors.toList());

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
                .whenComplete((v, th) -> {
                    futures.forEach(cf -> assertTrue(cf.getNow(null) != null));
                    result.append("done");
                });

        allOf.join();

        assertTrue("Result was empty", result.length() > 0);
    }

    private void delay(long time)
    {
        try
        {
            Thread.sleep(time);
        }
        catch (InterruptedException e)
        {
        }
    }

    private static final Random random = new Random();
    private int maxDelay = 2000; // 2 seconds
    private int minDelay = 500;  // 0.5 seconds
    private void randomDelay()
    {
        try
        {
            Thread.sleep(minDelay + random.nextInt(maxDelay));
        }
        catch (InterruptedException e)
        {
        }
    }


    private String delayedUpperCase(String s)
    {
        delay(1000L);

        log.info("Inside delayedUpperCase");

        return s.toUpperCase();
    }

    private String delayedLowerCase(String s)
    {
        delay(1000L);

        log.info("Inside delayedLowerCase");

        return s.toLowerCase();
    }
}
