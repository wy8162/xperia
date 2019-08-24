package y.w.spring.conversion;

import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.convert.support.DefaultConversionService;

import java.io.IOException;

/**
 * SpringConversionServiceTest
 *
 * @author ywang
 * @date 8/22/2019
 */
@Log4j
public class SpringConversionServiceTest
{
    @Test
    public void conversionServiceTest() throws IOException
    {
//        DefaultConversionService service = new DefaultConversionService();
//        service.addConverter(new ZonedDateTimeConverter());
//        service.addConverter(new RankingConverter());
//        service.addConverterFactory(new EntityConverterFactory());
//
//        service.convert("{\"id\":\"1234\",\"customerName\":\"Jack\"}", Customer.class);

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ConversionBeanConfig.class);

        Order order = ctx.getBean("order", Order.class);

        log.info("Order : " + order.toString());
    }
}
