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
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ConversionBeanConfig.class);

        Order order = ctx.getBean("order", Order.class);

        log.info("Order : " + order.toString());
    }
}
