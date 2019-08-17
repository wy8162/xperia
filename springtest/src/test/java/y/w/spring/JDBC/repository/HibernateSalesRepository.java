package y.w.spring.JDBC.repository;

import y.w.spring.model.Sales;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class HibernateSalesRepository implements SalesRepository
{
    private String SELECT_SALES = "from y.w.spring.model.Sales s";

    private final SessionFactory sessionFactory;

    public HibernateSalesRepository(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    @Override public Sales findById(Long id)
    {
        return null;
    }

    @Transactional
    @Override public Sales findOne(Long id)
    {
        return null;
    }

    @Transactional
    @Override public void delete(Sales sales)
    {

    }

    @Transactional
    @Override public Sales save(Sales sales)
    {
        return null;
    }

    @Override public Long count()
    {
        return null;
    }

    @Transactional
    @Override public List<Sales> findAll()
    {
        return session().createQuery(SELECT_SALES).list();
    }

    /**
     * @return the transactional session
     */
    protected Session session()
    {
        return sessionFactory.getCurrentSession();
    }
}
