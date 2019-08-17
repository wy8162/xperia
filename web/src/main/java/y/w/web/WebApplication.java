package y.w.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * The following are automatically registered
 * <li>
 *     The equivalency in application.properties can be used as well.
 *     <td>CharacterEncodingFilter - or setting spring.http.encoding.charset to UTF-8. Can be disabled by spring.http.encoding.enabled=true</td>
 *     <td>HiddenHttpMethodFilter - or spring.mvc.hiddenmethod.filter.eabled=true</td>
 *     <td>FormContentFilter - or spring.mvc.formcontent.filter.enabled=true</td>
 *     <td>RequestContextFilter</td>
 * </li>
 */
@SpringBootApplication
public class WebApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(WebApplication.class, args);
    }

}
