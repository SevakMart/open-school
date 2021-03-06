package app.openschool.course;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  @Override
  public Page<Course> findAll(
      String courseTitle,
      List<Long> subCategoryIds,
      List<Long> languageIds,
      List<Long> difficultyIds,
      Pageable pageable) {
    return courseRepository.findAll(
        courseTitle, subCategoryIds, languageIds, difficultyIds, pageable);
  }
}
