package y.w.spring.conversion;

import lombok.extern.log4j.Log4j;
import org.springframework.core.convert.converter.Converter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ZonedDateTimeConverter
 *
 * @author ywang
 * @date 8/22/2019
 */
@Log4j
public class ZonedDateTimeConverter implements Converter<String, ZonedDateTime>
{
    @Override public ZonedDateTime convert(String source)
    {
        log.info("Convert ZonedDateTime");
        return ZonedDateTime.parse(source, DateTimeFormatter.ISO_ZONED_DATE_TIME).withZoneSameInstant(ZoneId.of("UTC"));
    }
}
