package y.w.springdata.s.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import y.w.springdata.config.JavaConfig;
import y.w.springdata.model.Student;
import y.w.springdata.repository.CourseRecordRepository;
import y.w.springdata.repository.CourseRepository;
import y.w.springdata.repository.StudentRepository;
import y.w.springdata.service.StudentService;
import y.w.springdata.service.StudentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * StudentServiceTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(JavaConfig.class)
@Profile("test")
@Sql("classpath:testdata.sql")
@Slf4j
public class StudentServiceTest
{
    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private CourseRecordRepository courseRecordRepository;

    @MockBean
    private CourseRepository courseRepository;

    private StudentService studentService;

    @Before
    public void setup()
    {
        studentService = new StudentServiceImpl(studentRepository, courseRepository, courseRecordRepository);
    }

    @Test
    public void findAllTest()
    {
        List<Student> students = Lists.newArrayList(Student.builder().id(1L).name("someone").build());

        given(studentRepository.findAll()).willReturn(students);

        List<Student> results = studentService.findAll();

        assertThat(results).containsExactlyElementsOf(students);
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void findByStudentById()
    {
        Long id = 1L;
        Student student = Student.builder().name("name").build();

        given(studentRepository.findById(id)).willReturn(Optional.of(student));

        Optional<Student> r =studentService.findStudentById(id);

        assertThat(r).containsSame(student);
        verify(studentRepository, times(1)).findById(id);
    }
}
