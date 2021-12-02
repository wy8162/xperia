package y.w.j8.multithreading.forkjoin;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.Test;

public class DirSizeAction {
    @Test
    public void testIt() {

        final ForkJoinPool pool = new ForkJoinPool();

        try {
            final AtomicLong sizeAccumulator = new AtomicLong();
            SizeOfFileAction task1 = new SizeOfFileAction(new File("/Users/yangwang/development/akka-metrics"), sizeAccumulator);
            pool.invoke(task1);
            System.out.println("Total size: " + sizeAccumulator.get());
        } finally {
            pool.shutdown();
        }
    }

    private static class SizeOfFileAction extends RecursiveAction {
        private final File file;
        private final AtomicLong sizeAccumulator;

        public SizeOfFileAction(File file, AtomicLong sizeAccumulator) {
            this.file = Objects.requireNonNull(file);
            this.sizeAccumulator = Objects.requireNonNull(sizeAccumulator);
        }

        @Override
        protected void compute() {
            if (file.isFile()) {
                sizeAccumulator.addAndGet(file.length());
            } else {
                final File[] files = file.listFiles();
                if (files != null) {
                    for (final File f : files) {
                        ForkJoinTask.invokeAll(new SizeOfFileAction(f, sizeAccumulator));
                    }
                }
            }
        }
    }
}
