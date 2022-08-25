package app.openschool.course;

import app.openschool.common.response.ResponseMessage;
import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.api.dto.CourseInfoDto;
import app.openschool.course.api.dto.CreateCourseRequest;
import app.openschool.course.api.mapper.CourseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @Operation(summary = "get course info", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Get details and information about the course"),
        @ApiResponse(
            responseCode = "404",
            description = "Invalid courseId supplied",
            content = @Content(schema = @Schema()))
      })
  @GetMapping("{id}")
  public ResponseEntity<CourseInfoDto> getCourseInfo(
      @Parameter(description = "Course id") @PathVariable Long id) {
    return courseService
        .findCourseById(id)
        .map(course -> ResponseEntity.ok(CourseMapper.toCourseInfoDto(course)))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(summary = "find all courses", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponse(
      responseCode = "200",
      description =
          "Returns paginated list of courses depending on passed filtering parameters "
              + "(by default returns all courses with size of pagination), "
              + "or empty list if no course have been found")
  @GetMapping
  public ResponseEntity<Page<CourseDto>> findAll(
      @Parameter(description = "Title of the course") @RequestParam(required = false)
          String courseTitle,
      @Parameter(description = "List of subCategory ids which were selected")
          @RequestParam(required = false)
          List<Long> subCategoryIds,
      @Parameter(description = "List of language ids which where selected")
          @RequestParam(required = false)
          List<Long> languageIds,
      @Parameter(description = "List of difficulty level ids which where selected")
          @RequestParam(required = false)
          List<Long> difficultyIds,
      @Parameter(
              description =
                  "Includes parameters page, size, and sort which is not required. "
                      + "Page results page you want to retrieve (0..N). "
                      + "Size is count of records per page(1..N). "
                      + "Sorting criteria in the format: property(,asc|desc). "
                      + "Default sort order is ascending. "
                      + "Multiple sort criteria are supported.")
          Pageable pageable) {
    return ResponseEntity.ok(
        CourseMapper.toCourseDtoPage(
            courseService.findAll(
                courseTitle, subCategoryIds, languageIds, difficultyIds, pageable)));
  }

  @Operation(summary = "add course", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Creates new course and returns that"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid category title supplied or image not provided",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Only user with admin role has access to this method",
            content = @Content(schema = @Schema()))
      })
  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping
  public ResponseEntity<CourseDto> add(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description =
                  "Request object for creating new course, which includes title, "
                      + "id of parent category, which is necessary to pass only when"
                      + " will be created a subcategory and image of category")
          @Valid
          @RequestBody
          CreateCourseRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(CourseMapper.toCourseDto(courseService.add(request)));
  }
}
