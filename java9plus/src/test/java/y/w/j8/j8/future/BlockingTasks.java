package y.w.j8.j8.future;

import java.util.concurrent.ForkJoinPool;
import java.util.function.Supplier;

/**
 * https://stackoverflow.com/questions/37512662/is-there-anything-wrong-with-using-i-o-managedblocker-in-java8-parallelstream
 */
public class BlockingTasks {

    public static<T> T callInManagedBlock(final Supplier<T> supplier) {
        final SupplierManagedBlock<T> managedBlock = new SupplierManagedBlock<>(supplier);
        try {
            ForkJoinPool.managedBlock(managedBlock);
        } catch (InterruptedException e) {
            throw new Error(e);
        }
        return managedBlock.getResult();
    }

    private static class SupplierManagedBlock<T> implements ForkJoinPool.ManagedBlocker {
        private final Supplier<T> supplier;
        private T result;
        private boolean done = false;

        private SupplierManagedBlock(final Supplier<T> supplier) {
            this.supplier = supplier;
        }

        @Override
        public boolean block() {
            result = supplier.get();
            done = true;
            return true;
        }

        @Override
        public boolean isReleasable() {
            return done;
        }

        public T getResult() {
            return result;
        }
    }
}

