package y.w.j8.multithreading.forkjoin.ca;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import y.w.j8.multithreading.forkjoin.ca.model.AccountDetails;

public class CustomerAccountsApi extends AbstractCustomerAccountsTask<AccountDetails> {
    private static final ForkJoinPool pool = new ForkJoinPool(64);
    private final String customerId;

    public CustomerAccountsApi(String customerId) {
        this.customerId = customerId;
    }

    public CompletableFuture<AccountDetails> getAccounts() {
        pool.submit(this::compute);

        return CompletableFuture.completedFuture(join());
    }

    @Override
    protected AccountDetails compute() {
        EntitleTask entileTask = new EntitleTask(customerId);
        TCRTask tcrTask = new TCRTask(customerId);

        entileTask.fork();
        tcrTask.fork();

        return null;
    }
}
