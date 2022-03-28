package app.openschool.coursemanagement;

import app.openschool.coursemanagement.api.dto.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:3000")
public class CourseController {

  private final CourseService courseService;

  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  @GetMapping("/categories")
  @Operation(summary = "find all categories")
  public ResponseEntity<Page<CategoryDto>> allCategories(Pageable pageable) {
    return ResponseEntity.ok(this.courseService.findAllCategories(pageable));
  }
}
