package y.w.spring.restful;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
//@JsonDeserialize(using = MultiplicationResultAttemptDeserializer.class)
public class MutualFundDTO
{
    @JsonProperty("message")
    private final String message;

    @JsonProperty("symbols_requested")
    private final int symbolsRequested;

    @JsonProperty("symbols_returned")
    private final int symbolsReturned;

    @JsonProperty("data")
    private final  List<FundDTO> funds;

    MutualFundDTO()
    {
        this(null, 0, 0, null);
    }
}
