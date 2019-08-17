package y.w.spring.jpa.service;

import y.w.spring.JDBC.repository.SpringDataJpaSalesRepository;
import y.w.spring.model.Sales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesSpringDataJpaTxnService
{
    private SpringDataJpaSalesRepository repository;

    @Autowired
    public SalesSpringDataJpaTxnService(SpringDataJpaSalesRepository repository)
    {
        this.repository = repository;
    }

    //@Transactional not needed. JpaRepository is marked with @Transactional by default.
    public Iterable<Sales> findAll()
    {
        return repository.findAll();
    }
}
