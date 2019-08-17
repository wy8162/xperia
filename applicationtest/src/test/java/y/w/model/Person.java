package y.w.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * Person
 *
 * @author ywang
 * @date 8/8/2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person
{
    private String firstName;
    private String lastName;
    private Date   birthDay;
}
