package y.w.springdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import y.w.springdata.model.Student;
import y.w.springdata.model.dto.StudentCourseRatingDTO;
import y.w.springdata.model.dto.StudentDTO;
import y.w.springdata.service.StudentService;

import java.util.List;

/**
 * ServiceRestController
 */
@RestController
@RequestMapping("/api/v1")
public class StudentRestController
{
    private final StudentService studentService;

    @Autowired
    public StudentRestController(StudentService studentService)
    {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<StudentDTO> findAll()
    {
        List<StudentDTO> students = studentService.findAllStudents();
        return students;
    }

    @GetMapping("/students/all")
    public List<Student> findAllStudents()
    {
        List<Student> students = studentService.findAll();
        return students;
    }

    @GetMapping("/ratings")
    public List<StudentCourseRatingDTO> fetchStudentCourseRating()
    {
        List<StudentCourseRatingDTO> r = studentService.findStudentCourseRating();

        return r;
    }
}
