package app.openschool.user;

import app.openschool.category.api.dto.PreferredCategoryDto;
import app.openschool.course.Course;
import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.api.dto.UserCourseDto;
import app.openschool.course.api.dto.UserSavedCourseRequest;
import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.course.api.mapper.UserCourseMapper;
import app.openschool.user.api.dto.UserWithSavedMentorsDto;
import app.openschool.user.api.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{userId}/courses/suggested")
  @Operation(
      summary = "find suggested courses",
      security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<List<CourseDto>> getSuggestedCourses(@PathVariable Long userId) {
    return ResponseEntity.ok(CourseMapper.toCourseDtoList(userService.getSuggestedCourses(userId)));
  }

  @PostMapping("/{userId}/categories")
  @Operation(
      summary = "save preferred categories",
      security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Set<PreferredCategoryDto>> savePreferredCategories(
      @PathVariable Long userId, @RequestBody Set<Long> categoryIds) {
    return ResponseEntity.ok(userService.savePreferredCategories(userId, categoryIds));
  }

  @GetMapping("/{userId}/courses/enrolled")
  @Operation(
      summary = "find user's enrolled courses by course status",
      security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<List<UserCourseDto>> findEnrolledCourses(
      @PathVariable Long userId, @RequestParam(required = false) Long courseStatusId) {
    return ResponseEntity.ok(
        UserCourseMapper.toUserCourseDtoList(
            userService.findEnrolledCourses(userId, courseStatusId)));
  }

  @GetMapping("/{userId}/courses/saved")
  @Operation(
      summary = "find user's saved courses",
      security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Page<CourseDto>> findSavedCourses(
      @PathVariable Long userId, Pageable pageable) {
    return ResponseEntity.ok(
        CourseMapper.toCourseDtoPage(userService.findSavedCourses(userId, pageable)));
  }

  @PostMapping("/{userId}/courses/saved")
  @Operation(summary = "save course", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<?> saveCourse(
      @PathVariable Long userId,
      @Valid @RequestBody UserSavedCourseRequest userSavedCourseRequest,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest()
          .body(
              bindingResult.getAllErrors().stream()
                  .map(DefaultMessageSourceResolvable::getDefaultMessage));
    }
    Course savedCourse = userService.saveCourse(userId, userSavedCourseRequest.getCourseId());
    return ResponseEntity.ok(CourseMapper.toCourseDto(savedCourse));
  }

  @DeleteMapping("/{userId}/courses/{courseId}/saved")
  @Operation(
      summary = "delete course from saved courses",
      security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<CourseDto> deleteCourse(
      @PathVariable Long userId, @PathVariable Long courseId) {
    Course deletedCourse = userService.deleteCourse(userId, courseId);
    return ResponseEntity.ok(CourseMapper.toCourseDto(deletedCourse));
  }

  @PostMapping("/courses/{courseId}")
  @Operation(summary = "enroll a course", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<CourseDto> enrollCourse(@PathVariable long courseId) {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return userService
        .enrollCourse(username, courseId)
        .map(
            course ->
                ResponseEntity.status(HttpStatus.CREATED).body(CourseMapper.toCourseDto(course)))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/{userId}/mentors/{mentorId}")
  @Operation(summary = "save mentor", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<UserWithSavedMentorsDto> saveMentor(
      @PathVariable Long userId, @PathVariable Long mentorId) {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            UserMapper.userToUserWithSavedMentorsDto(
                userService.saveMentor(userId, mentorId, username)));
  }
}
