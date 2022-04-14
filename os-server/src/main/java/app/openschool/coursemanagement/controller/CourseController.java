package app.openschool.coursemanagement.controller;

import app.openschool.coursemanagement.api.dto.CategoryDto;
import app.openschool.coursemanagement.api.dto.CourseDto;
import app.openschool.coursemanagement.api.dto.PreferredCategoryDto;
import app.openschool.coursemanagement.api.dto.SavePreferredCategoriesRequestDto;
import app.openschool.coursemanagement.api.dto.SavePreferredCategoriesResponseDto;
import app.openschool.coursemanagement.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CourseController {

  private final CourseService courseService;

  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  @GetMapping("/categories")
  @Operation(summary = "find all categories", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Page<CategoryDto>> allCategories(Pageable pageable) {
    return ResponseEntity.ok(this.courseService.findAllCategories(pageable));
  }

  @GetMapping("/category-search")
  @Operation(
      summary = "find all categories by title mapped by subcategories",
      security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Map<String, List<PreferredCategoryDto>>> findCategoriesByTitle(
      String title) {
    return ResponseEntity.ok(courseService.findCategoriesByTitle(title));
  }

  @GetMapping("/courses/suggested")
  @Operation(
      summary = "find suggested courses",
      security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<List<CourseDto>> getSuggestedCourses(@RequestParam Long userId) {
    return ResponseEntity.ok(this.courseService.getSuggestedCourses(userId));
  }

  @PostMapping("/choose-categories")
  @Operation(
      summary = "save preferred categories",
      security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<SavePreferredCategoriesResponseDto> savePreferredCategories(
      @RequestBody SavePreferredCategoriesRequestDto savePreferredCategoriesRequestDto) {

    return ResponseEntity.ok(
        courseService.savePreferredCategories(savePreferredCategoriesRequestDto));
  }
}
