package y.w.springdata.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import y.w.springdata.model.Course;
import y.w.springdata.model.CourseRecord;
import y.w.springdata.model.Student;
import y.w.springdata.model.dto.StudentCourseRatingDTO;
import y.w.springdata.model.dto.StudentDTO;
import y.w.springdata.repository.CourseRecordRepository;
import y.w.springdata.repository.CourseRepository;
import y.w.springdata.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * StudentServiceImpl
 */
@Service
public class StudentServiceImpl implements StudentService
{
    private StudentRepository repository;
    private CourseRepository courseRepository;
    private CourseRecordRepository recordRepositor;

    public StudentServiceImpl(StudentRepository repository, CourseRepository courseRepository,
            CourseRecordRepository recordRepositor)
    {
        this.repository = repository;
        this.courseRepository = courseRepository;
        this.recordRepositor = recordRepositor;
    }

    @Autowired


    @Override public List<Student> findAll()
    {
        List<Student> students = Lists.newArrayList(repository.findAll());
        for (Student s : students) {
            List<CourseRecord> records = Lists.newArrayList(recordRepositor.findAllByStudentId(s.getId()));
            List<Course> courses = new ArrayList<>();

            for (CourseRecord r : records) {
                Optional<Course> course = courseRepository.findById(r.getCourse().getId());
                course.ifPresent(courses::add);
            }
            s.setCourses(courses);
            s.setRecords(records);
        }
        return students;
    }

    @Override public List<StudentDTO> findAllStudents()
    {
        return repository.findAllStudent();
    }

    @Override public Optional<Student> findStudentById(Long id)
    {
        return repository.findById(id);
    }

    @Override public List<StudentCourseRatingDTO> findStudentCourseRating()
    {
        return repository.findAllStudentCourseRating();
    }
}
