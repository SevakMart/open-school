package app.openschool.course;

import app.openschool.course.api.dto.CourseInfoDto;
import java.util.Optional;

public interface CourseService {
  Optional<CourseInfoDto> findCourseById(Long id);
}
