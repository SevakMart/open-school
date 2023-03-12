package app.openschool.course.discussion.mentor.answer;

import app.openschool.common.response.ResponseMessage;
import app.openschool.course.discussion.AnswerService;
import app.openschool.course.discussion.dto.AnswerRequestDto;
import app.openschool.course.discussion.dto.AnswerResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/courses/mentor-questions/answer")
public class MentorAnswerController {

  // ToDo this class and the components used in it will be changed in the future
  private final AnswerService answerService;

  public MentorAnswerController(@Qualifier("discussionAnswerMentor") AnswerService answerService) {
    this.answerService = answerService;
  }

  @Operation(summary = "add question", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Creates new question and returns that"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request arguments supplied or not provided",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
      })
  @PostMapping("/{enrolledCourseId}")
  @PreAuthorize("hasAnyAuthority('MENTOR')")
  public ResponseEntity<AnswerResponseDto> create(@PathVariable Long enrolledCourseId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object for creating new question")
          @RequestBody
          AnswerRequestDto requestDto,
      Principal principal) {
    AnswerResponseDto answerResponseDto =
        answerService.create(enrolledCourseId, requestDto, principal.getName());
    return ResponseEntity.status(HttpStatus.CREATED).body(answerResponseDto);
  }
}
