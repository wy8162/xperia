package y.w.spring.restful;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Date;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class GsonFundDTO
{
    @JsonProperty
    private final String symbol;
    @JsonProperty
    private final String name;
    @JsonProperty
    private final double price;
    @JsonProperty
    private final double close_yesterday;
    @JsonProperty
    private final double return_ytd;
    @JsonProperty
    private final double net_assets;
    @JsonProperty
    private final double change_asset_value;
    @JsonProperty
    private final double change_pct;
    @JsonProperty
    private final double yield_pct;
    @JsonProperty
    private final double return_day;
    @JsonProperty
    private final double return_1week;
    @JsonProperty
    private final double return_4week;
    @JsonProperty
    private final double return_13week;
    @JsonProperty
    private final double return_52week;
    @JsonProperty
    private final double return_156week;
    @JsonProperty
    private final double return_260week;
    @JsonProperty
    private final double income_dividend;
    @JsonProperty
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private final Date   income_dividend_date;
    @JsonProperty
    private final double capital_gain;
    @JsonProperty
    private final double expense_ratio;

    // Needed for Jackson deserializer
    public GsonFundDTO()
    {
        this(null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, 0, 0);
    }
}
