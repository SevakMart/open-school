package app.openschool.discussion;

import app.openschool.common.response.ResponseMessage;
import app.openschool.discussion.dto.DiscussionQuestionRequestDto;
import app.openschool.discussion.dto.DiscussionQuestionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/discussionQuestions")
public class DiscussionQuestionController {
  private final DiscussionQuestionService discussionQuestionService;
  private final DiscussionQuestionRepository discussionQuestionRepository;
  private final MessageSource messageSource;

  public DiscussionQuestionController(
      @Qualifier("discussionQuestion") DiscussionQuestionService discussionQuestionService,
      DiscussionQuestionRepository discussionQuestionRepository,
      MessageSource messageSource) {
    this.discussionQuestionService = discussionQuestionService;
    this.discussionQuestionRepository = discussionQuestionRepository;
    this.messageSource = messageSource;
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
  public ResponseEntity<DiscussionQuestionResponseDto> create(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object for creating new question")
          @RequestBody
          DiscussionQuestionRequestDto requestDto,
      Principal principal) {
    DiscussionQuestionResponseDto discussionQuestionResponseDto =
        discussionQuestionService.create(requestDto, principal);
    return ResponseEntity.status(HttpStatus.CREATED).body(discussionQuestionResponseDto);
  }
}
