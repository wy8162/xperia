package y.w.springdata.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import y.w.springdata.model.CourseRecord;

import java.util.List;

public interface CourseRecordRepository extends CrudRepository<CourseRecord, Long>, QueryByExampleExecutor<CourseRecord>
{
    /**
     * @return list of courses registered by a student
     */
    List<CourseRecord> findAllByStudentId(Long id);

    /**
     * @return list of students who registered a course
     */
    List<CourseRecord> findAllByCourseId(String courseId);
}
