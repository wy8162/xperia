package y.w.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import y.w.web.error.CustomErrorAttributes;

/**
 * WebConfiguration
 *
 * @author ywang
 * @date 8/6/2019
 */
@Configuration
@ComponentScan("y.w.web")
public class WebConfiguration
{
    /**
     * View resolver. This is a JSP resolver. We used Thymeleaf in dependency so Thymeleaf related
     * view resolver is configured automatically.
     *
     * @return
     */
//    @Bean
//    public InternalResourceViewResolver internalResourceViewResolver()
//    {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//
//        viewResolver.setPrefix("classpath:/templates/");
//        viewResolver.setSuffix(".html");
//
//        return viewResolver;
//    }

    /**
     * Bean for custom error attributes.
     *
     * Spring will detect this and uses it.
     */
    @Bean
    public CustomErrorAttributes errorAttributes()
    {
        return new CustomErrorAttributes();
    }

    /**
     * Define a locale resolve. LocaleResolver is one of those
     * provided by Spring. It resolves locales by inspecting the Accept-Language
     * header of an HTTP request.
     *
     * There are others as well:
     * SessionLocaleResolver, CookieLocaleResolver, etc.
     *
     * Implement LocaleResolver interface to define a custom one.
     */
    @Bean
    public LocaleResolver localeResolver () {
        return new AcceptHeaderLocaleResolver();
    }
}
