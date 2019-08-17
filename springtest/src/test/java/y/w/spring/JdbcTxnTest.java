package y.w.spring;

import y.w.spring.JDBC.repository.JdbcSalesRepository;
import y.w.spring.config.JdbcTransactionConfig;
import y.w.spring.JDBC.service.SalesDeclarativeTxnService;
import y.w.spring.JDBC.service.SalesProgramaticTxnService;
import y.w.spring.model.Sales;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= JdbcTransactionConfig.class)
@ActiveProfiles("dev")
public class JdbcTxnTest
{
    @Autowired
    private JdbcSalesRepository jdbcSalesRepository;

    @Autowired
    private SalesDeclarativeTxnService salesDeclarativeTxnService;

    @Autowired
    private SalesProgramaticTxnService salesProgramaticTxnService;

    @Test public void loadContextTest()
    {
    }

    @Test
    public void getAllNoTransactionalTest()
    {
        List<Sales> sales = jdbcSalesRepository.findAll();

        for (Sales s : sales)
            System.out.println(s);
    }

    @Test
    public void getAllDeclarativeTxnTest()
    {
        List<Sales> sales = salesDeclarativeTxnService.findAll();

        for (Sales s : sales)
            System.out.println(s);
    }

    @Test
    @Ignore("Need Persistence Provider like Hibernate to work")
    public void entityManagerTest()
    {
        List<Sales> sales = salesProgramaticTxnService.findAllEntityTxn();

        for (Sales s : sales)
            System.out.println(s);
    }

    @Test
    public void transactionManagerTest()
    {
        List<Sales> sales = salesProgramaticTxnService.findAllTxnManager();

        for (Sales s : sales)
            System.out.println(s);
    }

    @Test
    public void transactionTemplateTest()
    {
        List<Sales> sales = salesProgramaticTxnService.findAllTransactionTemplate();

        for (Sales s : sales)
            System.out.println(s);
    }
}
