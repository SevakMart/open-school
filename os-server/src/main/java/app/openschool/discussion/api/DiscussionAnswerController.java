package app.openschool.discussion.api;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.common.response.ResponseMessage;
import app.openschool.discussion.api.dto.DiscussionAnswerRequestDto;
import app.openschool.discussion.api.dto.DiscussionAnswerResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.security.Principal;
import java.util.Locale;
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
@RequestMapping("/api/v1/discussionAnswers")
public class DiscussionAnswerController {

  private final DiscussionAnswerService discussionAnswerService;
  private final DiscussionAnswerRepository discussionAnswerRepository;
  private final MessageSource messageSource;

  public DiscussionAnswerController(
      DiscussionAnswerService discussionAnswerService,
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
          @RequestBody
          DiscussionAnswerRequestDto requestDto,
      Principal principal) {
    DiscussionAnswerResponseDto discussionAnswerResponseDto =
        discussionAnswerService.create(requestDto, principal);
    return ResponseEntity.status(HttpStatus.CREATED).body(discussionAnswerResponseDto);
  }

  @Operation(summary = "update answer", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Modifies the answer and returns that"),
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
  public ResponseEntity<DiscussionAnswerResponseDto> update(
      @Parameter(description = "Answer id which will be updated.") @PathVariable(value = "id")
          Long id,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object for updating the answer.")
          @RequestBody
          DiscussionAnswerRequestDto requestDto,
      Principal principal) {
    checkAnswerAuthor(id, principal);
    return ResponseEntity.ok().body(discussionAnswerService.update(id, requestDto));
  }

  @Operation(summary = "delete answer", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "The answer was deleted",
            content = @Content(schema = @Schema())),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid question id supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> delete(
      @Parameter(description = "Id of answer which will be deleted") @PathVariable(value = "id")
          Long questionId,
      Principal principal) {
    checkAnswerAuthor(questionId, principal);
    discussionAnswerService.delete(questionId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  private void checkAnswerAuthor(Long id, Principal principal) {
    DiscussionAnswer discussionAnswer =
        discussionAnswerRepository.findById(id).orElseThrow(IllegalAccessError::new);
    if (!discussionAnswer.getUser().getEmail().equals(principal.getName())) {
      throw new PermissionDeniedException(
          messageSource.getMessage("permission.denied", null, Locale.ROOT));
    }
  }
}
