package y.w.springdata.model.dto;

import y.w.springdata.model.Rating;

/**
 * Projection interface StudentCourseRatingDTO
 */
public interface StudentCourseRatingDTO
{
    String getName();
    String getCourse();
    Rating getRating();

    void setName(String name);
    void setCourse(String name);
    void setRating(String name);
}
