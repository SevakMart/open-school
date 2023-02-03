package app.openschool.discussion.api;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.common.response.ResponseMessage;
import app.openschool.discussion.api.dto.DiscussionQuestionRequestDto;
import app.openschool.discussion.api.dto.DiscussionQuestionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.security.Principal;
import java.util.Locale;
import javax.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/discussionQuestions")
public class DiscussionQuestionController {

  private final DiscussionQuestionService discussionQuestionService;
  private final DiscussionQuestionRepository discussionQuestionRepository;
  private final MessageSource messageSource;

  public DiscussionQuestionController(
      DiscussionQuestionService discussionQuestionService,
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

  @Operation(summary = "update question", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Modifies the question and returns that"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request arguments supplied or not provided",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "401",
            description = "User should be logged in.",
            content = @Content(schema = @Schema()))
      })
  @PutMapping("/{id}")
  public ResponseEntity<DiscussionQuestionResponseDto> update(
      @Parameter(description = "Question id which will be updated") @PathVariable(value = "id")
          Long id,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object for updating the question")
          @Valid
          @RequestBody
          DiscussionQuestionRequestDto requestDto,
      Principal principal) {
    checkQuestionAuthor(id, principal);
    return ResponseEntity.ok().body(discussionQuestionService.update(id, requestDto));
  }

  @Operation(summary = "delete question", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "The question was deleted",
            content = @Content(schema = @Schema())),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid course id supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),

      })
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> delete(
      @Parameter(description = "Id of question which will be deleted") @PathVariable(value = "id")
          Long questionId,
      Principal principal) {
    checkQuestionAuthor(questionId, principal);
    discussionQuestionService.delete(questionId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  private void checkQuestionAuthor(Long id, Principal principal) {
    DiscussionQuestion discussionQuestion =
        discussionQuestionRepository.findById(id).orElseThrow(IllegalAccessError::new);
    if (!discussionQuestion.getUser().getEmail().equals(principal.getName())) {
      throw new PermissionDeniedException(
          messageSource.getMessage("permission.denied", null, Locale.ROOT));
    }
  }
}
