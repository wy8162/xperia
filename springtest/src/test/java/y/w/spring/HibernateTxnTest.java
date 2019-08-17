package y.w.spring;

import y.w.spring.config.JpaHibernateTransactionConfig;
import y.w.spring.hibernate.service.SalesHibernateTxnService;
import y.w.spring.model.Sales;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= JpaHibernateTransactionConfig.class)
@ActiveProfiles("dev")
public class HibernateTxnTest
{
    @Autowired
    private SalesHibernateTxnService salesHibernateTxnService;

    @Test public void loadContextTest()
    {
    }

    @Test
    public void salesHibernateTxnServiceTest()
    {
        List<Sales> sales = salesHibernateTxnService.findAll();

        for (Sales s : sales)
            System.out.println(s);
    }
}
