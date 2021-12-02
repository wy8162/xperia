package y.w.j8.multithreading.forkjoin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import jsr166y.ForkJoinTask;
import org.junit.Before;
import org.junit.Test;

public class TestForkJoin {
    private int[] arr;
    private CustomRecursiveTask customRecursiveTask;

    @Before
    public void setUp() {
        Random random = new Random();
        arr = new int[50];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(35);
        }
        customRecursiveTask = new CustomRecursiveTask(arr);
    }

    @Test
    public void testForkJoinTask() {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        ForkJoinPool forkJoinPoolTwo = ForkJoinPool.commonPool();

        assertNotNull(forkJoinPool);
        assertEquals(5, forkJoinPool.getParallelism());
        assertEquals(forkJoinPool, forkJoinPoolTwo);
    }

    @Test
    public void executeRecursiveAction_whenExecuted_thenCorrect() {

        CustomRecursiveAction myRecursiveAction = new CustomRecursiveAction(arr, 0, arr.length - 1);
        ForkJoinPool.commonPool().invoke(myRecursiveAction);

        assertTrue(myRecursiveAction.isDone());
    }

    @Test
    public void executeRecursiveTask_whenExecuted_thenCorrect() {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        forkJoinPool.execute(customRecursiveTask);
        int result = customRecursiveTask.join();
        assertTrue(customRecursiveTask.isDone());

        forkJoinPool.submit(customRecursiveTask);
        int resultTwo = customRecursiveTask.join();
        assertTrue(customRecursiveTask.isDone());
    }

    /**
     * The fork() method submits a task to a pool, but it doesn't trigger its execution.
     * The join() method must be used for this purpose.
     */
    @Test
    public void executeRecursiveTaskWithFJ_whenExecuted_thenCorrect() {
        CustomRecursiveTask customRecursiveTaskFirst = new CustomRecursiveTask(arr);
        CustomRecursiveTask customRecursiveTaskSecond = new CustomRecursiveTask(arr);
        CustomRecursiveTask customRecursiveTaskLast = new CustomRecursiveTask(arr);

        customRecursiveTaskFirst.fork();
        customRecursiveTaskSecond.fork();
        customRecursiveTaskLast.fork();
        int result = 0;
        result += customRecursiveTaskLast.join();
        result += customRecursiveTaskSecond.join();
        result += customRecursiveTaskFirst.join();

        assertTrue(customRecursiveTaskFirst.isDone());
        assertTrue(customRecursiveTaskSecond.isDone());
        assertTrue(customRecursiveTaskLast.isDone());
        assertTrue(result != 0);
    }

    @Test
    public void testCustomRecursiveTask2() {
        int[] nums = new int[]{1,2,3,4,5,6,7};

        CustomRecursiveTask2 task2 = new CustomRecursiveTask2(nums);

        task2.fork();
        Integer result = task2.join();

        System.out.println("CustomRecursiveTask2: " + result);

        // Another way to call the task
        result = new ForkJoinPool().invoke(task2);
        System.out.println("CustomRecursiveTask2: " + result);
    }
}
