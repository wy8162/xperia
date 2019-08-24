package y.w.spring.formatter;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * ZonedDateTimeFormatter
 *
 * @author ywang
 * @date 8/23/2019
 */
public class ZonedDateTimeAnnotationFormatterFactory
    implements AnnotationFormatterFactory<ZonedDateTimeFormat>
{

    @Override public Set<Class<?>> getFieldTypes()
    {
        return new HashSet<>(Arrays.asList(ZonedDateTime.class));
    }

    @Override public Printer<?> getPrinter(ZonedDateTimeFormat annotation, Class<?> fieldType)
    {
        return getFormatter(annotation, fieldType);
    }

    @Override public Parser<?> getParser(ZonedDateTimeFormat annotation, Class<?> fieldType)
    {
        return getFormatter(annotation, fieldType);
    }

    private Formatter<?> getFormatter(ZonedDateTimeFormat annotation, Class<?> fieldType)
    {
        switch (annotation.style())
        {
        case ISO_ZONED_DATE_TIME:
            return new ZonedDateTimeFormatter(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        case ISO_DATE_TIME:
            return new ZonedDateTimeFormatter(DateTimeFormatter.ISO_DATE_TIME);
        }
        throw new IllegalArgumentException("Invalid ZonedDateTime formatter: " + annotation.style());
    }
}
