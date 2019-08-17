package y.w.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE) // needed for JSON and JPA
@Setter(value = AccessLevel.PACKAGE)
@Getter
public final class Course
{
    @Id
    @Column(name="COURSE_ID")
    private String id;
    private String courseName;

    @ManyToMany(mappedBy = "courses")
    @Builder.Default
    private List<Student> students = new ArrayList<>();

    // Bidirection OneToMany
    @OneToMany//(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "COURSE_ID")
    @Builder.Default
    private List<CourseRecord> records = new ArrayList<>();

    public Course add(Student s) { students.add(s); return this; }
    public Course add(CourseRecord c) { records.add(c); return this; }
}
