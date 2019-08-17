package y.w.spring.config;

import y.w.spring.JDBC.repository.JdbcSalesRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"y.w.spring.JDBC"})
@Import({DataSourceConfig.class})
public class JdbcTransactionConfig
{
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource)
    {
        return new JdbcTemplate(dataSource);
    }

    @Bean public JdbcSalesRepository jdbcSalesRepository(JdbcOperations jdbcOperations)
    {
        return new JdbcSalesRepository(jdbcOperations);
    }

    // The following are for transaction management

    /**
     * Needed for transaction management. Also:
     * @EnableTransactionManagement in Java configuration
     * @Transactional on methods or classes.
     *
     * @param dataSource
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
