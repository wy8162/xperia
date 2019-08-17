package y.w.webapp.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * WebConfig - this is the WebApplicationContext.
 *
 * For controllers, view resolvers, and handler mappings, etc. These drive the
 * front end.
 *
 * @author ywang
 * @date 8/14/2019
 */
@Configuration
@EnableWebMvc
@ComponentScan("y.w.webapp")
public class WebApplicationContextConfig implements WebMvcConfigurer
{
    // If no view resolved configures yet, NameViewResolver is used by default
    // But here we will configure JSP view resolver
    @Bean
    public ViewResolver viewResolver()
    {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        resolver.setOrder(1);
        resolver.setExposeContextBeansAsAttributes(true);

        return resolver;
    }

    /**
     * Configure static content handling. By calling enable() on the given
     * DefaultServletHandlerConfigurer, you’re asking DispatcherServlet to forward
     * requests for static resources to the servlet container’s default servlet and not to try to
     * handle them itself.
     *
     * DispatcherServlet won't handle static resources then.
     *
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
    {
        configurer.enable();
    }

    /**
     * messageSource method name can not be change to others, else there will has errors when browse web page.
     * messageSource is used to make page text internalization. The message file is saved in src/main/resources/config/messages_en_US.preoperties
     * You should create config folder by yourself if it dose not exist.
     * */
    @Bean(name = "messageSource")
    public MessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:config/messages_en_US");
        messageSource.setCacheSeconds(1);
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }
}
