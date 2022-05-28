package app.openschool.course;

import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.api.dto.CourseSearchingFeaturesDto;
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

  @GetMapping("/features")
  public ResponseEntity<CourseSearchingFeaturesDto> getCourseSearchingFeatures() {
    return ResponseEntity.ok(this.courseService.getCourseSearchingFeatures());
  }

  @GetMapping("/searched")
  public ResponseEntity<Page<CourseDto>> searchCourses(
      Pageable pageable,
      @RequestParam(required = false) String courseTitle,
      @RequestParam(required = false) List<Long> subCategoryIds,
      @RequestParam(required = false) List<Long> languageIds,
      @RequestParam(required = false) List<Long> difficultyIds) {
    return ResponseEntity.ok(
        this.courseService.searchCourses(
            pageable, courseTitle, subCategoryIds, languageIds, difficultyIds));
  }
}
