package app.openschool.course.module.quiz;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.openschool.common.security.JwtTokenProvider;
import app.openschool.common.security.UserPrincipal;
import app.openschool.course.module.quiz.api.mapper.QuizMapper;
import app.openschool.course.module.quiz.util.QuizGenerator;
import app.openschool.user.User;
import app.openschool.user.role.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class QuizControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private JwtTokenProvider jwtTokenProvider;

  @MockBean private QuizServiceImpl quizService;

  private static final String REQUEST_BODY =
      "{\n"
          + "  \"maxGrade\": \"10\",\n"
          + "  \"passingScore\": \"7\",\n"
          + "  \"questions\": [\n"
          + "    {\n"
          + "      \"question\": \"What is Java bytecode\",\n"
          + "      \"answerOptions\": [\n"
          + "        {\n"
          + "          \"answerOption\": \"Java bytecode is the instruction set for the JVM\",\n"
          + "          \"rightAnswer\": \"true\"\n"
          + "        }\n"
          + "      ],\n"
          + "      \"questionType\": \"MULTIPLE_CHOICE\"\n"
          + "    }\n"
          + "  ]\n"
          + "}";

  private static final String AUTHORIZATION = "Authorization";

  @Test
  void createQuiz_withCorrectCredentials_isCreated() throws Exception {
    Quiz quiz = QuizGenerator.generateQuiz();
    when(quizService.createQuiz(anyLong(), any())).thenReturn(Optional.of(quiz));

    mockMvc
        .perform(
            post("/api/v1/quizzes/1")
                .contentType(APPLICATION_JSON)
                .content(REQUEST_BODY)
                .header(AUTHORIZATION, generateJwtToken(generateMentor())))
        .andExpect(status().isCreated())
        .andExpect(
            content().json(new ObjectMapper().writeValueAsString(QuizMapper.quizToQuizDto(quiz))));
  }

  @Test
  void createQuiz_withIncorrectModuleId_returnNotFound() throws Exception {
    when(quizService.createQuiz(anyLong(), any())).thenReturn(Optional.empty());

    mockMvc
        .perform(
            post("/api/v1/quizzes/1")
                .contentType(APPLICATION_JSON)
                .content(REQUEST_BODY)
                .header(AUTHORIZATION, generateJwtToken(generateMentor())))
        .andExpect(status().isNotFound());
  }

  @Test
  void createQuiz_withStudent_isForbidden() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/quizzes/1")
                .contentType(APPLICATION_JSON)
                .content(REQUEST_BODY)
                .header(AUTHORIZATION, generateJwtToken(generateStudent())))
        .andExpect(status().isForbidden());
  }

  @Test
  void createQuiz_withoutJwtToken_isUnauthorized() throws Exception {
    mockMvc
        .perform(post("/api/v1/quizzes/1").contentType(APPLICATION_JSON).content(REQUEST_BODY))
        .andExpect(status().isUnauthorized());
  }

  private String generateJwtToken(User user) {
    return "Bearer " + jwtTokenProvider.generateJwtToken(new UserPrincipal(user));
  }

  private User generateMentor() {
    User mentor = new User("Test", "pass");
    mentor.setRole(new Role("MENTOR"));
    return mentor;
  }

  private User generateStudent() {
    User mentor = new User("Test", "pass");
    mentor.setRole(new Role("STUDENT"));
    return mentor;
  }
}
