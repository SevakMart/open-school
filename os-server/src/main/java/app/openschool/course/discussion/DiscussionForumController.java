package app.openschool.course.discussion;

import app.openschool.common.response.ResponseMessage;
import app.openschool.course.discussion.dto.AnswerRequestDto;
import app.openschool.course.discussion.dto.AnswerResponseDto;
import app.openschool.course.discussion.dto.MentorQuestionResponseDto;
import app.openschool.course.discussion.dto.QuestionRequestDto;
import app.openschool.course.discussion.dto.QuestionResponseDto;
import app.openschool.course.discussion.dto.UpdateQuestionRequest;
import app.openschool.course.discussion.mapper.AnswerMapper;
import app.openschool.course.discussion.mapper.MentorQuestionMapper;
import app.openschool.course.discussion.mapper.QuestionMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/courses/enrolled")
public class DiscussionForumController {

  private final QuestionService questionService;
  private final AnswerService answerService;

  private final QuestionService mentorQuestionService;

  public DiscussionForumController(
      @Qualifier("discussionQuestion") QuestionService questionService,
      @Qualifier("discussionAnswer") AnswerService answerService,
      @Qualifier("discussionQuestionMentor") QuestionService mentorQuestionService) {
    this.questionService = questionService;
    this.answerService = answerService;
    this.mentorQuestionService = mentorQuestionService;
  }

  @Operation(summary = "add question", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Creates new question and returns that"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request arguments supplied or not provided",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "403",
            description = "User has not enrolled in the course provided",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @PostMapping("/{enrolledCourseId}/peers-questions")
  public ResponseEntity<QuestionResponseDto> createQuestion(
      @Parameter(description = "ID of the enrolled course") @PathVariable Long enrolledCourseId,
      @Valid @RequestBody QuestionRequestDto requestDto,
      Principal principal) {
    QuestionResponseDto questionResponseDto =
        QuestionMapper.toResponseDto(
            questionService.create(enrolledCourseId, requestDto, principal.getName()));
    return ResponseEntity.status(HttpStatus.CREATED).body(questionResponseDto);
  }

  @Operation(
      summary = "update peers question",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Modifies the Peers-Question and returns that"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request arguments provided.",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @PutMapping("/{enrolledCourseId}/peers-questions/{peersQuestionId}")
  public ResponseEntity<QuestionResponseDto> updatePeersQuestion(
      @Parameter(description = "ID of the enrolled course")
          @PathVariable(value = "enrolledCourseId")
          Long enrolledCourseId,
      @Parameter(description = "Peers-Question ID to be updated")
          @PathVariable(value = "peersQuestionId")
          Long peersQuestionId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object for updating the Peers-Question")
          @Valid
          @RequestBody
          UpdateQuestionRequest request,
      Principal principal) {

    return ResponseEntity.ok()
        .body(
            QuestionMapper.toResponseDto(
                questionService.update(
                    request, peersQuestionId, enrolledCourseId, principal.getName())));
  }

  @Operation(
      summary = "delete peers question",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "Peers-Question was deleted",
            content = @Content(schema = @Schema())),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request arguments provided.",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @DeleteMapping("/{enrolledCourseId}/peers-questions/{peersQuestionId}")
  public ResponseEntity<HttpStatus> deletePeersQuestion(
      @Parameter(description = "ID of the enrolled course")
          @PathVariable(value = "enrolledCourseId")
          Long enrolledCourseId,
      @Parameter(description = "Peers-Question ID to be deleted")
          @PathVariable(value = "peersQuestionId")
          Long peersQuestionId,
      Principal principal) {

    questionService.delete(peersQuestionId, enrolledCourseId, principal.getName());
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @Operation(
      summary = "find all questions related to the provided course",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description =
                "Returns a paginated list of Questions, depending on the "
                    + "sort parameters passed (by default, all Questions are sorted by Question_ID,"
                    + "or an empty list if the Questions are not found."),
        @ApiResponse(
            responseCode = "403",
            description = "Only registered users have access to this method",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @GetMapping("/{enrolledCourseId}/peers-questions")
  public ResponseEntity<Page<QuestionResponseDto>> findQuestionsByCourseId(
      @Parameter(description = "Enrolled course id") @PathVariable Long enrolledCourseId,
      Pageable pageable,
      @RequestParam(value = "q", required = false) String searchQuery) {
    Page<QuestionResponseDto> questionResponseDtos =
        QuestionMapper.toQuestionDtoPage(
            questionService.findQuestionByCourseId(enrolledCourseId, pageable, searchQuery));
    return ResponseEntity.ok().body(questionResponseDtos);
  }

  @Operation(summary = "get Question by ID", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Get information about Question"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid QuestionID or EnrolledCourseID supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Only registered users have access to this method",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @GetMapping("/{enrolledCourseId}/peers-questions/{questionId}")
  public ResponseEntity<QuestionResponseDto> getQuestionById(
      @Parameter(description = "Enrolled course id") @PathVariable Long enrolledCourseId,
      @Parameter(description = "Question id") @PathVariable Long questionId) {
    return ResponseEntity.ok(
        QuestionMapper.toResponseDto(
            questionService.findQuestionByIdAndEnrolledCourseId(enrolledCourseId, questionId)));
  }

  @Operation(
      summary = "find all Answers related to the provided Question",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description =
                "Returns a paginated list of Answers, depending on the "
                    + "sort parameters passed (by default, all Answers are sorted by Answer_ID,"
                    + "or an empty list if the Answers are not found."),
        @ApiResponse(
            responseCode = "403",
            description = "Only registered users have access to this method",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @GetMapping("/{enrolledCourseId}/peers-questions/answers/{questionId}")
  public ResponseEntity<Page<AnswerResponseDto>> findAnswersByQuestionId(
      @Parameter(description = "Question id") @PathVariable Long questionId, Pageable pageable) {
    return ResponseEntity.ok()
        .body(
            AnswerMapper.toAnswerDtoPage(
                answerService.findAnswerByQuestionId(questionId, pageable)));
  }

  @Operation(summary = "add answer", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Creates new answer and returns that"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request arguments supplied or not provided",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "403",
            description = "User has not enrolled in the course provided",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @PostMapping("/{enrolledCourseId}/peers-answers")
  public ResponseEntity<AnswerResponseDto> createAnswer(
      @Parameter(description = "ID of the enrolled course") @PathVariable Long enrolledCourseId,
      @Valid @RequestBody AnswerRequestDto requestDto,
      Principal principal) {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            AnswerMapper.toAnswerDto(
                answerService.create(enrolledCourseId, requestDto, principal.getName())));
  }

  @Operation(summary = "get Answer by ID", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Get information about Answer"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid AnswerID supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Only registered users have access to this method",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @GetMapping("/{enrolledCourseId}/peers-answers/{answerId}")
  public ResponseEntity<AnswerResponseDto> getAnswerById(
      @Parameter(description = "Answer id") @PathVariable Long answerId) {

    return ResponseEntity.ok(AnswerMapper.toAnswerDto(answerService.findAnswerById(answerId)));
  }

  @Operation(summary = "ask Mentor", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Creates new question and returns that"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request arguments supplied or not provided",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "403",
            description = "User has not enrolled in the course provided",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @PostMapping("/{enrolledCourseId}/mentor-questions")
  public ResponseEntity<MentorQuestionResponseDto> createMentorQuestion(
      @Parameter(description = "ID of the enrolled course") @PathVariable Long enrolledCourseId,
      @Valid @RequestBody QuestionRequestDto requestDto,
      Principal principal) {

    MentorQuestionResponseDto mentorQuestionResponseDto =
        MentorQuestionMapper.toResponseDto(
            mentorQuestionService.create(enrolledCourseId, requestDto, principal.getName()));
    return ResponseEntity.status(HttpStatus.CREATED).body(mentorQuestionResponseDto);
  }

  @Operation(
      summary = "find all MentorQuestions related to the provided course",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description =
                "Returns a paginated list of MentorQuestions, depending on the "
                    + "sort parameters passed (by default, all Questions are sorted by Question_ID,"
                    + "or an empty list if the MentorQuestions are not found."),
        @ApiResponse(
            responseCode = "403",
            description = "Only registered users have access to this method",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @GetMapping("/{enrolledCourseId}/mentor-questions")
  public ResponseEntity<Page<MentorQuestionResponseDto>> findMentorQuestionsByCourseId(
      @Parameter(description = "Enrolled course id") @PathVariable Long enrolledCourseId,
      Pageable pageable,
      @RequestParam(value = "q", required = false) String searchQuery) {

    Page<MentorQuestionResponseDto> dtoPage =
        MentorQuestionMapper.toQuestionDtoPage(
            mentorQuestionService.findQuestionByCourseId(enrolledCourseId, pageable, searchQuery));
    return ResponseEntity.ok().body(dtoPage);
  }

  @Operation(
      summary = "get Mentor-Question by ID",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Get information about Question"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid Mentor-QuestionID or EnrolledCourseID supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Only registered users have access to this method",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @GetMapping("/{enrolledCourseId}/mentor-questions/{questionId}")
  public ResponseEntity<MentorQuestionResponseDto> getMentorQuestionById(
      @Parameter(description = "Enrolled course id") @PathVariable Long enrolledCourseId,
      @Parameter(description = "Question id") @PathVariable Long questionId) {
    return ResponseEntity.ok(
        MentorQuestionMapper.toResponseDto(
            mentorQuestionService.findQuestionByIdAndEnrolledCourseId(
                enrolledCourseId, questionId)));
  }
}
