package y.w.spring.formatter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;

/**
 * Usage
 *
 * @author ywang
 * @date 8/23/2019
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BeanUsingZonedDateTimeAnnotation
{
    @ZonedDateTimeFormat(style = ZonedDateTimeStyle.ISO_ZONED_DATE_TIME)
    private ZonedDateTime dateTime;
}
