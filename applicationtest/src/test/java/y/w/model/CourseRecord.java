package y.w.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Identifies {@link Student} and {@link Course} relationship.
 */
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor // needed for JSON and JPA
@Setter
@Getter
public final class CourseRecord
{
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    //    @OneToOne//(cascade = CascadeType.ALL)
    //    @JoinColumn(name = "COURSE_ID", referencedColumnName = "COURSE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COURSE_ID")
    private Course course;

    //    @OneToOne//(cascade = CascadeType.ALL)
    //    @JoinColumn(name = "STUDENT_ID", referencedColumnName = "STUDENT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDENT_ID")
    private Student student;
}
