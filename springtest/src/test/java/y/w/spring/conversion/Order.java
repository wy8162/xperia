package y.w.spring.conversion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;

/**
 * Order
 *
 * @author ywang
 * @date 8/22/2019
 */
@Setter
@Getter
@ToString
public class Order
{
    private Ranking       ranking;
    private Customer      customer;
    private LineItem      lineItem;
    private ZonedDateTime dateTime;

    public Order(Ranking ranking, Customer customer, LineItem lineItem, ZonedDateTime dateTime)
    {
        this.ranking = ranking;
        this.customer = customer;
        this.lineItem = lineItem;
        this.dateTime = dateTime;
    }
}
