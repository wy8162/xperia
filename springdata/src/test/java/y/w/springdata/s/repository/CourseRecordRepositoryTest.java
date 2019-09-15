package y.w.springdata.s.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import y.w.springdata.config.JavaConfig;
import y.w.springdata.model.Course;
import y.w.springdata.model.CourseRecord;
import y.w.springdata.model.Rating;
import y.w.springdata.repository.CourseRecordRepository;

/**
 * CourseRecordRepositoryTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(JavaConfig.class)
@Profile("test")
@Sql("classpath:testdata.sql")
@Slf4j
public class CourseRecordRepositoryTest
{
    @Autowired
    private CourseRecordRepository courseRecordRepository;

    @Test
    public void findAllTest()
    {
        Iterable<CourseRecord> ratings = courseRecordRepository.findAll();

        ratings.forEach((CourseRecord r) ->
        {
            log.info(String.format("This rating = %s %s %s",
                    r.getRating(), r.getCourse().getCourseName(), r.getStudent().getName()));
        });
    }

    @Test
    public void queryByExampleTest()
    {
        Example<CourseRecord> example = Example.of(CourseRecord.builder().rating(Rating.GOOD).build());

        // an exact match will be performed on all non-null properties
        Iterable<CourseRecord> rs = courseRecordRepository.findAll(example);

        rs.forEach((CourseRecord r) ->
        {
            log.info(String.format("This rating = %s %s %s",
                    r.getRating(), r.getCourse().getCourseName(), r.getStudent().getName()));
        });

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase();
        example = Example.of(CourseRecord.builder().course(Course.builder().id("chm01").build()).build(), matcher);

        rs = courseRecordRepository.findAll(example);

        rs.forEach((CourseRecord r) ->
        {
            log.info(String.format("This rating = %s %s %s",
                    r.getRating(), r.getCourse().getCourseName(), r.getStudent().getName()));
        });
    }
}
