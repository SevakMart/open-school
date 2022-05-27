package app.openschool.user;

import app.openschool.common.response.ResponseMessage;
import app.openschool.user.api.dto.MentorDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Locale;
import org.springframework.context.MessageSource;
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
  private final MessageSource messageSource;

  public MentorController(UserService userService, MessageSource messageSource) {
    this.userService = userService;
    this.messageSource = messageSource;
  }

  @GetMapping("/{mentorId}/courses")
  @Operation(
      summary = "find courses by mentorID",
      security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<?> findMentorCourses(
      Pageable pageable, @PathVariable Long mentorId, Locale locale) {
    if (userService.findMentorById(mentorId).isEmpty()) {
      return ResponseEntity.badRequest()
          .body(
              new ResponseMessage(
                  messageSource.getMessage("exception.nonexistent.mentor.message", null, locale)));
    }
    return ResponseEntity.ok(userService.findMentorCourses(mentorId, pageable));
  }

  @GetMapping
  @Operation(summary = "find all mentors", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Page<MentorDto>> getAllMentors(Pageable pageable) {
    return ResponseEntity.ok(this.userService.findAllMentors(pageable));
  }
}
