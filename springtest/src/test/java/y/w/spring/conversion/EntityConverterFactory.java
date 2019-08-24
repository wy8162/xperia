package y.w.spring.conversion;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.io.IOException;
import java.io.StringReader;

/**
 * RankingConverterFactory - Convert JSON String to any subclasses of Entity
 *
 * @author ywang
 * @date 8/22/2019
 */
@Log4j
public class EntityConverterFactory implements ConverterFactory<String, Entity>
{
    @Override public <T extends Entity> Converter<String, T> getConverter(Class<T> targetType)
    {
        log.info("Inside y.w.spring.conversion.EntityConverterFactory.getConverter");
        return new JsonToEntityConverter<>(targetType);
    }

    private static final class JsonToEntityConverter<T extends Entity> implements Converter<String, T>
    {
        private Class<T> type;

        public JsonToEntityConverter(Class<T> type)
        {
            this.type = type;
        }

        @Override
        public T convert(String source)
        {
            log.info(String.format("Inside y.w.spring.conversion.EntityConverterFactory.JsonToEntityConverter.convert: \n%s\n%s", type.getName(), source));
            if (source.isEmpty()) {
                return null;
            }

            ObjectMapper objectMapper = new ObjectMapper();

            T t = null;
            try
            {
                t = objectMapper.readValue(new StringReader(source), type);
            }
            catch (IOException e)
            {
                log.error(String.format("Failed to convert % to %", source, type.getName()));
            }
            return t;
        }
    }
}
