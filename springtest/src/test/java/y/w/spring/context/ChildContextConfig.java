package y.w.spring.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * ParentContextConfig
 *
 * @author ywang
 * @date 8/20/2019
 */
@Configuration
public class ChildContextConfig
{
    @Bean
    public TestBeans.BeanInChind beanInChind()
    {
        return new TestBeans.BeanInChind();
    }

    @Bean
    @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "prototype")
    public TestBeans.ScopedBean scopedBean()
    {
        return new TestBeans.ScopedBean();
    }
}
