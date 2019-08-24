package y.w.spring.conversion;

import lombok.extern.log4j.Log4j;
import org.springframework.core.convert.converter.Converter;

/**
 * RankingConverter
 *
 * @author ywang
 * @date 8/22/2019
 */
@Log4j
public class RankingConverter implements Converter<String, Ranking>
{
    @Override public Ranking convert(String source)
    {
        log.info("Convert Ranking");
        return Enum.valueOf(Ranking.class, source.trim());
    }
}
