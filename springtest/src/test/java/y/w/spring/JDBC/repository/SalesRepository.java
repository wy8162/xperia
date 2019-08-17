package y.w.spring.JDBC.repository;

import y.w.spring.model.Sales;

import java.util.List;

public interface SalesRepository
{
    Sales findById(Long id);

    Sales findOne(Long id);

    void delete(Sales sales);

    Sales save(Sales sales);

    Long count();

    List<Sales> findAll();
}
