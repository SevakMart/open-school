package app.openschool.course.discussion.peers.answer;

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
}
