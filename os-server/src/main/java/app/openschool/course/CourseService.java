package app.openschool.course;

import java.util.Optional;

public interface CourseService {
  Optional<Course> findCourseById(Long id);
}
