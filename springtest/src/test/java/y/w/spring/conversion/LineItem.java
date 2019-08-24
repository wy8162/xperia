package y.w.spring.conversion;

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
@ToString
@NoArgsConstructor
public class LineItem extends Entity
{
    private String itemName;

    public LineItem(String id, String itemName)
    {
        super(id);
        this.itemName = itemName;
    }
}
