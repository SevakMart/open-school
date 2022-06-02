package app.openschool.user;

import app.openschool.category.api.dto.PreferredCategoryDto;
import app.openschool.common.response.ResponseMessage;
import app.openschool.course.CourseService;
import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.api.dto.UserCourseDto;
import app.openschool.user.api.dto.MentorDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
  private final CourseService courseService;
  private final MessageSource messageSource;

  public UserController(
      UserService userService, CourseService courseService, MessageSource messageSource) {
    this.userService = userService;
    this.courseService = courseService;
    this.messageSource = messageSource;
  }

  @GetMapping("/mentors")
  @Operation(summary = "find all mentors", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Page<MentorDto>> getAllMentors(Pageable pageable) {
    return ResponseEntity.ok(this.userService.findAllMentors(pageable));
  }

  @GetMapping("/{userId}/courses/suggested")
  @Operation(
      summary = "find suggested courses",
      security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<List<CourseDto>> getSuggestedCourses(@PathVariable Long userId) {
    return ResponseEntity.ok(this.userService.getSuggestedCourses(userId));
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
      summary = "find user's courses by course status",
      security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<List<UserCourseDto>> findEnrolledCourses(
      @PathVariable Long userId, @RequestParam(required = false) Long courseStatusId) {
    return ResponseEntity.ok(this.userService.findUserEnrolledCourses(userId, courseStatusId));
  }

  @GetMapping("/{userId}/courses/saved")
  @Operation(
      summary = "find user's saved courses",
      security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<?> findSavedCourses(
      Pageable pageable, @PathVariable Long userId, Locale locale) {
    if (userService.findById(userId).isEmpty()) {
      return ResponseEntity.badRequest()
          .body(new ResponseMessage(messageSource.getMessage("user.not.exists", null, locale)));
    }
    return ResponseEntity.ok(this.userService.findUserSavedCourses(pageable, userId));
  }

  @PostMapping("/{userId}/courses/{courseId}/saved")
  @Operation(summary = "save course", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<ResponseMessage> saveCourse(
      @PathVariable Long userId, @PathVariable Long courseId, Locale locale) {
    if (userService.findById(userId).isEmpty()) {
      return ResponseEntity.badRequest()
          .body(new ResponseMessage(messageSource.getMessage("user.not.exists", null, locale)));
    }
    if (courseService.findById(courseId).isEmpty()) {
      return ResponseEntity.badRequest()
          .body(new ResponseMessage(messageSource.getMessage("course.not.exists", null, locale)));
    }
    this.userService.saveCourse(userId, courseId);
    return ResponseEntity.ok(
        new ResponseMessage(
            messageSource.getMessage("course.successfully.saved.message", null, locale)));
  }
}
