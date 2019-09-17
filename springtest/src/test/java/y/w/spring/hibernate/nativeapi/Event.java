package y.w.spring.hibernate.nativeapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Event
 *
 * @author ywang
 * @date 9/16/2019
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
class Event
{
    private static final long serialVersionUID = 1075229082774978863L;
    private Long     id;
    private String   name;
    private int      duration;
    private Date     startDate;
    private Location location;
}
