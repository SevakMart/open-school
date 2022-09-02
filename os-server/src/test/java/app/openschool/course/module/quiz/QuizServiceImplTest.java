package app.openschool.course.module.quiz;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.openschool.course.module.ModuleRepository;
import app.openschool.course.module.quiz.util.QuizDtoGenerator;
import app.openschool.course.module.util.ModuleGenerator;
import app.openschool.user.User;
import app.openschool.user.api.UserGenerator;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

@ExtendWith(MockitoExtension.class)
class QuizServiceImplTest {

  @Mock ModuleRepository moduleRepository;

  @Mock QuizRepository quizRepository;

  private QuizServiceImpl quizService;

  @BeforeEach
  void setUp() {
    quizService = new QuizServiceImpl(moduleRepository, quizRepository);
  }

  @AfterEach
  void cleanUp() {
    SecurityContextHolder.clearContext();
  }

  @Test
  void createQuiz_withIncorrectModuleId_returnEmptyOptional() {
    Long moduleId = 1L;
    when(moduleRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertEquals(quizService.createQuiz(moduleId, any()), Optional.empty());
  }

  @Test
  void createQuiz_withIncorrectMentor_throwIllegalArgumentException() {
    Long moduleId = 1L;
    when(moduleRepository.findById(anyLong()))
        .thenReturn(Optional.of(ModuleGenerator.generateModule()));
    setAuthentication(UserGenerator.generateStudent());

    assertThatThrownBy(
            () -> quizService.createQuiz(moduleId, QuizDtoGenerator.generateCreateQuizDto()))
        .isInstanceOf(IllegalArgumentException.class);
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

  private void setAuthentication(User user) {
    SecurityContextHolder.setContext(
        new SecurityContextImpl(
            new UsernamePasswordAuthenticationToken(user.getEmail(), null, null)));
  }
}
