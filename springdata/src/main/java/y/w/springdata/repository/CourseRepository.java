package y.w.springdata.repository;

import org.springframework.data.repository.CrudRepository;
import y.w.springdata.model.Course;

/**
 * CourseRepository
 */
public interface CourseRepository extends CrudRepository<Course, String>
{
}
