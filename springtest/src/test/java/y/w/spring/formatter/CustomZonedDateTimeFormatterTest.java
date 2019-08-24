package y.w.spring.formatter;

import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.validation.DataBinder;

/**
 * CustomZonedDateTimeFormatterTest
 *
 * @author ywang
 * @date 8/23/2019
 */
public class CustomZonedDateTimeFormatterTest
{
    @Test
    public void zonedDateTimeAnnotationTest()
    {
        DefaultFormattingConversionService service =
                new DefaultFormattingConversionService();
        service.addFormatterForFieldAnnotation(
                new ZonedDateTimeAnnotationFormatterFactory());

        BeanUsingZonedDateTimeAnnotation bean = new BeanUsingZonedDateTimeAnnotation();
        DataBinder dataBinder = new DataBinder(bean);
        dataBinder.setConversionService(service);

        MutablePropertyValues mpv = new MutablePropertyValues();
        mpv.add("dateTime", "2019-08-01T21:21:21.000Z");

        dataBinder.bind(mpv);
        dataBinder.getBindingResult()
                .getModel()
                .entrySet()
                .forEach(System.out::println);

        System.out.println(bean.toString());
    }
}
