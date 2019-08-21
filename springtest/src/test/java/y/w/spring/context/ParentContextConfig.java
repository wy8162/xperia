package y.w.spring.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ParentContextConfig
 *
 * @author ywang
 * @date 8/20/2019
 */
@Configuration
public class ParentContextConfig
{
    @Bean
    public TestBeans.BeanInParent beanInParent()
    {
        return new TestBeans.BeanInParent();
    }
}
