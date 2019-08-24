package y.w.spring.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * ZonedDateTimeFormatter
 *
 * @author ywang
 * @date 8/23/2019
 */
public class ZonedDateTimeFormatter implements Formatter<ZonedDateTime>
{
    // Default
    private DateTimeFormatter formatter; // DateTimeFormatter.ISO_ZONED_DATE_TIME or DateTimeFormatter.ISO_DATE_TIME;

    public ZonedDateTimeFormatter(DateTimeFormatter formatter)
    {
        this.formatter = formatter;
    }

    @Override public ZonedDateTime parse(String text, Locale locale) throws ParseException
    {
        return ZonedDateTime.parse(text, formatter).withZoneSameInstant(ZoneId.of("UTC"));
    }

    @Override public String print(ZonedDateTime object, Locale locale)
    {
        return formatter.toFormat().format(object);
    }
}
