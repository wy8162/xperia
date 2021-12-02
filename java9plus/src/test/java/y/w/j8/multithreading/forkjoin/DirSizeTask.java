package y.w.j8.multithreading.forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import org.junit.Test;

/**
 * Check https://github.com/albertattard/java-fork-join-example for details. It is excellent.
 */
public class DirSizeTask {
    @Test
    public void testIt() {
        // When we create a new ForkJoinPool, the default level of parallelism (number of threads)
        // will be by default the number of available processors in our system, a number that gets
        // returned by the method Runtime.availableProcessors().
        //
        // For CPU-intensive tasks you’ll see no benefit in having a pool larger than the number of
        // processors that you have available.
        //
        // However, if your tasks are IO-intensive tasks (what means that they’ll be frequently waiting
        // for IO operations to complete) you could possibly benefit from a larger pool in some cases.
        //
        System.out.println("CPU Core: " + Runtime.getRuntime().availableProcessors());
        System.out.println("CommonPool Parallelism: " + ForkJoinPool.commonPool().getParallelism());
        System.out.println("CommonPool Common Parallelism: " + ForkJoinPool.getCommonPoolParallelism());

        final ForkJoinPool pool = new ForkJoinPool();

        try {
            SizeOfFileTask task1 = new SizeOfFileTask(new File("/Users/yangwang/development/akka-metrics"));
            pool.submit(task1);
            SizeOfFileTask task2 = new SizeOfFileTask(new File("/Users/yangwang/development/akka-stream"));
            pool.submit(task2);
            SizeOfFileTask task3 = new SizeOfFileTask(new File("/Users/yangwang/development/akka-stream"));
            pool.submit(task3);
            SizeOfFileTask task4 = new SizeOfFileTask(new File("/Users/yangwang/development/akka-stream"));
            pool.submit(task4);
            System.out.println("Total size  2: " + task2.join());
            System.out.println("Total size  1: " + task1.join());
            System.out.println("Total size  3: " + task3.join());
            System.out.println("Total size  4: " + task4.join());
        } finally {
            pool.shutdown();
        }

        for (Entry<String, Integer> e : SizeOfFileTask.counter.entrySet())
            System.out.println(String.format("%s : %d", e.getKey(), e.getValue()));
    }

    private static class SizeOfFileTask extends RecursiveTask<Long> {
        public static ConcurrentHashMap<String, Integer> counter = new ConcurrentHashMap<>();
        private static final long serialVersionUID = -196522408291343951L;

        private final File file;

        public SizeOfFileTask(File file) {
            this.file = Objects.requireNonNull(file);
        }

        @Override
        protected Long compute() {
            counter.putIfAbsent(Thread.currentThread().getName(), 0);
            counter.put(Thread.currentThread().getName(), counter.get(Thread.currentThread().getName()) + 1);
            if (file.isFile()) {
//                System.out.println(String.format("--> Thread = %s .. %s", Thread.currentThread().getName(), file.getAbsolutePath()));
                return file.length();
            } else {
                final List<SizeOfFileTask> tasks = new ArrayList<>();
                final File[] files = file.listFiles();
                if (files != null) {
                    for (final File f : files) {
                        final SizeOfFileTask task = new SizeOfFileTask(f);
                        task.fork();
                        tasks.add(task);
                    }
                }
                long size = 0;
                for (final SizeOfFileTask task : tasks) {
                    size += task.join();
                }

                return size;
            }
        }
    }
}
