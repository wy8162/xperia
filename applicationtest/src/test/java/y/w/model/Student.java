package y.w.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor // needed for JSON and JPA
@Setter
@Getter
public final class Student
{
    @Id
    @Column(name = "STUDENT_ID")
    private Long id;
    private String name;

    @ManyToMany
    @JoinTable(name="student_course",
               joinColumns = { @JoinColumn(name = "fk_student") },
               inverseJoinColumns = { @JoinColumn(name = "fk_course")})
    @Builder.Default // This makes sure the following value is set properly instead of being set null by default constructor
    private List<Course> courses = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "STUDENT_ID")
    @Builder.Default
    private List<CourseRecord> records = new ArrayList<>();

    // For convenience
    public Student add(Course c) { courses.add(c); return this; }
    public Student add(CourseRecord c) { records.add(c); return this; }
}
