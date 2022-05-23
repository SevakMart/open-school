package app.openschool.user;

import app.openschool.course.api.dto.MentorCourseDto;
import app.openschool.user.api.dto.MentorDto;
import app.openschool.user.api.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mentors")
public class MentorController {

  private final UserService userService;

  public MentorController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{mentorId}/courses")
  @Operation(
      summary = "find courses by mentorID",
      security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Page<MentorCourseDto>> findMentorCourses(
      Pageable pageable, @PathVariable Long mentorId) {
    if (userService.findMentorById(mentorId).isEmpty()) {
      throw new UserNotFoundException(String.valueOf(mentorId));
    }
    return ResponseEntity.ok(userService.findMentorCourses(mentorId, pageable));
  }

  @GetMapping
  @Operation(summary = "find all mentors", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Page<MentorDto>> getAllMentors(Pageable pageable) {
    return ResponseEntity.ok(this.userService.findAllMentors(pageable));
  }
}
