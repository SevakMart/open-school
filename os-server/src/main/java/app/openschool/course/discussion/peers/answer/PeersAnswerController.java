package app.openschool.course.discussion.peers.answer;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.common.response.ResponseMessage;
import app.openschool.course.discussion.AnswerService;
import app.openschool.course.discussion.dto.AnswerRequestDto;
import app.openschool.course.discussion.dto.AnswerResponseDto;
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
@RequestMapping("/api/v1/courses/peers-questions/answer")
public class PeersAnswerController {

  private final AnswerService answerService;
  private final PeersAnswerRepository peersAnswerRepository;
  private final MessageSource messageSource;

  public PeersAnswerController(
      @Qualifier("discussionAnswer") AnswerService answerService,
      PeersAnswerRepository peersAnswerRepository,
      MessageSource messageSource) {
    this.answerService = answerService;
    this.peersAnswerRepository = peersAnswerRepository;
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
  public ResponseEntity<AnswerResponseDto> create(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object for creating new answer")
          @Valid
          @RequestBody
          AnswerRequestDto requestDto,
      Principal principal) {
    AnswerResponseDto answerResponseDto = answerService.create(requestDto, principal.getName());
    return ResponseEntity.status(HttpStatus.CREATED).body(answerResponseDto);
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
  public ResponseEntity<AnswerResponseDto> update(
      @Parameter(description = "Answer id which will be updated.") @PathVariable(value = "id")
          Long id,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object for updating the answer.")
          @RequestParam
          String text,
      Principal principal) {
    checkAnswerAuthor(id, principal);
    return ResponseEntity.ok().body(answerService.update(id, text));
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
          Long answerId,
      Principal principal) {
    checkAnswerAuthor(answerId, principal);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  private PeersAnswer checkAnswerAuthor(Long id, Principal principal) {
    PeersAnswer peersAnswer =
        peersAnswerRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    if (!peersAnswer.getUser().getEmail().equals(principal.getName())) {
      throw new PermissionDeniedException(
          messageSource.getMessage("permission.denied", null, Locale.ROOT));
    }
    return peersAnswer;
  }
}
