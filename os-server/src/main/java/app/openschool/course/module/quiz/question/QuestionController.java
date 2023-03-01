package app.openschool.course.module.quiz.question;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.course.module.quiz.Quiz;
import app.openschool.course.module.quiz.QuizRepository;
import app.openschool.course.module.quiz.question.api.dto.CreateQuestionDto;
import app.openschool.course.module.quiz.question.api.dto.QuestionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.security.Principal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/{quizId}/questions")
public class QuestionController {

  private final QuestionService questionService;
  private final QuizRepository quizRepository;

  public QuestionController(QuestionService questionService, QuizRepository quizRepository) {
    this.questionService = questionService;
    this.quizRepository = quizRepository;
  }

  @Operation(summary = "delete question", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "question successfully deleted"),
        @ApiResponse(
            responseCode = "404",
            description = "Invalid question id supplied",
            content = @Content(schema = @Schema())),
        @ApiResponse(
            responseCode = "403",
            description = "If the user have not mentor role",
            content = @Content(schema = @Schema())),
        @ApiResponse(
            responseCode = "400",
            description = "if the question doesn't belong to the current user",
            content = @Content(schema = @Schema()))
      })
  @PreAuthorize("hasAuthority('MENTOR')")
  @DeleteMapping("/{questionId}")
  public ResponseEntity<Void> deleteQuestion(
      @Parameter(description = "Id of the quiz to which this question belongs") @PathVariable()
          Long quizId,
      @Parameter(description = "Id of the question which will be deleted") @PathVariable
          Long questionId,
      Principal principal) {
    checkQuestionAuthor(quizId, principal);
    if (questionService.deleteQuestion(questionId)) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @Operation(summary = "update question", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "question successfully updated"),
        @ApiResponse(
            responseCode = "404",
            description = "Invalid question id supplied",
            content = @Content(schema = @Schema())),
        @ApiResponse(
            responseCode = "403",
            description = "If the user have not mentor role",
            content = @Content(schema = @Schema())),
        @ApiResponse(
            responseCode = "400",
            description = "if the question doesn't belong to the current user",
            content = @Content(schema = @Schema()))
      })
  @PreAuthorize("hasAuthority('MENTOR')")
  @PutMapping("/{questionId}")
  public ResponseEntity<Void> updateQuestion(
      @Parameter(description = "Id of the quiz to which this question belongs") @PathVariable()
          Long quizId,
      @Parameter(description = "Id of the question which will be updated") @PathVariable
          Long questionId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object to update question")
          @RequestBody
          CreateQuestionDto createQuestionDto,
      Principal principal) {
    checkQuestionAuthor(quizId, principal);
    if (questionService.updateQuestion(questionId, createQuestionDto)) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping()
  public ResponseEntity<Page<QuestionDto>> findAllByQuizId(
      @Parameter(description = "Quiz ID that belongs to the questions being searched") @PathVariable
          Long quizId,
      @Parameter(
              description =
                  "Includes parameters page, size, and sort which is not required. "
                      + "Page results page you want to retrieve (0..N). "
                      + "Size is count of records per page(1..N). "
                      + "Sorting criteria in the format: property(,asc|desc). "
                      + "Default sort order is ascending. "
                      + "Multiple sort criteria are supported.")
          Pageable pageable) {

    return ResponseEntity.ok(questionService.findAllByQuizId(quizId, pageable));
  }

  private void checkQuestionAuthor(Long quizId, Principal principal) {
    Quiz quiz = quizRepository.findById(quizId).orElseThrow(IllegalArgumentException::new);
    if (!quiz.getModule().getCourse().getMentor().getEmail().equals(principal.getName())) {
      throw new PermissionDeniedException("permission.denied");
    }
  }
}
