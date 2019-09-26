package y.w.springdata.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import y.w.springdata.model.Student;
import y.w.springdata.model.dto.StudentCourseRatingDTO;
import y.w.springdata.model.dto.StudentDTO;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Long>
{

    // Have to use interface StudentCourseRatingDTO instead of value object.
    @Query(value = "select s.name as name, c.course_name as course, r.rating as rating " +
                   "from student s, course_record r, course c " +
                   "where s.student_id = r.student_id and r.course_id = c.course_id",
           nativeQuery = true)
    List<StudentCourseRatingDTO> findAllStudentCourseRating();

    @Query("SELECT new y.w.springdata.model.dto.StudentDTO(s.id, s.name) FROM Student s")
    List<StudentDTO>  findAllStudent();

    // Uses the @NamedQuery defined in y.w.springdata.model.Student
    List<Student> fetchByName(@Param("name") String name);
}