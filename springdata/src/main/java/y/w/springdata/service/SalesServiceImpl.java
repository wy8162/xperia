package y.w.springdata.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import y.w.springdata.model.Sales;
import y.w.springdata.repository.SalesRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SalesServiceImpl implements SalesService
{
    private static final Log logger = LogFactory.getLog(SalesServiceImpl.class);

    private final SalesRepository salesRepository;

    @Autowired
    public SalesServiceImpl(SalesRepository salesRepository)
    {
        this.salesRepository = salesRepository;
    }

    @Transactional
    @Override public List<Sales> getSalesForDept(String deptNo)
    {
        return salesRepository.findByDeptNo(deptNo);
    }

    @Transactional()
    @Override public Iterable<Sales> findAll()
    {
        return salesRepository.findAll();
    }

//    @Async
//    @Override public CompletableFuture<Iterable<Sales>> asyncFindAll()
//    {
//        logger.info("Thread name: " + Thread.currentThread().getName());
//
//        Iterable<Sales> sales = salesRepository.findAll();
//
//        return CompletableFuture.completedFuture(sales);
//    }

    @Transactional
    @Override public Optional<Sales> findOne(Long id)
    {
        return salesRepository.findById(id);
    }

    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = {Exception.class})
    @Override
    public Sales createSales(Sales sales)
    {
        log.info(sales.toString());

        Sales s = salesRepository.save(sales);

        log.info(s.toString());
        return s;
    }


    @Transactional
    @Override
    public void deleteOneById(Long id)
    {
        salesRepository.deleteById(id);
    }
}
