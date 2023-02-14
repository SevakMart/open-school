package app.openschool.course.module.quiz;

import app.openschool.common.response.ResponseMessage;
import app.openschool.course.module.quiz.api.dto.CreateQuizDto;
import app.openschool.course.module.quiz.api.dto.EnrolledQuizAssessmentRequestDto;
import app.openschool.course.module.quiz.api.dto.EnrolledQuizAssessmentResponseDto;
import app.openschool.course.module.quiz.api.dto.ModifyQuizDataRequest;
import app.openschool.course.module.quiz.api.dto.QuizDto;
import app.openschool.course.module.quiz.api.mapper.QuizMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Locale;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
            content = @Content(schema = @Schema())),
        @ApiResponse(
            responseCode = "400",
            description = "if the module doesn't belong to the current user",
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

  @Operation(
      summary = "modify data of quizzes",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Modifies provided field or fields of quiz and returns updated quiz"),
        @ApiResponse(
            responseCode = "400",
            description =
                "Invalid quiz id, invalid new parent quiz id or"
                    + " invalid or existing quiz title supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Only user with admin role has access to this method",
            content = @Content(schema = @Schema()))
      })
  @PatchMapping(value = "/{id}")
  public ResponseEntity<QuizDto> updateQuiz(
      @Parameter(description = "Id of quiz which fields will be modified")
          @PathVariable(value = "id")
          Long quizId,
      @Parameter(
              description =
                  "Request object for modifying quiz, "
                      + "which includes new title,new description that must be unique ")
          @Valid
          @RequestBody
          ModifyQuizDataRequest request) {
    return ResponseEntity.ok()
        .body(QuizMapper.quizToQuizDto(quizService.updateQuiz(quizId, request)));
  }

  @Operation(summary = "delete quiz", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "quiz successfully deleted"),
        @ApiResponse(
            responseCode = "404",
            description = "Invalid quiz id supplied",
            content = @Content(schema = @Schema())),
        @ApiResponse(
            responseCode = "403",
            description = "If the user have not mentor role",
            content = @Content(schema = @Schema())),
        @ApiResponse(
            responseCode = "400",
            description = "if the quiz doesn't belong to the current user",
            content = @Content(schema = @Schema()))
      })
  @PreAuthorize("hasAuthority('MENTOR')")
  @DeleteMapping("/{quizId}")
  public ResponseEntity<Void> deleteQuiz(
      @Parameter(description = "Id of the quiz which will be deleted") @PathVariable Long quizId) {
    if (quizService.deleteQuiz(quizId)) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<QuizDto> findById(
      @Parameter(description = "Id of the quiz which will be find") @PathVariable Long id) {
    return ResponseEntity.ok(quizService.findById(id));
  }

  @GetMapping("/byModule/{id}")
  public ResponseEntity<Page<QuizDto>> findAllByModuleId(
      @Parameter(description = "Module ID that belongs to the quizzes being searched") @PathVariable
          Long id,
      @Parameter(
              description =
                  "Includes parameters page, size, and sort which is not required. "
                      + "Page results page you want to retrieve (0..N). "
                      + "Size is count of records per page(1..N). "
                      + "Sorting criteria in the format: property(,asc|desc). "
                      + "Default sort order is ascending. "
                      + "Multiple sort criteria are supported.")
          Pageable pageable) {

    return ResponseEntity.ok(quizService.findAllByModuleId(id, pageable));
  }

  @PostMapping("/enrolledQuizzes/{enrolledQuizId}")
  public ResponseEntity<EnrolledQuizAssessmentResponseDto> completeEnrolledQuiz(
      @Parameter(description = "Id of the enrolled quiz which will be completed") @PathVariable
          Long enrolledQuizId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object which contains questions and chosen answer options")
          @RequestBody
          EnrolledQuizAssessmentRequestDto enrolledQuizAssessmentRequestDto,
      Locale locale) {
    return quizService
        .completeEnrolledQuiz(enrolledQuizId, enrolledQuizAssessmentRequestDto, locale)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
