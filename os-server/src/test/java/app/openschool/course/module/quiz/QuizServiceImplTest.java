package app.openschool.course.module.quiz;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.openschool.course.module.ModuleRepository;
import app.openschool.course.module.api.ModuleGenerator;
import app.openschool.course.module.quiz.api.dto.QuizDto;
import app.openschool.course.module.quiz.util.EnrolledQuizAssessmentRequestDtoGenerator;
import app.openschool.course.module.quiz.util.EnrolledQuizAssessmentResponseDtoGenerator;
import app.openschool.course.module.quiz.util.EnrolledQuizGenerator;
import app.openschool.course.module.quiz.util.QuizDtoGenerator;
import app.openschool.course.module.quiz.util.QuizGenerator;
import app.openschool.user.User;
import app.openschool.user.api.UserGenerator;
import java.util.Locale;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

@ExtendWith(MockitoExtension.class)
class QuizServiceImplTest {
  @Mock ModuleRepository moduleRepository;

  @Mock QuizRepository quizRepository;
  @Mock EnrolledQuizRepository enrolledQuizRepository;

  @Mock MessageSource messageSource;
  private QuizServiceImpl quizService;

  @BeforeEach
  void setUp() {
    quizService =
        new QuizServiceImpl(
            moduleRepository, quizRepository, enrolledQuizRepository, messageSource);
  }

  @AfterEach
  void cleanUp() {
    SecurityContextHolder.clearContext();
  }

  @Test
  void createQuiz_withCorrectArg_returnQuiz() {
    when(moduleRepository.findById(anyLong()))
        .thenReturn(Optional.of(ModuleGenerator.generateModule()));
    when(quizRepository.save(any())).thenReturn(QuizGenerator.generateQuiz());
    Quiz actualQuiz = quizService.createQuiz(1L, QuizDtoGenerator.generateCreateQuizDto()).get();
    Quiz expectedQuiz = QuizGenerator.generateQuiz();
    assertEquals(actualQuiz.getDescription(), expectedQuiz.getDescription());
    assertEquals(actualQuiz.getTitle(), expectedQuiz.getTitle());
    assertEquals(actualQuiz.getMaxGrade(), expectedQuiz.getMaxGrade());
    assertEquals(actualQuiz.getPassingScore(), expectedQuiz.getPassingScore());
  }

  @Test
  void createQuiz_withIncorrectModuleId_returnEmptyOptional() {
    Long moduleId = 1L;
    when(moduleRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertEquals(quizService.createQuiz(moduleId, any()), Optional.empty());
  }

  @Test
  void createQuiz_withCorrectModuleId_returnOptionalOfQuiz() {
    Quiz quiz = Quiz.getInstance();
    when(moduleRepository.findById(anyLong()))
        .thenReturn(Optional.of(ModuleGenerator.generateModule()));
    when(quizRepository.save(any())).thenReturn(quiz);
    setAuthentication(UserGenerator.generateMentor());

    Long moduleId = 1L;
    assertEquals(
        quizService.createQuiz(moduleId, QuizDtoGenerator.generateCreateQuizDto()),
        Optional.of(quiz));
    verify(moduleRepository, times(1)).findById(anyLong());
    verify(quizRepository, times(1)).save(any());
  }

  @Test
  void deleteQuiz_withInCorrectQuizId_returnFalse() {
    when(quizRepository.findById(anyLong())).thenReturn(Optional.empty());

    Long quizId = 1L;
    assertEquals(quizService.deleteQuiz(quizId), false);
  }

  @Test
  void deleteQuiz_withCorrectQuizId_returnTrue() {
    when(quizRepository.findById(anyLong())).thenReturn(Optional.of(QuizGenerator.generateQuiz()));

    setAuthentication(UserGenerator.generateMentor());

    Long quizId = 1L;
    assertEquals(quizService.deleteQuiz(quizId), true);
  }

  @Test
  void updateQuiz_withCorrectArg_returnQuiz() {
    when(quizRepository.findById(anyLong())).thenReturn(Optional.of(QuizGenerator.generateQuiz()));
    when(quizRepository.save(any())).thenReturn(QuizGenerator.generateQuiz());

    Quiz actualQuiz = quizService.updateQuiz(1L, QuizDtoGenerator.generateModifyQuizDataRequest());
    Quiz expectedQuiz = QuizGenerator.generateQuiz();
    assertEquals(actualQuiz.getTitle(), expectedQuiz.getTitle());
    assertEquals(actualQuiz.getDescription(), expectedQuiz.getDescription());
    assertEquals(actualQuiz.getMaxGrade(), expectedQuiz.getMaxGrade());
    assertEquals(actualQuiz.getPassingScore(), expectedQuiz.getPassingScore());
  }

  @Test
  void updateQuiz_withInCorrectQuizId_throwIllegalArgumentException() {
    when(quizRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThatThrownBy(() -> quizService.updateQuiz(1L, any()))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void findById_withCorrectArg_returnQuiz() {
    when(quizRepository.findById(anyLong())).thenReturn(Optional.of(QuizGenerator.generateQuiz()));
    QuizDto actualQuizDto = quizService.findById(1L);
    QuizDto quizDto = QuizDtoGenerator.generateQuizDto();
    assertEquals(actualQuizDto.getTitle(), quizDto.getTitle());
    assertEquals(actualQuizDto.getDescription(), quizDto.getDescription());
    assertEquals(actualQuizDto.getMaxGrade(), quizDto.getMaxGrade());
    assertEquals(actualQuizDto.getPassingScore(), quizDto.getPassingScore());
  }

  @Test
  void findById_withInCorrectId_throwIllegalArgumentException() {
    when(quizRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThatThrownBy(() -> quizService.findById(1L)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void findAllByModuleId_withCorrectArg_quizDtoPage() {
    doReturn(QuizGenerator.generateQuizPage())
        .when(quizRepository)
        .findAllByModuleId(anyLong(), any());
    PageRequest pageRequest = PageRequest.of(0, 1);
    Page<QuizDto> actualPageQuizDto = quizService.findAllByModuleId(1L, pageRequest);
    Page<QuizDto> quizDtoPage = QuizGenerator.generateQuizDtoPage();
    assertEquals(
        actualPageQuizDto.stream().findFirst().orElseThrow().getTitle(),
        quizDtoPage.stream().findFirst().orElseThrow().getTitle());
    assertEquals(
        actualPageQuizDto.stream().findFirst().orElseThrow().getDescription(),
        quizDtoPage.stream().findFirst().orElseThrow().getDescription());
    assertEquals(
        actualPageQuizDto.stream().findFirst().orElseThrow().getMaxGrade(),
        quizDtoPage.stream().findFirst().orElseThrow().getMaxGrade());
    assertEquals(
        actualPageQuizDto.stream().findFirst().orElseThrow().getPassingScore(),
        quizDtoPage.stream().findFirst().orElseThrow().getPassingScore());
  }

  @Test
  void completeEnrolledQuiz_WithCorrectQuizId_returnOptionalOfEnrolledQuizAssessmentResponseDto() {
    String quizStatus = "FAILED";
    when(enrolledQuizRepository.findById(anyLong()))
        .thenReturn(Optional.of(EnrolledQuizGenerator.generateEnrolledQuiz()));
    when(messageSource.getMessage(anyString(), any(), any())).thenReturn(quizStatus);

    assertEquals(
        quizService.completeEnrolledQuiz(
            1L,
            EnrolledQuizAssessmentRequestDtoGenerator.generateEnrolledQuizAssessmentRequestDto(),
            Locale.ROOT),
        Optional.of(
            EnrolledQuizAssessmentResponseDtoGenerator.generateEnrolledQuizAssessmentResponseDto(
                quizStatus)));
  }

  @Test
  void completeEnrolledQuiz_WithIncorrectQuizId_returnEmptyOptional() {
    when(enrolledQuizRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertEquals(quizService.completeEnrolledQuiz(1L, null, null), Optional.empty());
  }

  private void setAuthentication(User user) {
    SecurityContextHolder.setContext(
        new SecurityContextImpl(
            new UsernamePasswordAuthenticationToken(user.getEmail(), null, null)));
  }
}
