package y.w.spring;

import y.w.spring.config.JpaTransactionConfig;
import y.w.spring.jpa.service.SalesSpringDataJpaTxnService;
import y.w.spring.model.Sales;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import y.w.spring.JDBC.repository.SpringDataJpaSalesRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= JpaTransactionConfig.class)
@EnableJpaRepositories(basePackageClasses={ SpringDataJpaSalesRepository.class})
@ActiveProfiles("dev")
public class SpringDataJpaTxnTest
{
    @Autowired
    private SalesSpringDataJpaTxnService txnService;

    @Test public void loadContextTest()
    {
    }

    @Test
    public void salesHibernateTxnServiceTest()
    {
        Iterable<Sales> it = txnService.findAll();

        it.forEach( s -> System.out.println(s) );
    }
}
