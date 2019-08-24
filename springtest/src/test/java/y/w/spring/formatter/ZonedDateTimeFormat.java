package y.w.spring.formatter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ZonedDateTimeFormat
 *
 * @author ywang
 * @date 8/23/2019
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
public @interface ZonedDateTimeFormat
{
    ZonedDateTimeStyle style() default ZonedDateTimeStyle.ISO_ZONED_DATE_TIME;
}