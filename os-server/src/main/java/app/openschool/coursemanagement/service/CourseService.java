package app.openschool.coursemanagement.service;

import app.openschool.coursemanagement.api.dto.CategoryDto;
import app.openschool.coursemanagement.api.dto.CourseDto;
import app.openschool.coursemanagement.api.dto.PreferredCategoryDto;
import app.openschool.coursemanagement.api.dto.SavePreferredCategoriesRequestDto;
import app.openschool.coursemanagement.api.dto.SavePreferredCategoriesResponseDto;
import app.openschool.coursemanagement.api.dto.UserCourseDto;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {

  Page<CategoryDto> findAllCategories(Pageable pageable);

  Map<String, List<PreferredCategoryDto>> findCategoriesByTitle(String title);

  List<CourseDto> getSuggestedCourses(@RequestParam Long userId);

  SavePreferredCategoriesResponseDto savePreferredCategories(
      SavePreferredCategoriesRequestDto savePreferredCategoriesDto);
  List<CourseDto> getSuggestedCourses(Long userId);

  List<UserCourseDto> findUserCourses(Long userId, Long courseStatusId);
}
