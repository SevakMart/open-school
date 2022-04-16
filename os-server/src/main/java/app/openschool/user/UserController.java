package app.openschool.user;

import app.openschool.category.api.dto.SavePreferredCategoriesRequestDto;
import app.openschool.category.api.dto.SavePreferredCategoriesResponseDto;
import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.api.dto.UserCourseDto;
import app.openschool.user.api.dto.MentorDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
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

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/mentors")
  @Operation(summary = "find all mentors", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Page<MentorDto>> findAllMentors(Pageable pageable) {
    return ResponseEntity.ok(this.userService.findAllMentors(pageable));
  }

  @GetMapping("/{userId}/courses/suggested")
  @Operation(
      summary = "find suggested courses",
      security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<List<CourseDto>> getSuggestedCourses(@PathVariable Long userId) {
    return ResponseEntity.ok(this.userService.getSuggestedCourses(userId));
  }

  @PostMapping("/choose-categories")
  @Operation(
      summary = "save preferred categories",
      security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<SavePreferredCategoriesResponseDto> savePreferredCategories(
      @RequestBody SavePreferredCategoriesRequestDto savePreferredCategoriesRequestDto) {

    return ResponseEntity.ok(
        userService.savePreferredCategories(savePreferredCategoriesRequestDto));
  }

  @GetMapping("/{userId}/courses")
  @Operation(summary = "find user's courses by course status")
  public ResponseEntity<List<UserCourseDto>> findUserCourses(
      @PathVariable Long userId, @RequestParam(required = false) Long courseStatusId) {
    return ResponseEntity.ok(this.userService.findUserCourses(userId, courseStatusId));
  }
}
