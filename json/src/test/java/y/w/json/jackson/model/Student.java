package y.w.json.jackson.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Student - A simple one demonstrating that Jackson can do it without
 * any help.
 *
 * @author ywang
 * @date 8/5/2019
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Student
{
    /**
     * All the setter and getter generated match the field names exactly.
     * This is must for Jackscon to process it correctly.
     */
    private String name;
    private Date   birthday;
    private int    age;
    private List<String> courses;
    private Map<String, Integer> courseScore;
}
