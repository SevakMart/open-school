package app.openschool.user;

import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.user.api.dto.MentorDto;
import app.openschool.user.api.mapper.MentorMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
      summary = "find mentor's courses",
      security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Page<CourseDto>> findMentorCourses(
      @PathVariable Long mentorId, Pageable pageable) {
    return ResponseEntity.ok(
        CourseMapper.toCourseDtoPage(userService.findMentorCourses(mentorId, pageable)));
  }

  @GetMapping
  @Operation(summary = "find all mentors", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Page<MentorDto>> findAllMentors(Pageable pageable) {
    return ResponseEntity.ok(MentorMapper.toMentorDtoPage(userService.findAllMentors(pageable)));
  }

  @GetMapping("/{userId}")
  @Operation(summary = "find saved mentors", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Page<MentorDto>> findSavedMentors(
      @PathVariable Long userId, Pageable pageable) {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return ResponseEntity.ok(
        MentorMapper.toMentorDtoPage(userService.findSavedMentors(userId, username, pageable)));
  }

  @GetMapping("/searched")
  @Operation(summary = "find mentors by name", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Page<MentorDto>> findMentorsByName(
      @RequestParam(required = false) String name, Pageable pageable) {
    return ResponseEntity.ok(
        MentorMapper.toMentorDtoPage(userService.findMentorsByName(name, pageable)));
  }

  @GetMapping("/searched/{userId}")
  @Operation(
      summary = "find saved mentors by name",
      security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Page<MentorDto>> findSavedMentorsByName(
      @PathVariable Long userId, @RequestParam(required = false) String name, Pageable pageable) {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return ResponseEntity.ok(
        MentorMapper.toMentorDtoPage(
            userService.findSavedMentorsByName(userId, username, name, pageable)));
  }
}
