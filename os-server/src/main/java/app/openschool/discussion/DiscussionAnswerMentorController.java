package app.openschool.discussion;

import app.openschool.common.response.ResponseMessage;
import app.openschool.discussion.dto.DiscussionAnswerRequestDto;
import app.openschool.discussion.dto.DiscussionAnswerResponseDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/discussionAnswerMentor")
public class DiscussionAnswerMentorController {

  private final DiscussionAnswerService discussionAnswerService;

  public DiscussionAnswerMentorController(
      @Qualifier("discussionAnswerMentor") DiscussionAnswerService discussionAnswerService) {
    this.discussionAnswerService = discussionAnswerService;
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
  @PostMapping
  @PreAuthorize("hasAnyAuthority('MENTOR')")
  public ResponseEntity<DiscussionAnswerResponseDto> create(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object for creating new question")
          @RequestBody
          DiscussionAnswerRequestDto requestDto,
      Principal principal) {
    DiscussionAnswerResponseDto discussionAnswerResponseDto =
        discussionAnswerService.create(requestDto, principal);
    return ResponseEntity.status(HttpStatus.CREATED).body(discussionAnswerResponseDto);
  }
}
