package y.w.json.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;
import y.w.json.jackson.model.Student;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * StudentTest
 *
 * @author ywang
 * @date 8/5/2019
 */
@Log4j
public class SimpleStudentTest
{
    private final String COURSE_PHYSICS = "physics";
    private final String COURSE_MATH    = "math";

    private Student student;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup()
    {
        student = Student.builder()
                .name("Jack")
                .age(15)
                .birthday(new Date())
                .courses(Arrays.asList(COURSE_PHYSICS, COURSE_MATH))
                // Guava ImmutableMap.of(1, "a") alternatively
                .courseScore(ImmutableMap.of(COURSE_PHYSICS, 90, COURSE_MATH, 95))
                .build();
    }

    @Test
    public void serializeDeserializeTest() throws IOException
    {
        // Serialize Java object to String
        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, student);

        String json = stringWriter.toString();
        log.info(json);

        // Deserialize JSON to Java object
        Student s = objectMapper.readValue(json, Student.class);

        assertThat(s.equals(student)).isTrue(); // can do this b/c of @EqualsAndHashCode in Student.

        // Alternatively, using StringReader
        s = objectMapper.readValue(new StringReader(json), Student.class);
    }

    @Test
    public void genericTest() throws IOException
    {
        List<Student> students = Arrays.asList(student, student);

        // Serialize List<Student>
        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, students);
        String json = stringWriter.toString();

        log.info("Students deserialized: " + json);

        // Now, deserialize it
        List<Student> s = objectMapper.readValue(json, new TypeReference<List<Student>>(){});

        assertThat(students).containsAll(s);
    }
}
