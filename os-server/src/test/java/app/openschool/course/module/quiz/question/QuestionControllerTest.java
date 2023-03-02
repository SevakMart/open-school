package app.openschool.course.module.quiz.question;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.openschool.common.security.JwtTokenProvider;
import app.openschool.common.security.UserPrincipal;
import app.openschool.course.module.quiz.Quiz;
import app.openschool.course.module.quiz.QuizRepository;
import app.openschool.course.module.quiz.question.util.QuestionGenerator;
import app.openschool.course.module.quiz.util.QuizGenerator;
import app.openschool.user.User;
import app.openschool.user.api.UserGenerator;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class QuestionControllerTest {
  private static final String UPDATE_QUESTION_REQUEST =
      "{\n"
          + "  \"question\": \"What is Java bytecode\",\n"
          + "  \"rightAnswersCount\": 3,\n"
          + "  \"answerOptions\": [\n"
          + "    {\n"
          + "      \"answerOption\": \"Java bytecode is the instruction\",\n"
          + "      \"rightAnswer\": true\n"
          + "    }\n"
          + "  ],\n"
          + "  \"questionType\": \"MULTIPLE_CHOICE\"\n"
          + "}";

  @Autowired private MockMvc mockMvc;

  @Autowired private JwtTokenProvider jwtTokenProvider;

  @MockBean QuestionServiceImpl questionService;
  @MockBean QuizRepository quizRepository;
  private static final String AUTHORIZATION = "Authorization";

  @Test
  void deleteQuestion_withCorrectCredentials_isNoContent() throws Exception {
    when(questionService.deleteQuestion(anyLong())).thenReturn(true);
    when(quizRepository.findById(anyLong())).thenReturn(Optional.of(QuizGenerator.generateQuiz()));
    mockMvc
        .perform(
            delete("/api/v1/1/questions/1")
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateMentor())))
        .andExpect(status().isNoContent());
  }

  @Test
  void deleteQuestion_withIncorrectQuestionId_returnNotFound() throws Exception {
    when(questionService.deleteQuestion(anyLong())).thenReturn(false);
    when(quizRepository.findById(anyLong())).thenReturn(Optional.of(QuizGenerator.generateQuiz()));

    mockMvc
        .perform(
            delete("/api/v1/1/questions/1")
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateMentor())))
        .andExpect(status().isNotFound());
  }

  @Test
  void deleteQuestion_withIncorrectMentor_isBadRequest() throws Exception {
    when(questionService.deleteQuestion(anyLong())).thenThrow(IllegalArgumentException.class);
    Quiz quiz = QuizGenerator.generateQuiz();
    quiz.getModule().getCourse().getMentor().setEmail("anotherEmail");
    when(quizRepository.findById(anyLong())).thenReturn(Optional.of(quiz));

    mockMvc
        .perform(
            delete("/api/v1/1/questions/1")
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateMentor())))
        .andExpect(status().isForbidden());
  }

  @Test
  void deleteQuestion_withStudent_isForbidden() throws Exception {
    mockMvc
        .perform(
            delete("/api/v1/1/questions/1")
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateStudent())))
        .andExpect(status().isForbidden());
  }

  @Test
  void deleteQuestion_withoutJwtToken_isUnauthorized() throws Exception {
    mockMvc.perform(delete("/api/v1/1/questions/1")).andExpect(status().isUnauthorized());
  }

  @Test
  void updateQuestion_withCorrectCredentials_isNoContent() throws Exception {
    when(questionService.updateQuestion(anyLong(), any())).thenReturn(true);
    when(quizRepository.findById(anyLong())).thenReturn(Optional.of(QuizGenerator.generateQuiz()));

    mockMvc
        .perform(
            put("/api/v1/1/questions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(UPDATE_QUESTION_REQUEST)
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateMentor())))
        .andExpect(status().isNoContent());
  }

  @Test
  void updateQuestion_withIncorrectQuestionId_returnNotFound() throws Exception {
    when(questionService.updateQuestion(anyLong(), any())).thenReturn(false);
    when(quizRepository.findById(anyLong())).thenReturn(Optional.of(QuizGenerator.generateQuiz()));

    mockMvc
        .perform(
            put("/api/v1/1/questions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(UPDATE_QUESTION_REQUEST)
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateMentor())))
        .andExpect(status().isNotFound());
  }

  @Test
  void updateQuestion_withIncorrectMentor_isBadRequest() throws Exception {
    when(questionService.updateQuestion(anyLong(), any()))
        .thenThrow(IllegalArgumentException.class);
    Quiz quiz = QuizGenerator.generateQuiz();
    quiz.getModule().getCourse().getMentor().setEmail("anotherEmail");
    when(quizRepository.findById(anyLong())).thenReturn(Optional.of(quiz));
    mockMvc
        .perform(
            put("/api/v1/1/questions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(UPDATE_QUESTION_REQUEST)
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateMentor())))
        .andExpect(status().isForbidden());
  }

  @Test
  void updateQuestion_withStudent_isForbidden() throws Exception {
    mockMvc
        .perform(
            put("/api/v1/1/questions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(UPDATE_QUESTION_REQUEST)
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateStudent())))
        .andExpect(status().isForbidden());
  }

  @Test
  void updateQuestion_withoutJwtToken_isUnauthorized() throws Exception {
    mockMvc
        .perform(
            put("/api/v1/1/questions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(UPDATE_QUESTION_REQUEST))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void findAllByQuizId_withCorrectArg_returnOk() throws Exception {
    when(questionService.findAllByQuizId(anyLong(), any()))
        .thenReturn(QuestionGenerator.generateQuestionDtoPage());
    mockMvc
        .perform(
            get("/api/v1/1/questions")
                .contentType(APPLICATION_JSON)
                .content(UPDATE_QUESTION_REQUEST)
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateMentor())))
        .andExpect(status().isOk());
  }

  @Test
  void findAllByQuizId_withoutJwtToken_isUnauthorized() throws Exception {
    mockMvc.perform(get("/api/v1/1/questions")).andExpect(status().isUnauthorized());
  }

  private String generateJwtToken(User user) {
    return "Bearer " + jwtTokenProvider.generateJwtToken(new UserPrincipal(user));
  }
}
