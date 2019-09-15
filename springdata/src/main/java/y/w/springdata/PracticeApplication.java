package y.w.springdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import y.w.springdata.config.JavaConfig;

@SpringBootApplication
@Import(JavaConfig.class)
@EnableTransactionManagement
public class PracticeApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(PracticeApplication.class, args);
    }
}
