package app.openschool.course.module.quiz.question;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.openschool.common.security.JwtTokenProvider;
import app.openschool.common.security.UserPrincipal;
import app.openschool.user.User;
import app.openschool.user.api.UserGenerator;
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
class QuestionControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private JwtTokenProvider jwtTokenProvider;

  @MockBean QuestionServiceImpl questionService;

  private static final String AUTHORIZATION = "Authorization";

  @Test
  void deleteQuestion_withCorrectCredentials_isNoContent() throws Exception {
    when(questionService.deleteQuestion(anyLong())).thenReturn(true);

    mockMvc
        .perform(
            delete("/api/v1/questions/1")
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateMentor())))
        .andExpect(status().isNoContent());
  }

  @Test
  void deleteQuestion_withIncorrectQuestionId_returnNotFound() throws Exception {
    when(questionService.deleteQuestion(anyLong())).thenReturn(false);

    mockMvc
        .perform(
            delete("/api/v1/questions/1")
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateMentor())))
        .andExpect(status().isNotFound());
  }

  @Test
  void deleteQuestion_withIncorrectMentor_isBadRequest() throws Exception {
    when(questionService.deleteQuestion(anyLong())).thenThrow(IllegalArgumentException.class);

    mockMvc
        .perform(
            delete("/api/v1/questions/1")
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateMentor())))
        .andExpect(status().isBadRequest());
  }

  @Test
  void deleteQuestion_withStudent_isForbidden() throws Exception {
    mockMvc
        .perform(
            delete("/api/v1/questions/1")
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateStudent())))
        .andExpect(status().isForbidden());
  }

  @Test
  void deleteQuestion_withoutJwtToken_isUnauthorized() throws Exception {
    mockMvc.perform(delete("/api/v1/questions/1")).andExpect(status().isUnauthorized());
  }

  private String generateJwtToken(User user) {
    return "Bearer " + jwtTokenProvider.generateJwtToken(new UserPrincipal(user));
  }
}
