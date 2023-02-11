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
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/discussionAnswers")
public class DiscussionAnswerController {

  private final DiscussionAnswerService discussionAnswerService;
  private final DiscussionAnswerRepository discussionAnswerRepository;
  private final MessageSource messageSource;

  public DiscussionAnswerController(
      @Qualifier("discussionAnswer") DiscussionAnswerService discussionAnswerService,
      DiscussionAnswerRepository discussionAnswerRepository,
      MessageSource messageSource) {
    this.discussionAnswerService = discussionAnswerService;
    this.discussionAnswerRepository = discussionAnswerRepository;
    this.messageSource = messageSource;
  }

  @Operation(summary = "add answer", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Creates new answer and returns that"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request arguments supplied or not provided",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
      })
  @PostMapping
  public ResponseEntity<DiscussionAnswerResponseDto> create(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object for creating new answer")
          @Valid
          @RequestBody
          DiscussionAnswerRequestDto requestDto,
      Principal principal) {
    DiscussionAnswerResponseDto discussionAnswerResponseDto =
        discussionAnswerService.create(requestDto, principal);
    return ResponseEntity.status(HttpStatus.CREATED).body(discussionAnswerResponseDto);
  }
}
