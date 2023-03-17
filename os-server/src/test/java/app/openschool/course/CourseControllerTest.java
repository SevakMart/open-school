package app.openschool.course;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.openschool.common.security.JwtTokenProvider;
import app.openschool.common.security.UserPrincipal;
import app.openschool.course.api.CourseGenerator;
import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.course.discussion.AnswerService;
import app.openschool.course.discussion.QuestionService;
import app.openschool.course.discussion.TestHelper;
import app.openschool.course.discussion.dto.AnswerResponseDto;
import app.openschool.course.discussion.dto.QuestionResponseDto;
import app.openschool.course.discussion.dto.UpdateQuestionRequest;
import app.openschool.course.discussion.peers.question.PeersQuestion;
import app.openschool.faq.Faq;
import app.openschool.faq.FaqServiceImpl;
import app.openschool.faq.api.FaqGenerator;
import app.openschool.faq.api.dto.CreateFaqRequest;
import app.openschool.user.User;
import app.openschool.user.role.Role;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CourseControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private JwtTokenProvider jwtTokenProvider;

  @MockBean private CourseServiceImpl courseService;

  @MockBean private FaqServiceImpl faqService;

  @MockBean private @Qualifier("discussionQuestion") QuestionService questionService;

  @MockBean private @Qualifier("discussionAnswer") AnswerService answerService;

  @Test
  void getCourseInfoUnauthorized() throws Exception {
    mockMvc
        .perform(get("/api/v1/courses/1").contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void getCourseWithWrongCourseId() throws Exception {
    when(courseService.findCourseById(1L)).thenThrow(new IllegalArgumentException());
    User user = new User("Test", "pass");
    user.setRole(new Role("STUDENT"));
    String jwt = "Bearer " + jwtTokenProvider.generateJwtToken(new UserPrincipal(user));
    mockMvc
        .perform(
            get("/api/v1/courses/1").contentType(APPLICATION_JSON).header("Authorization", jwt))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getCourseWithRightCourseId() throws Exception {
    when(courseService.findCourseById(1L))
        .thenReturn(
            CourseMapper.toCourseInfoDto(CourseGenerator.generateCourseWithEnrolledCourses()));
    User user = new User("Test", "pass");
    user.setRole(new Role("STUDENT"));
    String jwt = "Bearer " + jwtTokenProvider.generateJwtToken(new UserPrincipal(user));
    mockMvc
        .perform(
            get("/api/v1/courses/1").contentType(APPLICATION_JSON).header("Authorization", jwt))
        .andExpect(status().isOk());
  }

  @Test
  void searchCourses() throws Exception {
    when(courseService.findAll(null, null, null, null, PageRequest.of(0, 2)))
        .thenReturn(new PageImpl<>(List.of(), PageRequest.of(0, 2), 2));
    mockMvc
        .perform(
            get("/api/v1/courses/searched")
                .queryParam("page", "0")
                .queryParam("size", "2")
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void findAllFaqs_unauthorized() throws Exception {

    Pageable pageable = PageRequest.of(0, 5);
    PageImpl<Faq> faqs = FaqGenerator.generateFaqPage(pageable);
    when(faqService.findAll(pageable)).thenReturn(faqs);

    mockMvc
        .perform(
            get("/api/v1/courses/faqs")
                .queryParam("page", "o")
                .queryParam("size", "5")
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void findAllFaqs_withoutAdminRole() throws Exception {

    Pageable pageable = PageRequest.of(0, 5);
    PageImpl<Faq> faqs = FaqGenerator.generateFaqPage(pageable);
    when(faqService.findAll(pageable)).thenReturn(faqs);

    // Role- MENTOR
    String jwt = generateJwtToken();
    mockMvc
        .perform(
            get("/api/v1/courses/faqs")
                .queryParam("page", "o")
                .queryParam("size", "5")
                .header("Authorization", jwt)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  void findFaqsByCourseId_authorized() throws Exception {

    long courseId = 1L;
    Faq faq = FaqGenerator.generateFaq();
    faq.getCourse().setId(courseId);
    Page<Faq> faqs = new PageImpl<>(List.of(faq));

    Pageable pageable = PageRequest.of(0, 5);

    when(faqService.findFaqsByCourseId(courseId, pageable)).thenReturn(faqs);

    String jwt = generateJwtToken();
    mockMvc
        .perform(
            get("/api/v1/courses/faqs/1")
                .queryParam("courseId", "1")
                .queryParam("page", "o")
                .queryParam("size", "5")
                .header("Authorization", jwt)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void findFaqsByCourseId_unauthorized() throws Exception {

    Page<Faq> faqs = new PageImpl<>(List.of(FaqGenerator.generateFaq()));

    Pageable pageable = PageRequest.of(0, 5);

    when(faqService.findFaqsByCourseId(1L, pageable)).thenReturn(faqs);
    mockMvc
        .perform(
            get("/api/v1/courses/faqs/1")
                .queryParam("courseId", "1")
                .queryParam("page", "o")
                .queryParam("size", "5")
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void addFaq_unauthorized() throws Exception {

    CreateFaqRequest request = new CreateFaqRequest("question", "answer", 1L);
    String email = "poxos@gm.com";
    String requestBody = "{\"question\": \"question\",\"answer\": \"answer\", \"courseId\": 1}";
    when(faqService.add(request, email)).thenReturn(new Faq());

    mockMvc
        .perform(post("/api/v1/courses/faqs").content(requestBody).contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void deleteFaq() throws Exception {

    String jwt = generateJwtToken();
    doNothing().when(faqService).delete(any(), anyString());
    mockMvc
        .perform(
            delete("/api/v1/courses/faqs/1")
                .header("Authorization", jwt)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  void createQuestion_withCorrectData() throws Exception {

    when(questionService.create(anyLong(), any(), anyString()))
        .thenReturn(new QuestionResponseDto());
    String requestBody = "{\"text\": \"Any question\"}";
    String jwt = generateJwtToken();

    mockMvc
        .perform(
            post("/api/v1/courses/enrolled/1/peers-questions")
                .header("Authorization", jwt)
                .content(requestBody)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  void createQuestion_unauthorized() throws Exception {

    when(questionService.create(anyLong(), any(), anyString()))
        .thenReturn(new QuestionResponseDto());
    String requestBody = "{\"text\": \"Any question\"}";

    mockMvc
        .perform(
            post("/api/v1/courses/enrolled/1/peers-questions")
                .content(requestBody)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void createQuestion_invalidArgument() throws Exception {

    when(questionService.create(anyLong(), any(), anyString()))
        .thenReturn(new QuestionResponseDto());

    String wrongRequestBody = "{\"wrong\": \"Any question\"}";
    String jwt = generateJwtToken();

    mockMvc
        .perform(
            post("/api/v1/courses/enrolled/1/peers-questions")
                .header("Authorization", jwt)
                .content(wrongRequestBody)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void createQuestion_incorrectEnrolledCourseId() throws Exception {

    long wrongEnrolledCourseId = 999L;

    when(questionService.create(anyLong(), any(), anyString()))
        .thenThrow(IllegalArgumentException.class);

    String requestBody = "{\"text\": \"Any question\"}";
    String jwt = generateJwtToken();

    mockMvc
        .perform(
            post("/api/v1/courses/enrolled/" + wrongEnrolledCourseId + "/peers-questions")
                .header("Authorization", jwt)
                .content(requestBody)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void updatePeersQuestion_withCorrectData() throws Exception {

    UpdateQuestionRequest request = new UpdateQuestionRequest();
    request.setText("Update question");
    PeersQuestion question = TestHelper.createDiscussionPeersQuestion();
    EnrolledCourse enrolledCourse = CourseGenerator.generateEnrolledCourse();
    enrolledCourse.setId(1L);
    enrolledCourse.setCourse(question.getCourse());
    enrolledCourse.setUser(question.getUser());

    when(questionService.update(any(), anyLong(), anyLong(), anyString()))
        .thenReturn(
            new PeersQuestion(
                question.getId(),
                request.getText(),
                question.getUser(),
                question.getCourse(),
                null,
                null));
    String jwt = generateJwtToken();
    String requestBody = "{\"text\": \"Update question\"}";

    mockMvc
        .perform(
            put("/api/v1/courses/enrolled/"
                    + enrolledCourse.getId()
                    + "/peers-questions/"
                    + question.getId())
                .header("Authorization", jwt)
                .content(requestBody)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void updatePeersQuestion_unauthorized() throws Exception {

    when(questionService.update(any(), anyLong(), anyLong(), anyString()))
        .thenReturn(new PeersQuestion());

    String requestBody = "{\"text\": \"Update question\"}";

    mockMvc
        .perform(
            put("/api/v1/courses/enrolled/1/peers-questions/1")
                .content(requestBody)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void updatePeersQuestion_withIncorrectArgument() throws Exception {

    when(questionService.update(any(), anyLong(), anyLong(), anyString()))
        .thenReturn(new PeersQuestion());
    String jwt = generateJwtToken();
    String wrongRequestBody = "{\"wrong\": \"Update question\"}";

    mockMvc
        .perform(
            put("/api/v1/courses/enrolled/1/peers-questions/1")
                .header("Authorization", jwt)
                .content(wrongRequestBody)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void deletePeersQuestion_withCorrectData() throws Exception {

    PeersQuestion question = TestHelper.createDiscussionPeersQuestion();
    EnrolledCourse enrolledCourse = CourseGenerator.generateEnrolledCourse();
    enrolledCourse.setId(1L);

    doNothing()
        .when(questionService)
        .delete(question.getId(), enrolledCourse.getId(), question.getUser().getEmail());
    String jwt = generateJwtToken();

    mockMvc
        .perform(
            delete(
                    "/api/v1/courses/enrolled/"
                        + enrolledCourse.getId()
                        + "/peers-questions/"
                        + question.getId())
                .header("Authorization", jwt)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  void deletePeersQuestion_unauthorized() throws Exception {

    PeersQuestion question = TestHelper.createDiscussionPeersQuestion();
    EnrolledCourse enrolledCourse = CourseGenerator.generateEnrolledCourse();
    enrolledCourse.setId(1L);

    doNothing()
        .when(questionService)
        .delete(question.getId(), enrolledCourse.getId(), question.getUser().getEmail());

    mockMvc
        .perform(
            delete(
                    "/api/v1/courses/enrolled/"
                        + enrolledCourse.getId()
                        + "/peers-questions/"
                        + question.getId())
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void deletePeersQuestion_incorrectArgument() throws Exception {

    EnrolledCourse enrolledCourse = CourseGenerator.generateEnrolledCourse();
    enrolledCourse.setId(1L);
    long wrongQuestionId = 999L;

    doThrow(IllegalArgumentException.class)
        .when(questionService)
        .delete(anyLong(), any(), anyString());

    String jwt = generateJwtToken();

    mockMvc
        .perform(
            delete(
                    "/api/v1/courses/enrolled/"
                        + enrolledCourse.getId()
                        + "/peers-questions/"
                        + wrongQuestionId)
                .header("Authorization", jwt)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void createAnswer_withCorrectData() throws Exception {

    when(answerService.create(anyLong(), any(), anyString())).thenReturn(new AnswerResponseDto());
    String requestBody = "{\"text\": \"Any answer\", \"questionId\": 1}";
    String jwt = generateJwtToken();

    mockMvc
        .perform(
            post("/api/v1/courses/enrolled/1/peers-answers")
                .header("Authorization", jwt)
                .content(requestBody)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  void createAnswer_unauthorized() throws Exception {

    when(answerService.create(anyLong(), any(), anyString())).thenReturn(new AnswerResponseDto());
    String requestBody = "{\"text\": \"Any answer\", \"questionId\": 1}";

    mockMvc
        .perform(
            post("/api/v1/courses/enrolled/1/peers-answers")
                .content(requestBody)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void createAnswer_invalidArgument() throws Exception {

    when(answerService.create(anyLong(), any(), anyString())).thenReturn(new AnswerResponseDto());

    String wrongRequestBody = "{\"wrong\": \"Any question\", \"questionId\": 1}";
    String jwt = generateJwtToken();

    mockMvc
        .perform(
            post("/api/v1/courses/enrolled/1/peers-answers")
                .header("Authorization", jwt)
                .content(wrongRequestBody)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void createAnswer_incorrectEnrolledCourseId() throws Exception {

    long wrongEnrolledCourseId = 999L;

    when(answerService.create(anyLong(), any(), anyString()))
        .thenThrow(IllegalArgumentException.class);

    String requestBody = "{\"text\": \"Any answer\", \"questionId\": 1}";
    String jwt = generateJwtToken();

    mockMvc
        .perform(
            post("/api/v1/courses/enrolled/" + wrongEnrolledCourseId + "/peers-answers")
                .header("Authorization", jwt)
                .content(requestBody)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  private String generateJwtToken() {
    User user = new User("Test", "pass");
    user.setRole(new Role("MENTOR"));
    return "Bearer " + jwtTokenProvider.generateJwtToken(new UserPrincipal(user));
  }
}
