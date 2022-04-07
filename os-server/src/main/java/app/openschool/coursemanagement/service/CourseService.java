package app.openschool.coursemanagement.service;

import app.openschool.coursemanagement.api.dto.CategoryDto;
import app.openschool.coursemanagement.api.dto.CategoryDtoForRegistration;
import app.openschool.coursemanagement.api.dto.CourseDto;
import app.openschool.coursemanagement.api.dto.CourseOfUserDto;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

public interface CourseService {

  Page<CategoryDto> findAllCategories(Pageable pageable);

  Map<String, List<CategoryDtoForRegistration>> findCategoriesByTitle(String title);

  List<CourseDto> getSuggestedCourses(@RequestParam Long userId);

  List<CourseOfUserDto> coursesOfUser(Long userId, Long courseStatusId);
}
