package y.w.spring.restful;

import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class GsonMutualFundDTO
{
    @SerializedName("message")
    private final String message;

    @SerializedName("symbols_requested")
    private final int symbolsRequested;

    @SerializedName("symbols_returned")
    private final int symbolsReturned;

    @SerializedName("data")
    private final  List<GsonFundDTO> funds;

    GsonMutualFundDTO()
    {
        this(null, 0, 0, null);
    }
}
