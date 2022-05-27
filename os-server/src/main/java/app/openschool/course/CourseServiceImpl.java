package app.openschool.course;

import app.openschool.course.api.dto.CourseInfoDto;
import app.openschool.course.api.mapper.CourseMapper;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

  private final CourseRepository courseRepository;

  public CourseServiceImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public Optional<CourseInfoDto> findCourseById(Long id) {
    Optional<Course> optionalCourse = courseRepository.findById(id);
    Optional<CourseInfoDto> optionalCourseInfoDto = Optional.empty();
    if (optionalCourse.isPresent()) {
      optionalCourseInfoDto = Optional.of(CourseMapper.toCourseInfoDto(optionalCourse.get()));
    }
    return optionalCourseInfoDto;
  }
}
