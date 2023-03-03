package app.openschool.course;

import app.openschool.course.api.dto.CourseInfoDto;
import app.openschool.course.api.dto.CreateCourseRequest;
import app.openschool.course.api.dto.UpdateCourseRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {

  CourseInfoDto findCourseById(Long id);

  Page<Course> findAll(
      String courseTitle,
      List<Long> subCategoryIds,
      List<Long> languageIds,
      List<Long> difficultyIds,
      Pageable pageable);

  Course add(CreateCourseRequest request);

  Course update(Long courseId, UpdateCourseRequest request);

  void delete(Long courseId);
}
