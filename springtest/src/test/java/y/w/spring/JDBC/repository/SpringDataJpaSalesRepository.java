package y.w.spring.JDBC.repository;

import y.w.spring.model.Sales;
import org.springframework.data.repository.CrudRepository;

/**
 * Use automatic IPA repositories
 *
 * Needs @EnableJpaRepositories(basePackages="") to enable it.
 */
public interface SpringDataJpaSalesRepository extends CrudRepository<Sales, Long>
{
}
