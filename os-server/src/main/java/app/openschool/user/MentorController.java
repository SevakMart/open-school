package app.openschool.user;

import app.openschool.auth.AuthService;
import app.openschool.common.response.ResponseMessage;
import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.user.api.dto.MentorDto;
import app.openschool.user.api.mapper.MentorMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mentors")
public class MentorController {

  private final UserService userService;
  private final AuthService authService;

  public MentorController(UserService userService, AuthService authService) {
    this.userService = userService;
    this.authService = authService;
  }

  @Operation(
      summary = "find mentor's courses",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns paginated list of mentor's courses"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid mentorId supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @GetMapping("/{mentorId}/courses")
  public ResponseEntity<Page<CourseDto>> findMentorCourses(
      @Parameter(description = "Mentor's id whose courses will be returned") @PathVariable
          Long mentorId,
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
        CourseMapper.toCourseDtoPage(userService.findMentorCourses(mentorId, pageable)));
  }

  @Operation(summary = "find all mentors", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponse(
      responseCode = "200",
      description = "Returns paginated list of mentors or empty list if no mentor have been found")
  @GetMapping
  public ResponseEntity<Page<MentorDto>> findAllMentors(
      @Parameter(
              description =
                  "Includes parameters page, size, and sort which is not required. "
                      + "Page results page you want to retrieve (0..N). "
                      + "Size is count of records per page(1..N). "
                      + "Sorting criteria in the format: property(,asc|desc). "
                      + "Default sort order is ascending. "
                      + "Multiple sort criteria are supported.")
          Pageable pageable) {
    return ResponseEntity.ok(MentorMapper.toMentorDtoPage(userService.findAllMentors(pageable)));
  }

  @Operation(summary = "find saved mentors", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description =
                "Returns paginated list of user's saved mentors "
                    + "or empty list if no mentor has been found"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid userId supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @GetMapping("/{userId}")
  public ResponseEntity<Page<MentorDto>> findSavedMentors(
      @Parameter(description = "User's id whose saved mentors will be returned") @PathVariable
          Long userId,
      @Parameter(
              description =
                  "Includes parameters page, size, and sort which is not required. "
                      + "Page results page you want to retrieve (0..N). "
                      + "Size is count of records per page(1..N). "
                      + "Sorting criteria in the format: property(,asc|desc). "
                      + "Default sort order is ascending. "
                      + "Multiple sort criteria are supported.")
          Pageable pageable) {
    User user = authService.validateUserRequestAndReturnUser(userId);
    return ResponseEntity.ok(
        MentorMapper.toMentorDtoPage(userService.findSavedMentors(user, pageable)));
  }

  @Operation(summary = "find mentors by name", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponse(
      responseCode = "200",
      description =
          "Returns paginated list of mentors depending on passed name parameter "
              + "(by default returns all mentors with size of pagination), "
              + "or empty list if no mentor have been found")
  @GetMapping("/searched")
  public ResponseEntity<Page<MentorDto>> findMentorsByName(
      @Parameter(description = "Mentor's name") @RequestParam(required = false) String name,
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
        MentorMapper.toMentorDtoPage(userService.findMentorsByName(name, pageable)));
  }

  @Operation(
      summary = "find saved mentors by name",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description =
                "Returns paginated list of user's saved mentors depending on passed name parameter "
                    + "(by default returns all saved mentors with size of pagination), "
                    + "or empty list if no saved mentor has been found"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid userId supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @GetMapping("/searched/{userId}")
  public ResponseEntity<Page<MentorDto>> findSavedMentorsByName(
      @Parameter(description = "User's id whose saved mentors will be returned") @PathVariable
          Long userId,
      @Parameter(description = "Mentor's name") @RequestParam(required = false) String name,
      @Parameter(
              description =
                  "Includes parameters page, size, and sort which is not required. "
                      + "Page results page you want to retrieve (0..N). "
                      + "Size is count of records per page(1..N). "
                      + "Sorting criteria in the format: property(,asc|desc). "
                      + "Default sort order is ascending. "
                      + "Multiple sort criteria are supported.")
          Pageable pageable) {
    authService.validateUserRequestAndReturnUser(userId);
    return ResponseEntity.ok(
        MentorMapper.toMentorDtoPage(userService.findSavedMentorsByName(userId, name, pageable)));
  }
}
