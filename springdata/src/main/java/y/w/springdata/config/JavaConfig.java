package y.w.springdata.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@ComponentScan
@Slf4j
public class JavaConfig extends WebMvcConfigurationSupport
{
    /**
     * RESTful will be able to respond with either JSON or XML
     * http://localhost:8080/springdata/api/order?mediaType=xml
     * http://localhost:8080/springdata/api/order?mediaType=json
     * Need Jackson-dataformat-xml dependency
     *
     * @param configurer
     */
    @Override
    public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false).
                favorParameter(true).
                parameterName("mediaType").
                ignoreAcceptHeader(true).
                useJaf(false).
                defaultContentType(MediaType.APPLICATION_JSON).
                mediaType("xml", MediaType.APPLICATION_XML).
                mediaType("json", MediaType.APPLICATION_JSON);
    }
}
