package app.openschool.course.module.quiz.question;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import app.openschool.course.module.quiz.question.util.QuestionGenerator;
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
class QuestionServiceImplTest {

  @Mock QuestionRepository questionRepository;

  private QuestionServiceImpl questionService;

  @BeforeEach
  void setUp() {
    questionService = new QuestionServiceImpl(questionRepository);
  }

  @AfterEach
  void cleanUp() {
    SecurityContextHolder.clearContext();
  }

  @Test
  void deleteQuestion_withCorrectQuestionId_returnTrue() {
    when(questionRepository.findById(anyLong()))
        .thenReturn(Optional.of(QuestionGenerator.generateQuestion()));

    setAuthentication(UserGenerator.generateMentor());

    Long questionId = 1L;
    assertTrue(questionService.deleteQuestion(questionId));
  }

  @Test
  void deleteQuestion_withInCorrectQuestionId_returnFalse() {
    when(questionRepository.findById(anyLong())).thenReturn(Optional.empty());

    Long questionId = 1L;
    assertFalse(questionService.deleteQuestion(questionId));
  }

  @Test
  void deleteQuestion_withIncorrectMentor_throwIllegalArgumentException() {
    when(questionRepository.findById(anyLong()))
        .thenReturn(Optional.of(QuestionGenerator.generateQuestion()));

    setAuthentication(UserGenerator.generateStudent());

    Long questionId = 1L;
    assertThatThrownBy(() -> questionService.deleteQuestion(questionId))
        .isInstanceOf(IllegalArgumentException.class);
  }

  private void setAuthentication(User user) {
    SecurityContextHolder.setContext(
        new SecurityContextImpl(
            new UsernamePasswordAuthenticationToken(user.getEmail(), null, null)));
  }
}
