package y.w.spring.restful;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
// instruct our @RestTemplate’s message converter to use a special deserializer to read the JSON data
@JsonDeserialize(using = ForexUsdDeserializer.class)
public class ForexUsd
{
    private final double usdEur;

    // Default constructor needed for JSON deserializer
    public ForexUsd()
    {
        this(0.0);
    }
}
