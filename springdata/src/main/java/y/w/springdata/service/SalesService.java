package y.w.springdata.service;

import y.w.springdata.model.Sales;

import java.util.List;
import java.util.Optional;

public interface SalesService
{
    Optional<Sales> findOne(Long id);

    List<Sales> getSalesForDept(String deptNo);

    Iterable<Sales> findAll();

    //    CompletableFuture<Iterable<Sales>> asyncFindAll();

    Sales createSales(Sales sales);

    void deleteOneById(Long id);
}
