package app.openschool.course;

import app.openschool.course.api.dto.CourseDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {

  Page<CourseDto> findAll(
      String courseTitle,
      List<Long> subCategoryIds,
      List<Long> languageIds,
      List<Long> difficultyIds,
      Pageable pageable);
}
