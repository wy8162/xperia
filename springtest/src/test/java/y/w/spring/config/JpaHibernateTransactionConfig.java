package y.w.spring.config;

import y.w.spring.JDBC.repository.HibernateSalesRepository;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"y.w.spring.hibernate"})
@Import({DataSourceConfig.class})
public class JpaHibernateTransactionConfig
{
    @Autowired
    @Qualifier("dataSourceOracleDBCP")
    BasicDataSource dataSource;

    @Bean
    public HibernateSalesRepository hibernateSalesRepository()
    {
        return new HibernateSalesRepository(sessionFactory());
    }

    // The following are for transaction management

    /**
     * Needed for transaction management. Also:
     * @EnableTransactionManagement in Java configuration
     * @Transactional on methods or classes.
     *
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new HibernateTransactionManager(sessionFactory());
    }

    // Hibernate related configurations

    @Bean
    public SessionFactory sessionFactory() {
        return new LocalSessionFactoryBuilder(dataSource)
                .scanPackages("y.w.spring.model")
                .addProperties(hibernateProperties())
                .buildSessionFactory();
    }
    @Bean
    public Properties hibernateProperties() {
        Properties hibernateProp = new Properties();
        hibernateProp.put("hibernate.dialect", "org.hibernate.dialect.OracleDialect"); //"org.hibernate.dialect.H2Dialect");
        //hibernateProp.put("hibernate.hbm2ddl.auto", "create-drop");
        hibernateProp.put("hibernate.format_sql", true);
        hibernateProp.put("hibernate.use_sql_comments", true);
        hibernateProp.put("hibernate.show_sql", true);
        return hibernateProp;
    }
}
