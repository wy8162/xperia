package y.w.j8.multithreading.forkjoin.ca;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import y.w.j8.multithreading.forkjoin.ca.model.CustomerAccount;

public class TCRTask extends AbstractCustomerAccountsTask<List<CustomerAccount>> {
    private static final Map<String, List<CustomerAccount>> allAccounts = new HashMap<>();
    private final String customerId;

    static {
        allAccounts.put("1", Arrays.asList(new CustomerAccount()));
        allAccounts.put("2", Arrays.asList(new CustomerAccount()));
        allAccounts.put("3", Arrays.asList(new CustomerAccount()));
        allAccounts.put("4", Arrays.asList(new CustomerAccount()));
        allAccounts.put("5", Arrays.asList(new CustomerAccount()));
    }

    public TCRTask(String customerId) {
        this.customerId = customerId;
    }

    @Override
    protected List<CustomerAccount> compute() {
        long delayTime = (long)(Math.random() * 1000);

        delay(delayTime);

        return allAccounts.getOrDefault(customerId, null);
    }
}
