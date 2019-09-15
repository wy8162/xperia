package y.w.springdata.service;

import y.w.springdata.model.Student;
import y.w.springdata.model.dto.StudentCourseRatingDTO;
import y.w.springdata.model.dto.StudentDTO;

import java.util.List;
import java.util.Optional;

/**
 * StudentService
 */
public interface StudentService
{
    List<Student> findAll();

    List<StudentDTO> findAllStudents();

    Optional<Student> findStudentById(Long id);

    List<StudentCourseRatingDTO> findStudentCourseRating();
}
