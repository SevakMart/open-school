package app.openschool.course.module.quiz.question;

import app.openschool.course.module.quiz.question.api.dto.CreateQuestionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

  private final QuestionService questionService;

  public QuestionController(QuestionService questionService) {
    this.questionService = questionService;
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
      @Parameter(description = "Id of the question which will be deleted") @PathVariable
          Long questionId) {
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
      @Parameter(description = "Id of the question which will be updated") @PathVariable
          Long questionId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object to update question")
          @RequestBody
          CreateQuestionDto createQuestionDto) {
    if (questionService.updateQuestion(questionId, createQuestionDto)) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
