package app.openschool.course.module.quiz;

import app.openschool.course.module.quiz.api.dto.CreateQuizDto;
import app.openschool.course.module.quiz.api.dto.QuizDto;
import app.openschool.course.module.quiz.api.mapper.QuizMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/quizzes")
public class QuizController {

  private final QuizService quizService;

  public QuizController(QuizService quizService) {
    this.quizService = quizService;
  }

  @Operation(
      summary = "create quiz for the module",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Creates a quiz for the module and returns it"),
        @ApiResponse(
            responseCode = "404",
            description = "Invalid module id supplied",
            content = @Content(schema = @Schema())),
        @ApiResponse(
            responseCode = "403",
            description =
                "If the user have not mentor role, request body is not built correctly, etc.",
            content = @Content(schema = @Schema()))
      })
  @PreAuthorize("hasAuthority('MENTOR')")
  @PostMapping("/{moduleId}")
  public ResponseEntity<QuizDto> createQuiz(
      @Parameter(description = "Id of the module for which the quiz will be created") @PathVariable
          Long moduleId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object to create a quiz for a specific module")
          @RequestBody
          CreateQuizDto createQuizDto) {
    return quizService
        .createQuiz(moduleId, createQuizDto)
        .map(quiz -> ResponseEntity.status(HttpStatus.CREATED).body(QuizMapper.quizToQuizDto(quiz)))
        .orElse(ResponseEntity.notFound().build());
  }
}
