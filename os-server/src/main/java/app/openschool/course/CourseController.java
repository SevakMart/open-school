package app.openschool.course;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.common.response.ResponseMessage;
import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.api.dto.CourseInfoDto;
import app.openschool.course.api.dto.CreateCourseRequest;
import app.openschool.course.api.dto.UpdateCourseRequest;
import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.course.discussion.AnswerService;
import app.openschool.course.discussion.QuestionService;
import app.openschool.course.discussion.dto.AnswerRequestDto;
import app.openschool.course.discussion.dto.AnswerResponseDto;
import app.openschool.course.discussion.dto.QuestionRequestDto;
import app.openschool.course.discussion.dto.QuestionResponseDto;
import app.openschool.course.discussion.dto.UpdateQuestionRequest;
import app.openschool.course.discussion.mapper.AnswerMapper;
import app.openschool.course.discussion.mapper.QuestionMapper;
import app.openschool.faq.FaqService;
import app.openschool.faq.api.dto.CreateFaqRequest;
import app.openschool.faq.api.dto.FaqDto;
import app.openschool.faq.api.dto.UpdateFaqDtoRequest;
import app.openschool.faq.api.mapper.FaqMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.security.Principal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Locale;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/v1/courses")
public class CourseController {

  private final CourseService courseService;
  private final FaqService faqService;
  private final MessageSource messageSource;
  private final QuestionService questionService;
  private final AnswerService answerService;

  public CourseController(
      CourseService courseService,
      MessageSource messageSource,
      FaqService faqService,
      @Qualifier("discussionQuestion") QuestionService questionService,
      @Qualifier("discussionAnswer") AnswerService answerService) {
    this.courseService = courseService;
    this.messageSource = messageSource;
    this.faqService = faqService;
    this.questionService = questionService;
    this.answerService = answerService;
  }

  @Operation(summary = "get course info", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Get details and information about the course"),
        @ApiResponse(
            responseCode = "404",
            description = "Invalid courseId supplied",
            content = @Content(schema = @Schema()))
      })
  @GetMapping("/{id}")
  public ResponseEntity<CourseInfoDto> getCourseInfo(
      @Parameter(description = "Course id") @PathVariable Long id) {
    return ResponseEntity.ok(courseService.findCourseById(id));
  }

  @Operation(summary = "find all courses", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponse(
      responseCode = "200",
      description =
          "Returns paginated list of courses depending on passed filtering parameters "
              + "(by default returns all courses with size of pagination), "
              + "or empty list if no course have been found")
  @GetMapping
  public ResponseEntity<Page<CourseDto>> findAll(
      @Parameter(description = "Title of the course") @RequestParam(required = false)
          String courseTitle,
      @Parameter(description = "List of subCategory ids which were selected")
          @RequestParam(required = false)
          List<Long> subCategoryIds,
      @Parameter(description = "List of language ids which where selected")
          @RequestParam(required = false)
          List<Long> languageIds,
      @Parameter(description = "List of difficulty level ids which where selected")
          @RequestParam(required = false)
          List<Long> difficultyIds,
      @Parameter(
              description =
                  "Includes parameters page, size, and sort which is not required. "
                      + "Page results page you want to retrieve (0..N). "
                      + "Size is count of records per page(1..N). "
                      + "Sorting criteria in the format: property(,asc|desc). "
                      + "Default sort order is ascending. "
                      + "Multiple sort criteria are supported.")
          Pageable pageable) {
    return ResponseEntity.ok(
        CourseMapper.toCourseDtoPage(
            courseService.findAll(
                courseTitle, subCategoryIds, languageIds, difficultyIds, pageable)));
  }

  @Operation(summary = "add course", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Creates new course and returns that"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request arguments supplied or not provided",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Only users with ADMIN or MENTOR role have access to this method",
            content = @Content(schema = @Schema()))
      })
  @PreAuthorize("hasAnyAuthority('ADMIN', 'MENTOR')")
  @PostMapping
  public ResponseEntity<CourseDto> add(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object for creating new course")
          @Valid
          @RequestBody
          CreateCourseRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(CourseMapper.toCourseDto(courseService.add(request)));
  }

  @Operation(summary = "update course", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Modifies the course and returns that"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request arguments supplied or not provided",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Only users with ADMIN or MENTOR role have access to this method",
            content = @Content(schema = @Schema()))
      })
  @PreAuthorize("hasAnyAuthority('ADMIN', 'MENTOR')")
  @PutMapping("/{id}")
  public ResponseEntity<CourseDto> update(
      @Parameter(description = "Course id which will be updated") @PathVariable(value = "id")
          Long courseId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object for updating the course")
          @Valid
          @RequestBody
          UpdateCourseRequest request,
      Principal principal) {
    courseAffiliationVerification(principal, courseId);
    return ResponseEntity.ok()
        .body(CourseMapper.toCourseDto(courseService.update(courseId, request)));
  }

  @Operation(summary = "delete course", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "The course was deleted",
            content = @Content(schema = @Schema())),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid course id supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Only users with ADMIN or MENTOR role have access to this method",
            content = @Content(schema = @Schema()))
      })
  @PreAuthorize("hasAnyAuthority('ADMIN', 'MENTOR')")
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> delete(
      @Parameter(description = "Id of course which will be deleted") @PathVariable(value = "id")
          Long courseId,
      Principal principal) {
    courseAffiliationVerification(principal, courseId);
    courseService.delete(courseId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @Operation(summary = "find all FAQ", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description =
                "Returns a paginated list of FAQs, depending on the "
                    + "sort parameters passed (by default, all FAQs are sorted by FAQ_ID,"
                    + "or an empty list if the answers to FAQs are not found."),
        @ApiResponse(
            responseCode = "403",
            description = "Only users with ADMIN role have access to this method",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/faqs")
  public ResponseEntity<Page<FaqDto>> findAllFaqs(Pageable pageable) {
    return ResponseEntity.ok().body(FaqMapper.toFaqDtoPage(faqService.findAll(pageable)));
  }

  @Operation(
      summary = "find FAQs related to the selected course",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponse(
      responseCode = "200",
      description =
          "Returns paginated list of faqs depending on passed filtering parameters "
              + "(by default returns all faqs with size of pagination), "
              + "or empty list if no faqs have been found.")
  @ApiResponse(
      responseCode = "403",
      description = "Only registered users have access to this method.",
      content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
  @GetMapping("/faqs/{courseId}")
  public ResponseEntity<Page<FaqDto>> findFaqsByCourseId(
      @PathVariable(value = "courseId") Long courseId, Pageable pageable) {
    return ResponseEntity.ok()
        .body(FaqMapper.toFaqDtoPage(faqService.findFaqsByCourseId(courseId, pageable)));
  }

  @Operation(summary = "update FAQs", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Modifies the FAQ and returns that"),
        @ApiResponse(
            responseCode = "400",
            description =
                "Invalid request arguments provided or User is not a mentor of this course",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @PreAuthorize("hasAnyAuthority('ADMIN', 'MENTOR')")
  @PutMapping("/faqs/{faqId}")
  public ResponseEntity<FaqDto> updateFaq(
      @Parameter(description = "FAQ ID to be updated") @PathVariable(value = "faqId") Long faqId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object for updating the FAQ")
          @RequestBody
          UpdateFaqDtoRequest request,
      Principal principal) {

    return ResponseEntity.ok()
        .body(FaqMapper.toFaqDto(faqService.update(request, faqId, principal.getName())));
  }

  @Operation(summary = "add FAQ", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Creates a new FAQ and returns it"),
        @ApiResponse(
            responseCode = "400",
            description =
                "Invalid request arguments supplied "
                    + "or User is not a mentor of the specified course",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Only users with ADMIN or MENTOR role have access to this method",
            content = @Content(schema = @Schema()))
      })
  @PreAuthorize("hasAnyAuthority('ADMIN', 'MENTOR')")
  @PostMapping("/faqs")
  public ResponseEntity<FaqDto> addFaq(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object for creating new course")
          @Valid
          @RequestBody
          CreateFaqRequest request,
      Principal principal)
      throws SQLIntegrityConstraintViolationException {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(FaqMapper.toFaqDto(faqService.add(request, principal.getName())));
  }

  @Operation(summary = "delete FAQ", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "The FAQ was deleted",
            content = @Content(schema = @Schema())),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid FAQ_ID provided or User is not a mentor of this course",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Only users with ADMIN or MENTOR role have access to this method",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @PreAuthorize("hasAnyAuthority('ADMIN', 'MENTOR')")
  @DeleteMapping("/faqs/{faqId}")
  public ResponseEntity<HttpStatus> deleteFaq(
      @Parameter(description = "Id of FAQ which will be deleted") @PathVariable(value = "faqId")
          Long faqId,
      Principal principal) {

    faqService.delete(faqId, principal.getName());
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
  @PostMapping("/enrolled/{enrolledCourseId}/peers-questions")
  public ResponseEntity<QuestionResponseDto> createQuestion(
      @Parameter(description = "ID of the enrolled course") @PathVariable Long enrolledCourseId,
      @Valid @RequestBody QuestionRequestDto requestDto,
      Principal principal) {
    QuestionResponseDto questionResponseDto =
        questionService.create(enrolledCourseId, requestDto, principal.getName());
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
  @PutMapping("/enrolled/{enrolledCourseId}/peers-questions/{peersQuestionId}")
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
  @DeleteMapping("/enrolled/{enrolledCourseId}/peers-questions/{peersQuestionId}")
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
  @GetMapping("/enrolled/{enrolledCourseId}/peers-questions")
  public ResponseEntity<Page<QuestionResponseDto>> findQuestionsByCourseId(
      @Parameter(description = "Enrolled course id") @PathVariable Long enrolledCourseId,
      Pageable pageable) {
    return ResponseEntity.ok()
        .body(
            QuestionMapper.toQuestionDtoPage(
                questionService.findQuestionByCourseId(enrolledCourseId, pageable)));
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
  @GetMapping("/enrolled/{enrolledCourseId}/peers-questions/{questionId}")
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
  @GetMapping("/enrolled/{enrolledCourseId}/peers-questions/answers/{questionId}")
  public ResponseEntity<Page<AnswerResponseDto>> findAnswersByQuestionId(
      @Parameter(description = "Answer id") @PathVariable Long questionId, Pageable pageable) {
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
  @PostMapping("/enrolled/{enrolledCourseId}/peers-answers")
  public ResponseEntity<AnswerResponseDto> createAnswer(
      @Parameter(description = "ID of the enrolled course") @PathVariable Long enrolledCourseId,
      @Valid @RequestBody AnswerRequestDto requestDto,
      Principal principal) {

    AnswerResponseDto answerResponseDto =
        answerService.create(enrolledCourseId, requestDto, principal.getName());
    return ResponseEntity.status(HttpStatus.CREATED).body(answerResponseDto);
  }

    @Operation(summary = "get Answer by ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get information about Answer"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid AnswerID supplied",
                            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Only registered users have access to this method",
                            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
            })
    @GetMapping("/enrolled/{enrolledCourseId}/peers-answers/{answerId}")
    public ResponseEntity<AnswerResponseDto> getAnswerById(
            @Parameter(description = "Answer id") @PathVariable Long answerId) {
    return ResponseEntity.ok(
        AnswerMapper.toAnswerDto(
            answerService.findAnswerById(answerId)));
    }

  private void courseAffiliationVerification(Principal principal, Long courseId) {

    CourseInfoDto course = courseService.findCourseById(courseId);
    if (!course.getMentorDto().getEmailPath().equals(principal.getName())) {
      throw new PermissionDeniedException(
          messageSource.getMessage("permission.denied", null, Locale.ROOT));
    }
  }
}
