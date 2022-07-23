package app.openschool.user;

import app.openschool.auth.AuthService;
import app.openschool.category.api.dto.PreferredCategoryDto;
import app.openschool.common.response.ResponseMessage;
import app.openschool.course.Course;
import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.api.dto.EnrolledCourseOverviewDto;
import app.openschool.course.api.dto.UserCourseDto;
import app.openschool.course.api.dto.UserSavedCourseRequest;
import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.course.api.mapper.EnrolledCourseMapper;
import app.openschool.course.api.mapper.UserCourseMapper;
import app.openschool.user.api.dto.UserWithSavedMentorsDto;
import app.openschool.user.api.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;
  private final AuthService authService;

  public UserController(UserService userService, AuthService authService) {
    this.userService = userService;
    this.authService = authService;
  }

  @Operation(
      summary = "find suggested courses",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description =
                "Suggests 4 courses for user ordered by course rating desc. "
                    + "If user has selected any category or categories "
                    + "the courses will be suggested by corresponding categories. "
                    + "If no category is selected will be suggested random courses."
                    + "If less then 4 courses exist will return "
                    + "corresponding count of courses or empty list."),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid userId supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @GetMapping("/{userId}/courses/suggested")
  public ResponseEntity<List<CourseDto>> getSuggestedCourses(
      @Parameter(description = "User's id whom will be suggested courses") @PathVariable
          Long userId) {
    return ResponseEntity.ok(CourseMapper.toCourseDtoList(userService.getSuggestedCourses(userId)));
  }

  @Operation(
      summary = "save preferred categories",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "The preferred categories have been saved"),
        @ApiResponse(
            responseCode = "400",
            description = "invalid userId or categoryId supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @PostMapping("/{userId}/categories")
  public ResponseEntity<Set<PreferredCategoryDto>> savePreferredCategories(
      @Parameter(description = "User's id whose chosen categories will be saved") @PathVariable
          Long userId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description =
                  "Ids of chosen categories which will be saved as user's preferred categories")
          @RequestBody
          Set<Long> categoryIds) {
    return ResponseEntity.ok(userService.savePreferredCategories(userId, categoryIds));
  }

  @Operation(
      summary = "find user's enrolled courses by course status",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description =
                "Returns list of user's enrolled courses or "
                    + "empty list if no course have been found"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid userId supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @GetMapping("/{userId}/courses/enrolled")
  public ResponseEntity<List<UserCourseDto>> findEnrolledCourses(
      @Parameter(description = "User's id whose enrolled courses will be returned") @PathVariable
          Long userId,
      @Parameter(
              description =
                  "Id of enrolled course status. If status id is provided will be returned "
                      + "user's enrolled courses with relevant status, "
                      + "otherwise user's all enrolled courses.")
          @RequestParam(required = false)
          Long courseStatusId) {
    return ResponseEntity.ok(
        UserCourseMapper.toUserCourseDtoList(
            userService.findEnrolledCourses(userId, courseStatusId)));
  }

  @Operation(
      summary = "find user's saved courses",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description =
                "Returns paginated list of user's saved courses or "
                    + "empty list if no course has been found"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid userId supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @GetMapping("/{userId}/courses/saved")
  public ResponseEntity<Page<CourseDto>> findSavedCourses(
      @Parameter(description = "User's id whose saved courses will be returned") @PathVariable
          Long userId,
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
            userService.findSavedCourses(
                userId, courseTitle, subCategoryIds, languageIds, difficultyIds, pageable)));
  }

  @Operation(summary = "save course", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "The course have been saved"),
        @ApiResponse(
            responseCode = "400",
            description = "invalid userId or courseId supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @PostMapping("/{userId}/courses/saved")
  public ResponseEntity<CourseDto> saveCourse(
      @Parameter(description = "User's id whose chosen course will be saved") @PathVariable
          Long userId,
      @Parameter(description = "Request object which contains id of the course which will be saved")
          @Valid
          @RequestBody
          UserSavedCourseRequest userSavedCourseRequest,
      @Parameter(
              description =
                  "Object which may contain errors depending on passed null value "
                      + "to the courseID field of userSavedCourseRequest")
          BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new IllegalArgumentException();
    }
    Course savedCourse = userService.saveCourse(userId, userSavedCourseRequest.getCourseId());
    return ResponseEntity.ok(CourseMapper.toCourseDto(savedCourse));
  }

  @Operation(
      summary = "delete course from user's saved courses",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Deleted the saved course"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid userId or courseId supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @DeleteMapping("/{userId}/courses/{courseId}/saved")
  public ResponseEntity<CourseDto> deleteCourse(
      @Parameter(description = "User's id whose saved course will be deleted") @PathVariable
          Long userId,
      @Parameter(description = "Id of user's saved course which will be deleted") @PathVariable
          Long courseId) {
    Course deletedCourse = userService.deleteCourse(userId, courseId);
    return ResponseEntity.ok(CourseMapper.toCourseDto(deletedCourse));
  }

  @Operation(summary = "enroll course", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "The course has been enrolled"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid userId or courseId supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @PostMapping("/{userId}/courses/{courseId}")
  public ResponseEntity<CourseDto> enrollCourse(
      @Parameter(description = "User's id who enrolls the course") @PathVariable long userId,
      @Parameter(description = "Id of the course which will be enrolled") @PathVariable
          long courseId) {
    User user = authService.validateUserRequestAndReturnUser(userId);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(CourseMapper.toCourseDto(userService.enrollCourse(user, courseId)));
  }

  @Operation(
      summary = "find user's enrolled course overview",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found user's enrolled course and returned overview of the course"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid enrolledCourseId supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @GetMapping("/{userId}/courses/enrolled/{enrolledCourseId}")
  public ResponseEntity<EnrolledCourseOverviewDto> findEnrolledCourseOverview(
      @Parameter(description = "Id of user's enrolled course which overview will be returned")
          @PathVariable
          Long enrolledCourseId) {
    return ResponseEntity.ok(
        EnrolledCourseMapper.toEnrolledCourseOverviewDto(
            userService.findEnrolledCourseById(enrolledCourseId)));
  }

  @Operation(summary = "save mentor", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "The mentor have been saved"),
        @ApiResponse(
            responseCode = "400",
            description = "invalid userId or mentorId supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @PostMapping("/{userId}/mentors/{mentorId}")
  public ResponseEntity<UserWithSavedMentorsDto> saveMentor(
      @Parameter(description = "User's id whose  chosen mentor will be saved") @PathVariable
          Long userId,
      @Parameter(description = "Id of mentor which will be saved") @PathVariable Long mentorId) {
    User user = authService.validateUserRequestAndReturnUser(userId);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(UserMapper.userToUserWithSavedMentorsDto(userService.saveMentor(user, mentorId)));
  }

  @Operation(summary = "delete saved mentor", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Deleted the saved mentor"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid userId or mentorId supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @DeleteMapping("/{userId}/mentors/{mentorId}/saved")
  public ResponseEntity<Void> deleteMentor(
      @Parameter(description = "User's id whose saved mentor will be deleted") @PathVariable
          Long userId,
      @Parameter(description = "Id of user's saved mentor which will be deleted") @PathVariable
          Long mentorId) {
    User user = authService.validateUserRequestAndReturnUser(userId);
    userService.deleteMentor(user, mentorId);
    return ResponseEntity.ok().build();
  }

  @Operation(
      summary = "complete enrolled module item",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Enrolled module item status has been changed to completed"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid enrolled module item id supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @PutMapping("/{userId}/enrolledModuleItems/{enrolledModuleItemId}")
  public ResponseEntity<HttpStatus> completeEnrolledModuleItem(
      @Parameter(description = "Id of the enrolled module item which will be completed")
          @PathVariable
          Long enrolledModuleItemId) {
    userService.completeEnrolledModuleItem(enrolledModuleItemId);
    return ResponseEntity.ok().build();
  }
}
