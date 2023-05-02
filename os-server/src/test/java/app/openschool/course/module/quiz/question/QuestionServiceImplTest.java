package app.openschool.course.module.quiz.question;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import app.openschool.course.module.quiz.question.answer.api.dto.AnswerOptionDto;
import app.openschool.course.module.quiz.question.api.dto.CreateQuestionDto;
import app.openschool.course.module.quiz.question.api.dto.QuestionDto;
import app.openschool.course.module.quiz.question.util.CreateQuestionDtoGenerator;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceImplTest {
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
    when(questionRepository.findById(anyLong())).thenReturn(Optional.empty());

    setAuthentication(UserGenerator.generateStudent());

    Long questionId = 1L;
    assertFalse(questionService.deleteQuestion(1L));
  }

  @Test
  void updateQuestion_withCorrectQuestionId_returnTrue() {
    when(questionRepository.findById(anyLong()))
        .thenReturn(Optional.of(QuestionGenerator.generateQuestion()));

    setAuthentication(UserGenerator.generateMentor());

    Long questionId = 1L;
    assertTrue(
        questionService.updateQuestion(
            questionId, CreateQuestionDtoGenerator.generateCreateQuestionDto()));
  }

  @Test
  void updateQuestion_withInCorrectQuestionId_returnFalse() {
    when(questionRepository.findById(anyLong())).thenReturn(Optional.empty());

    Long questionId = 1L;
    assertFalse(questionService.updateQuestion(questionId, CreateQuestionDto.getInstance()));
  }

  @Test
  void updateQuestion_withIncorrectMentor_throwIllegalArgumentException() {
    when(questionRepository.findById(anyLong())).thenReturn(Optional.empty());

    setAuthentication(UserGenerator.generateStudent());

    Long questionId = 1L;
    assertFalse(questionService.updateQuestion(questionId, CreateQuestionDto.getInstance()));
  }

  @Test
  void findAllByModuleId_withCorrectArg_quizDtoPage() {
    doReturn(Optional.of(QuestionGenerator.generateQuestionPage()))
        .when(questionRepository)
        .findAllQuestionsByQuizId(anyLong(), any());
    PageRequest pageRequest = PageRequest.of(0, 1);
    Page<QuestionDto> actualPageQuestionDto = questionService.findAllByQuizId(1L, pageRequest);

    Page<QuestionDto> questionDtoPage = QuestionGenerator.generateQuestionDtoPage();
    assertEquals(
        actualPageQuestionDto.stream().findFirst().orElseThrow().getQuestion(),
        questionDtoPage.stream().findFirst().orElseThrow().getQuestion());
    assertEquals(
        actualPageQuestionDto.stream().findFirst().orElseThrow().getQuestionType(),
        questionDtoPage.stream().findFirst().orElseThrow().getQuestionType());
    assertEquals(
        actualPageQuestionDto.stream().findFirst().orElseThrow().getRightAnswersCount(),
        questionDtoPage.stream().findFirst().orElseThrow().getRightAnswersCount());
    assertEquals(
        actualPageQuestionDto.stream().findFirst().orElseThrow().getAnswerOptions().stream()
            .findFirst()
            .orElse(new AnswerOptionDto())
            .getAnswerOption(),
        questionDtoPage.stream().findFirst().orElseThrow().getAnswerOptions().stream()
            .findFirst()
            .orElse(new AnswerOptionDto())
            .getAnswerOption());
  }

  @Test
  void findAllByModuleId_withInCorrectModuleId_throwIllegalArgumentException() {
    when(questionRepository.findAllQuestionsByQuizId(anyLong(), any()))
        .thenReturn(Optional.empty());
    PageRequest pageRequest = PageRequest.of(0, 1);
    assertThatThrownBy(() -> questionService.findAllByQuizId(1L, pageRequest))
        .isInstanceOf(IllegalArgumentException.class);
  }

  private void setAuthentication(User user) {
    SecurityContextHolder.setContext(
        new SecurityContextImpl(
            new UsernamePasswordAuthenticationToken(user.getEmail(), null, null)));
  }
}
