package app.openschool.course.discussion.peers.question;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.common.response.ResponseMessage;
import app.openschool.course.discussion.QuestionService;
import app.openschool.course.discussion.dto.QuestionRequestDto;
import app.openschool.course.discussion.dto.QuestionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.security.Principal;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/courses/peers-questions")
public class PeersQuestionController {
  private final QuestionService questionService;
  private final PeersQuestionRepository peersQuestionRepository;
  private final MessageSource messageSource;

  public PeersQuestionController(
      @Qualifier("discussionQuestion") QuestionService questionService,
      PeersQuestionRepository peersQuestionRepository,
      MessageSource messageSource) {
    this.questionService = questionService;
    this.peersQuestionRepository = peersQuestionRepository;
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
  public ResponseEntity<QuestionResponseDto> create(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object for creating new question")
          @RequestBody
          QuestionRequestDto requestDto,
      Principal principal) {
    QuestionResponseDto questionResponseDto =
        questionService.create(requestDto, principal.getName());
    return ResponseEntity.status(HttpStatus.CREATED).body(questionResponseDto);
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
  public ResponseEntity<QuestionResponseDto> update(
      @Parameter(description = "Question id which will be updated") @PathVariable(value = "id")
          Long id,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object for updating the question")
          @RequestParam
          String text,
      Principal principal) {
    checkQuestionAuthor(id, principal);
    return ResponseEntity.ok().body(questionService.update(id, text));
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
    questionService.delete(questionId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  private void checkQuestionAuthor(Long id, Principal principal) {
    PeersQuestion peersQuestion =
        peersQuestionRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    if (!peersQuestion.getUser().getEmail().equals(principal.getName())) {
      throw new PermissionDeniedException(
          messageSource.getMessage("permission.denied", null, Locale.ROOT));
    }
  }
}
