package app.openschool.course;

import app.openschool.course.api.dto.CourseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

  private final CourseService courseService;

  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  @GetMapping
  @Operation(summary = "find all courses", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Page<CourseDto>> findAll(
      @RequestParam(required = false) String courseTitle,
      @RequestParam(required = false) List<Long> subCategoryIds,
      @RequestParam(required = false) List<Long> languageIds,
      @RequestParam(required = false) List<Long> difficultyIds,
      Pageable pageable) {
    return ResponseEntity.ok(
        this.courseService.findAll(
            courseTitle, subCategoryIds, languageIds, difficultyIds, pageable));
  }
}
