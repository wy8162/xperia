package y.w.spring.conversion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity
 *
 * @author ywang
 * @date 8/22/2019
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class Entity
{
    private String id;

    public Entity(String id)
    {
        this.id = id;
    }
}
