package app.openschool.course;

import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

  private final CourseRepository courseRepository;

  public CourseServiceImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public Optional<Course> findCourseById(Long id) {
    return courseRepository.findById(id);
  }
}
