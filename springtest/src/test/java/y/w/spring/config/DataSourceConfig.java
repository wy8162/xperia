package y.w.spring.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig
{
    /**
     * Create a data source by looking up JNDI directory.
     * @return
     */
    @Bean
    @Profile("reference_purpose")
    public JndiObjectFactoryBean dataSourceJNDI()
    {
        JndiObjectFactoryBean factoryBean = new JndiObjectFactoryBean();
        factoryBean.setJndiName("/jdbc/OracleDataSource");
        factoryBean.setResourceRef(true);
        factoryBean.setProxyInterface(javax.sql.DataSource.class);
        return factoryBean;
    }

    /**
     * Need commons-dbcp to create pooled datasource based on Apache Commons DBCP.
     *
     * Samples:
     * MYSQL_DB_DRIVER_CLASS=com.mysql.jdbc.Driver
     * MYSQL_DB_URL=jdbc:mysql://localhost:3306/UserDB
     * MYSQL_DB_USERNAME=pankaj
     * MYSQL_DB_PASSWORD=pankaj123
     *
     * @return
     */
    @Bean
    @Profile("reference_purpose")
    public BasicDataSource dataSourceH2DBCP()
    {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:tcp://localhost/~/spitter");
        ds.setUsername("sa");
        ds.setPassword("");
        ds.setInitialSize(5);
        ds.setMaxActive(10);
        return ds;
    }

    /**
     * Oracle datasource
     *
     * Oracle DB Properties
     * ORACLE_DB_DRIVER_CLASS=oracle.jdbc.driver.OracleDriver
     * ORACLE_DB_URL=jdbc:oracle:thin:@localhost:1521:orcl
     * ORACLE_DB_USERNAME=hr
     * ORACLE_DB_PASSWORD=oracle
     *
     * @return
     */
    @Bean
    @Profile("dev")
    public BasicDataSource dataSourceOracleDBCP()
    {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        ds.setUrl("jdbc:oracle:thin:@localhost:1521:develop");
        ds.setUsername("awy");
        ds.setPassword("awy");
        ds.setInitialSize(5);
        ds.setMaxActive(10);
        return ds;
    }

    /**
     * Create Spring Datasource for test purposes. There are three
     * - DriverManagerDataSource Returns new connection everytime a connecion is requested. No pool.
     * - SimpleDriverDataSource similar to DriverManagerDataSource
     * - SingleConnectionDataSource returns the same connection everytime a connection is returned.
     */
    @Bean
    @Profile("reference_purpose")
    public DataSource dataSourceSpring()
    {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        ds.setUrl("jdbc:oracle:thin:@localhost:1521:develop");
        ds.setUsername("awy");
        ds.setPassword("awy");
        return ds;
    }

    /**
     * Embeded datasource using H2.
     *
     * @return
     */
    @Bean
    @Profile("reference_purpose")
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:test-data.sql")
                .build();
    }
}
