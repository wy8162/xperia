package y.w.j8.multithreading.forkjoin.ca;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import y.w.j8.multithreading.forkjoin.ca.model.CustomerAccount;

public class EntitleTask extends AbstractCustomerAccountsTask<List<CustomerAccount>> {
    private static Map<String, List<CustomerAccount>> serviceableAccounts = new HashMap<>();
    private final String customerId;

    static {
        serviceableAccounts.put("1", Arrays.asList(new CustomerAccount()));
        serviceableAccounts.put("2", Arrays.asList(new CustomerAccount()));
        serviceableAccounts.put("3", Arrays.asList(new CustomerAccount()));
        serviceableAccounts.put("4", Arrays.asList(new CustomerAccount()));
        serviceableAccounts.put("5", Arrays.asList(new CustomerAccount()));
    }

    public EntitleTask(String customerId) {
        this.customerId = customerId;
    }

    @Override
    protected List<CustomerAccount> compute() {
        long delayTime = (long)(Math.random() * 1000);

        delay(delayTime);

        return serviceableAccounts.getOrDefault(customerId, null);
    }
}
