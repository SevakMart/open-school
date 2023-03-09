package app.openschool.course.module.quiz;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.openschool.common.security.JwtTokenProvider;
import app.openschool.common.security.UserPrincipal;
import app.openschool.course.module.Module;
import app.openschool.course.module.ModuleRepository;
import app.openschool.course.module.api.ModuleGenerator;
import app.openschool.course.module.quiz.api.mapper.QuizMapper;
import app.openschool.course.module.quiz.util.EnrolledQuizAssessmentResponseDtoGenerator;
import app.openschool.course.module.quiz.util.QuizGenerator;
import app.openschool.user.User;
import app.openschool.user.api.UserGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Disabled;
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
@Disabled
public class QuizControllerTest {

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

  @Autowired private MockMvc mockMvc;

  @Autowired private JwtTokenProvider jwtTokenProvider;

  @MockBean private QuizServiceImpl quizService;
  @MockBean private ModuleRepository moduleRepository;

  private static final String AUTHORIZATION = "Authorization";

  @Test
  void createQuiz_withCorrectCredentials_isCreated() throws Exception {
    Quiz quiz = QuizGenerator.generateQuiz();
    when(quizService.createQuiz(anyLong(), any())).thenReturn(Optional.of(quiz));
    when(moduleRepository.findById(anyLong()))
        .thenReturn(Optional.of(ModuleGenerator.generateModule()));
    mockMvc
        .perform(
            post("/api/v1/1/quizzes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(REQUEST_BODY)
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateMentor())))
        .andExpect(status().isCreated())
        .andExpect(
            content().json(new ObjectMapper().writeValueAsString(QuizMapper.quizToQuizDto(quiz))));
  }

  @Test
  void createQuiz_withIncorrectModuleId_returnNotFound() throws Exception {
    when(quizService.createQuiz(anyLong(), any())).thenReturn(Optional.empty());
    when(moduleRepository.findById(anyLong()))
        .thenReturn(Optional.of(ModuleGenerator.generateModule()));
    mockMvc
        .perform(
            post("/api/v1/1/quizzes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(REQUEST_BODY)
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateMentor())))
        .andExpect(status().isNotFound());
  }

  @Test
  void createQuiz_withIncorrectMentor_isForbidden() throws Exception {
    when(quizService.createQuiz(anyLong(), any())).thenThrow(IllegalArgumentException.class);
    Module module = ModuleGenerator.generateModule();
    module.getCourse().getMentor().setEmail("anotherEmail");
    when(moduleRepository.findById(anyLong())).thenReturn(Optional.of(module));
    mockMvc
        .perform(
            post("/api/v1/1/quizzes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(REQUEST_BODY)
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateMentor())))
        .andExpect(status().isForbidden());
  }

  @Test
  void createQuiz_withStudent_isForbidden() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/1/quizzes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(REQUEST_BODY)
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateStudent())))
        .andExpect(status().isForbidden());
  }

  @Test
  void createQuiz_withoutJwtToken_isUnauthorized() throws Exception {

    mockMvc
        .perform(
            post("/api/v1/1/quizzes").contentType(MediaType.APPLICATION_JSON).content(REQUEST_BODY))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void deleteQuiz_withCorrectCredentials_isNoContent() throws Exception {
    when(quizService.deleteQuiz(anyLong())).thenReturn(true);
    when(moduleRepository.findById(anyLong()))
        .thenReturn(Optional.of(ModuleGenerator.generateModule()));
    mockMvc
        .perform(
            delete("/api/v1/1/quizzes/1")
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateMentor())))
        .andExpect(status().isNoContent());
  }

  @Test
  void deleteQuiz_withIncorrectQuizId_returnNotFound() throws Exception {
    when(quizService.deleteQuiz(anyLong())).thenReturn(false);
    when(moduleRepository.findById(anyLong()))
        .thenReturn(Optional.of(ModuleGenerator.generateModule()));

    mockMvc
        .perform(
            delete("/api/v1/1/quizzes/1")
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateMentor())))
        .andExpect(status().isNotFound());
  }

  @Test
  void deleteQuiz_withIncorrectMentor_isForbidden() throws Exception {
    Module module = ModuleGenerator.generateModule();
    module.getCourse().getMentor().setEmail("anotherEmail@gmail.com");
    when(moduleRepository.findById(anyLong())).thenReturn(Optional.of(module));

    mockMvc
        .perform(
            delete("/api/v1/1/quizzes/1")
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateMentor())))
        .andExpect(status().isForbidden());
  }

  @Test
  void deleteQuiz_withStudent_isForbidden() throws Exception {
    mockMvc
        .perform(
            delete("/api/v1/1/quizzes/1")
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateStudent())))
        .andExpect(status().isForbidden());
  }

  @Test
  void deleteQuiz_withoutJwtToken_isUnauthorized() throws Exception {
    mockMvc.perform(delete("/api/v1/1/quizzes/1")).andExpect(status().isUnauthorized());
  }

  @Test
  void updateQuiz_withCorrectArg_returnOk() throws Exception {
    when(quizService.updateQuiz(anyLong(), any())).thenReturn(QuizGenerator.generateQuiz());
    when(moduleRepository.findById(anyLong()))
        .thenReturn(Optional.of(ModuleGenerator.generateModule()));
    mockMvc
        .perform(
            patch("/api/v1/1/quizzes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(REQUEST_BODY)
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateMentor())))
        .andExpect(status().isOk());
  }

  @Test
  void updateQuiz_withIncorrectMentor_isForbidden() throws Exception {
    when(quizService.updateQuiz(anyLong(), any())).thenReturn(QuizGenerator.generateQuiz());

    when(moduleRepository.findById(anyLong()))
        .thenReturn(Optional.of(ModuleGenerator.generateModuleWithAnotherUser()));
    mockMvc
        .perform(
            patch("/api/v1/1/quizzes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(REQUEST_BODY)
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateMentor())))
        .andExpect(status().isForbidden());
  }

  @Test
  void updateQuiz_withoutJwtToken_isUnauthorized() throws Exception {
    mockMvc.perform(patch("/api/v1/1/quizzes/1")).andExpect(status().isUnauthorized());
  }

  @Test
  void findById_withCorrectArg_returnOk() throws Exception {
    when(quizService.findById(anyLong()))
        .thenReturn(QuizMapper.quizToQuizDto(QuizGenerator.generateQuiz()));

    mockMvc
        .perform(
            get("/api/v1/1/quizzes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(REQUEST_BODY)
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateMentor())))
        .andExpect(status().isOk());
  }

  @Test
  void findById_withoutJwtToken_isUnauthorized() throws Exception {
    mockMvc.perform(get("/api/v1/1/quizzes/1")).andExpect(status().isUnauthorized());
  }

  @Test
  void findAllByModuleId_withCorrectArg_returnOk() throws Exception {
    when(quizService.findAllByModuleId(anyLong(), any()))
        .thenReturn(QuizGenerator.generateQuizDtoPage());
    mockMvc
        .perform(
            get("/api/v1/1/quizzes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(REQUEST_BODY)
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateMentor())))
        .andExpect(status().isOk());
  }

  @Test
  void findByModuleId_withoutJwtToken_isUnauthorized() throws Exception {
    mockMvc.perform(get("/api/v1/1/quizzes")).andExpect(status().isUnauthorized());
  }

  @Test
  void completeEnrolledQuiz_withCorrectArg_returnOk() throws Exception {
    when(quizService.completeEnrolledQuiz(anyLong(), any(), any()))
        .thenReturn(
            Optional.of(
                EnrolledQuizAssessmentResponseDtoGenerator
                    .generateEnrolledQuizAssessmentResponseDto("FAILED")));
    mockMvc
        .perform(
            get("/api/v1/1/quizzes/enrolledQuizzes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(REQUEST_BODY)
                .header(AUTHORIZATION, generateJwtToken(UserGenerator.generateMentor())))
        .andExpect(status().isOk());
  }

  @Test
  void completeEnrolledQuiz_withoutJwtToken_isUnauthorized() throws Exception {
    mockMvc
        .perform(get("/api/v1/1/quizzes/enrolledQuizzes/1"))
        .andExpect(status().isUnauthorized());
  }

  private String generateJwtToken(User user) {
    return "Bearer " + jwtTokenProvider.generateJwtToken(new UserPrincipal(user));
  }
}
