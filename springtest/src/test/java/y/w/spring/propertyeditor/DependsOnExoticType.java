package y.w.spring.propertyeditor;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * ExoticType
 *
 * @author ywang
 * @date 8/21/2019
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DependsOnExoticType
{
    private ExoticType type;
    private Date       date;
}
