package app.openschool.course;

import app.openschool.category.api.dto.CategoryDto;
import app.openschool.category.api.dto.PreferredCategoryDto;
import app.openschool.category.api.dto.SavePreferredCategoriesRequestDto;
import app.openschool.category.api.dto.SavePreferredCategoriesResponseDto;
import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.api.dto.UserCourseDto;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {

  Page<CategoryDto> findAllCategories(Pageable pageable);

  Map<String, List<PreferredCategoryDto>> findCategoriesByTitle(String title);

  List<CourseDto> getSuggestedCourses(Long userId);

  SavePreferredCategoriesResponseDto savePreferredCategories(
      SavePreferredCategoriesRequestDto savePreferredCategoriesDto);

  List<UserCourseDto> findUserCourses(Long userId, Long courseStatusId);
}
