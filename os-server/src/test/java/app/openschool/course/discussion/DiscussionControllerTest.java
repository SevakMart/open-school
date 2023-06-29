package app.openschool.course.discussion;

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
import app.openschool.course.EnrolledCourse;
import app.openschool.course.api.CourseGenerator;
import app.openschool.course.discussion.dto.UpdateQuestionRequest;
import app.openschool.course.discussion.mentor.question.MentorQuestion;
import app.openschool.course.discussion.peers.answer.PeersAnswer;
import app.openschool.course.discussion.peers.question.PeersQuestion;
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
public class DiscussionControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private JwtTokenProvider jwtTokenProvider;

  @MockBean
  private @Qualifier("discussionQuestion") QuestionService questionService;

  @MockBean
  private @Qualifier("discussionQuestionMentor") QuestionService mentorQuestionService;

  @MockBean
  private @Qualifier("discussionAnswer") AnswerService answerService;

  @Test
  void createQuestion_withCorrectData() throws Exception {

    when(questionService.create(anyLong(), any(), anyString()))
        .thenReturn(TestHelper.createDiscussionPeersQuestion());
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

    when(questionService.create(anyLong(), any(), anyString())).thenReturn(new PeersQuestion());
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

    when(questionService.create(anyLong(), any(), anyString())).thenReturn(new PeersQuestion());

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
  void findQuestionsByCourseId_withCorrectData() throws Exception {

    Page questions = new PageImpl<>(List.of(TestHelper.createDiscussionPeersQuestion()));

    long enrolledCourseId = 1L;
    String jwt = generateJwtToken();
    String q = "Question";

    Pageable pageable = PageRequest.of(0, 5);

    when(questionService.findQuestionByCourseId(enrolledCourseId, pageable, q)).thenReturn(questions);
    mockMvc
        .perform(
            get("/api/v1/courses/enrolled/" + enrolledCourseId + "/peers-questions")
                .queryParam("page", "o")
                .queryParam("size", "5")
                .queryParam("q", q)
                .header("Authorization", jwt)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void findQuestionsByCourseId_unauthorized() throws Exception {

    Page questions = new PageImpl<>(List.of(TestHelper.createDiscussionPeersQuestion()));
    String q = "Question";

    long enrolledCourseId = 1L;
    Pageable pageable = PageRequest.of(0, 5);
    when(questionService.findQuestionByCourseId(enrolledCourseId, pageable, q)).thenReturn(questions);

    mockMvc
        .perform(
            get("/api/v1/courses/enrolled/" + enrolledCourseId + "/peers-questions")
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void getQuestionById_withCorrectData() throws Exception {

    long enrolledCourseId = 1L;
    long questionId = 1L;
    String jwt = generateJwtToken();

    when(questionService.findQuestionByIdAndEnrolledCourseId(enrolledCourseId, questionId))
        .thenReturn(new PeersQuestion());

    mockMvc
        .perform(
            get("/api/v1/courses/enrolled/" + enrolledCourseId + "/peers-questions" + questionId)
                .header("Authorization", jwt)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void getQuestionById_withIncorrectData() throws Exception {

    long enrolledCourseId = 1L;
    long wrongQuestionId = 1L;
    String jwt = generateJwtToken();

    when(questionService.findQuestionByIdAndEnrolledCourseId(enrolledCourseId, wrongQuestionId))
        .thenThrow(IllegalArgumentException.class);

    mockMvc
        .perform(
            get("/api/v1/courses/enrolled/"
                    + enrolledCourseId
                    + "/peers-questions/"
                    + wrongQuestionId)
                .header("Authorization", jwt)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void findAnswersByQuestionId_withCorrectData() throws Exception {

    Page answers = new PageImpl<>(List.of(TestHelper.createDiscussionPeersAnswer()));

    long enrolledCourseId = 1L;
    long questionId = 1L;
    String jwt = generateJwtToken();

    Pageable pageable = PageRequest.of(0, 5);
    when(answerService.findAnswerByQuestionId(questionId, pageable)).thenReturn(answers);

    mockMvc
        .perform(
            get("/api/v1/courses/enrolled/"
                    + enrolledCourseId
                    + "/peers-questions/answers/"
                    + questionId)
                .queryParam("page", "o")
                .queryParam("size", "5")
                .header("Authorization", jwt)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void findAnswersByQuestionId_unauthorized() throws Exception {

    Page answers = new PageImpl<>(List.of(TestHelper.createDiscussionPeersAnswer()));

    long enrolledCourseId = 1L;
    long questionId = 1L;

    Pageable pageable = PageRequest.of(0, 5);
    when(answerService.findAnswerByQuestionId(questionId, pageable)).thenReturn(answers);
    mockMvc
        .perform(
            get("/api/v1/courses/enrolled/"
                    + enrolledCourseId
                    + "/peers-questions/answers/"
                    + questionId)
                .queryParam("page", "o")
                .queryParam("size", "5")
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void createAnswer_withCorrectData() throws Exception {

    PeersAnswer answer = TestHelper.createDiscussionPeersAnswer();
    when(answerService.create(anyLong(), any(), anyString())).thenReturn(answer);
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

    when(answerService.create(anyLong(), any(), anyString())).thenReturn(new PeersAnswer());
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

    when(answerService.create(anyLong(), any(), anyString())).thenReturn(new PeersAnswer());

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

  @Test
  void getAnswerById_withCorrectData() throws Exception {

    PeersAnswer peersAnswer = TestHelper.createDiscussionPeersAnswer();
    long enrolledCourseId = 1L;
    long answerId = 1L;
    String jwt = generateJwtToken();

    when(answerService.findAnswerById(answerId)).thenReturn(peersAnswer);

    mockMvc
        .perform(
            get("/api/v1/courses/enrolled/" + enrolledCourseId + "/peers-answers/" + answerId)
                .header("Authorization", jwt)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void getAnswerById_withIncorrectData() throws Exception {

    long enrolledCourseId = 1L;
    long wrongAnswerId = 1L;
    String jwt = generateJwtToken();

    when(answerService.findAnswerById(wrongAnswerId)).thenThrow(IllegalArgumentException.class);

    mockMvc
        .perform(
            get("/api/v1/courses/enrolled/" + enrolledCourseId + "/peers-answers/" + wrongAnswerId)
                .header("Authorization", jwt)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getAnswerById_unauthorized() throws Exception {

    PeersAnswer peersAnswer = TestHelper.createDiscussionPeersAnswer();
    long enrolledCourseId = 1L;
    long answerId = 1L;

    when(answerService.findAnswerById(answerId)).thenReturn(peersAnswer);

    mockMvc
        .perform(
            get("/api/v1/courses/enrolled/" + enrolledCourseId + "/peers-answers/" + answerId)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void createMentorQuestion_withCorrectData() throws Exception {

    MentorQuestion mentorQuestion = TestHelper.createMentorQuestion();
    when(mentorQuestionService.create(anyLong(), any(), anyString())).thenReturn(mentorQuestion);
    String requestBody = "{\"text\": \"Any question\"}";
    String jwt = generateJwtToken();

    mockMvc
        .perform(
            post("/api/v1/courses/enrolled/1/mentor-questions")
                .header("Authorization", jwt)
                .content(requestBody)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  void createMentorQuestion_unauthorized() throws Exception {

    when(mentorQuestionService.create(anyLong(), any(), anyString()))
        .thenReturn(new MentorQuestion());
    String requestBody = "{\"text\": \"Any question\"}";

    mockMvc
        .perform(
            post("/api/v1/courses/enrolled/1/mentor-questions")
                .content(requestBody)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void createMentorQuestion_invalidArgument() throws Exception {

    when(mentorQuestionService.create(anyLong(), any(), anyString()))
        .thenReturn(new MentorQuestion());

    String wrongRequestBody = "{\"wrong\": \"Any question\"}";
    String jwt = generateJwtToken();

    mockMvc
        .perform(
            post("/api/v1/courses/enrolled/1/mentor-questions")
                .header("Authorization", jwt)
                .content(wrongRequestBody)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void createMentorQuestion_incorrectEnrolledCourseId() throws Exception {

    long wrongEnrolledCourseId = 999L;

    when(mentorQuestionService.create(anyLong(), any(), anyString()))
        .thenThrow(IllegalArgumentException.class);

    String requestBody = "{\"text\": \"Any question\"}";
    String jwt = generateJwtToken();

    mockMvc
        .perform(
            post("/api/v1/courses/enrolled/" + wrongEnrolledCourseId + "/mentor-questions")
                .header("Authorization", jwt)
                .content(requestBody)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void findMentorQuestionsByCourseId_withCorrectData() throws Exception {

    Page questions = new PageImpl<>(List.of(TestHelper.createMentorQuestion()));

    long enrolledCourseId = 1L;
    String jwt = generateJwtToken();
    String q = "Question";

    Pageable pageable = PageRequest.of(0, 5);

    when(mentorQuestionService.findQuestionByCourseId(enrolledCourseId, pageable, q))
        .thenReturn(questions);
    mockMvc
        .perform(
            get("/api/v1/courses/enrolled/" + enrolledCourseId + "/mentor-questions")
                .queryParam("page", "o")
                .queryParam("size", "5")
                .queryParam("q", q)
                .header("Authorization", jwt)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void findMentorQuestionsByCourseId_unauthorized() throws Exception {

    Page questions = new PageImpl<>(List.of(TestHelper.createMentorQuestion()));
    String q = "Question";

    long enrolledCourseId = 1L;
    Pageable pageable = PageRequest.of(0, 5);
    when(mentorQuestionService.findQuestionByCourseId(enrolledCourseId, pageable, q))
        .thenReturn(questions);

    mockMvc
        .perform(
            get("/api/v1/courses/enrolled/" + enrolledCourseId + "/mentor-questions")
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void getMentorQuestionById_withCorrectData() throws Exception {

    long enrolledCourseId = 1L;
    long questionId = 1L;
    String jwt = generateJwtToken();

    when(mentorQuestionService.findQuestionByIdAndEnrolledCourseId(enrolledCourseId, questionId))
        .thenReturn(new MentorQuestion());

    mockMvc
        .perform(
            get("/api/v1/courses/enrolled/" + enrolledCourseId + "/mentor-questions" + questionId)
                .header("Authorization", jwt)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void getMentorQuestionById_withIncorrectData() throws Exception {

    long enrolledCourseId = 1L;
    long wrongQuestionId = 1L;
    String jwt = generateJwtToken();

    when(mentorQuestionService.findQuestionByIdAndEnrolledCourseId(
            enrolledCourseId, wrongQuestionId))
        .thenThrow(IllegalArgumentException.class);

    mockMvc
        .perform(
            get("/api/v1/courses/enrolled/"
                    + enrolledCourseId
                    + "/mentor-questions/"
                    + wrongQuestionId)
                .header("Authorization", jwt)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  private String generateJwtToken() {
    User user = new User("Test", "pass");
    user.setRole(new Role("MENTOR"));
    return "Bearer " + jwtTokenProvider.generateJwtToken(new UserPrincipal(user));
  }
}
