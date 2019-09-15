package y.w.springdata.repository;

import org.springframework.data.repository.CrudRepository;
import y.w.springdata.model.Sales;

import java.util.List;

public interface SalesRepository extends CrudRepository<Sales, Long>
{
    List<Sales> findByDeptNo(String deptNo);
}
