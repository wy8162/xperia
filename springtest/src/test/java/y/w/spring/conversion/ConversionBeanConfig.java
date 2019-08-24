package y.w.spring.conversion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

/**
 * ConversionBeanConfig
 *
 * @author ywang
 * @date 8/22/2019
 */
@Configuration
@ImportResource("classpath:spring/converter-beans.xml")
public class ConversionBeanConfig
{
    @Bean
    public ConversionService conversionService()
    {
        DefaultConversionService service = new DefaultConversionService();
        service.addConverter(new ZonedDateTimeConverter());
        service.addConverter(new RankingConverter());
        service.addConverterFactory(new EntityConverterFactory());
        return service;
    }
}
