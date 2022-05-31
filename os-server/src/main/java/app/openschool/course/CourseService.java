package app.openschool.course;

import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.api.dto.CourseSearchingFeaturesDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {

  CourseSearchingFeaturesDto getCourseSearchingFeatures();

  Page<CourseDto> searchCourses(
      Pageable pageable,
      String courseTitle,
      List<Long> subCategoryIds,
      List<Long> languageIds,
      List<Long> difficultyIds);

  Optional<Course> findById(Long courseId);
}
