package app.openschool.course;

import app.openschool.course.api.dto.CourseInfoDto;
import app.openschool.course.api.mapper.CourseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

  private final CourseService courseService;

  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  @GetMapping("{id}")
  @Operation(summary = "get course info", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<CourseInfoDto> getCourseInfo(@PathVariable Long id) {
    return courseService
        .findCourseById(id)
        .map(course -> ResponseEntity.ok(CourseMapper.toCourseInfoDto(course)))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
