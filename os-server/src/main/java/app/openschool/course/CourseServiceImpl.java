package app.openschool.course;

import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.api.mapper.CourseMapper;
import java.util.List;
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
  public Page<CourseDto> findAll(
      String courseTitle,
      List<Long> subCategoryIds,
      List<Long> languageIds,
      List<Long> difficultyIds,
      Pageable pageable) {
    return CourseMapper.toCourseDtoPage(
        courseRepository.findAll(
            courseTitle, subCategoryIds, languageIds, difficultyIds, pageable));
  }
}
