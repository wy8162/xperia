package y.w.webapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * RootApplicationContext - for domain, service, security
 *
 * @author ywang
 * @date 8/17/2019
 */
@Configuration
@ComponentScan(basePackages = {
        "y.w.webapp.model",
        "y.w.webapp.security",
})
public class RootApplicationContext
{
}
