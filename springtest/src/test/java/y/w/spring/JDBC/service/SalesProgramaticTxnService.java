package y.w.spring.JDBC.service;

import y.w.spring.JDBC.repository.SalesRepository;
import y.w.spring.model.Sales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

@Service
public class SalesProgramaticTxnService
{
    private final SalesRepository            repository;
    private final TransactionTemplate        transactionTemplate;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public SalesProgramaticTxnService(
            PlatformTransactionManager transactionManager,
            @Qualifier("jdbcSalesRepository") SalesRepository repository)
    {
        this.repository = repository;
        this.transactionManager = transactionManager;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    /**
     * Traditional way. Need Persistence provider (JPA, Java Persistence API, Provider) for EntityManager like
     * <provider>org.hibernate.ejb.HibernatePersistence</provider>.
     *
     * @return
     */
    public List<Sales> findAllEntityTxn()
    {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("TEST_TXN");
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction txn = entityManager.getTransaction();
        txn.begin();
        List<Sales> sales = repository.findAll();
        txn.commit();
        return sales;
    }

    /**
     * Using TransactionManager directly.
     *
     * @return
     */
    public List<Sales> findAllTxnManager()
    {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();

        // explicitly setting the transaction name is something that can only be done programmatically
        def.setName("SomeTxName");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus status = transactionManager.getTransaction(def);

        try
        {
            List<Sales> sales = repository.findAll();
            transactionManager.commit(status);

            return sales;
        }
        catch (Exception e)
        {
            transactionManager.rollback(status);
        }
        return null;
    }

    /**
     * Using TransactionTemplate.
     *
     * @return
     */
    public List<Sales> findAllTransactionTemplate()
    {
        return transactionTemplate.execute((status) -> repository.findAll());
    }

    /*
    return transactionTemplate.execute(new TransactionCallback<List<Sales>>()
    {
        @Override public List<Sales> doInTransaction(TransactionStatus status)
        {
            return repository.findAll();
        }
    });

    If there is no result to be returned:
        TransactionCallbackWithoutResult

     */

}
