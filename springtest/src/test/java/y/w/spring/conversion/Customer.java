package y.w.spring.conversion;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Order
 *
 * @author ywang
 * @date 8/22/2019
 */
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class Customer extends Entity
{
    private String customerName;

    public Customer(String id, String customerName)
    {
        super(id);
        this.customerName = customerName;
    }
}
