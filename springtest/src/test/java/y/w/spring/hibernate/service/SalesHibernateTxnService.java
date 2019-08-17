package y.w.spring.hibernate.service;

import y.w.spring.JDBC.repository.SalesRepository;
import y.w.spring.model.Sales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SalesHibernateTxnService
{
    private SalesRepository repository;

    @Autowired
    public SalesHibernateTxnService(
            @Qualifier("hibernateSalesRepository") SalesRepository repository)
    {
        this.repository = repository;
    }

    @Transactional
    public List<Sales> findAll()
    {
        return repository.findAll();
    }
}
