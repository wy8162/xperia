package y.w.spring.JDBC.service;

import y.w.spring.JDBC.repository.SalesRepository;
import y.w.spring.model.Sales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SalesDeclarativeTxnService
{
    private SalesRepository repository;

    @Autowired
    public SalesDeclarativeTxnService(@Qualifier("jdbcSalesRepository") SalesRepository repository)
    {
        this.repository = repository;
    }

    @Transactional
    public List<Sales> findAll()
    {
        return repository.findAll();
    }
}
