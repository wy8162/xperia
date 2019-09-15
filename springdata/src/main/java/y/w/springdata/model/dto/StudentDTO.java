package y.w.springdata.model.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * StudentDTO - Student has collections of courses and records. Hibernate laziness will cause
 * JSON fail to serialize. So create this DTO for it.
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE) // needed for JSON and JPA
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class StudentDTO
{
    private Long id;
    private String name;

    public StudentDTO(Long id, String name)
    {
        this.id = id;
        this.name = name;
    }
}
