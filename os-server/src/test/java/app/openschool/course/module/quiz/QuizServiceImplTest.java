package app.openschool.course.module.quiz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.openschool.course.module.Module;
import app.openschool.course.module.ModuleRepository;
import app.openschool.course.module.quiz.util.QuizDtoGenerator;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QuizServiceImplTest {

  @Mock ModuleRepository moduleRepository;

  @Mock QuizRepository quizRepository;

  private QuizServiceImpl quizService;

  @BeforeEach
  void setUp() {
    quizService = new QuizServiceImpl(moduleRepository, quizRepository);
  }

  @Test
  void createQuiz_withIncorrectModuleId_returnEmptyOptional() {
    Long moduleId = 1L;
    when(moduleRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertEquals(quizService.createQuiz(moduleId, any()), Optional.empty());
  }

  @Test
  void createQuiz_withCorrectModuleId_returnOptionalOfQuiz() {
    Long moduleId = 1L;
    Quiz quiz = Quiz.getInstance();
    when(moduleRepository.findById(anyLong())).thenReturn(Optional.of(Module.getInstance()));
    when(quizRepository.save(any())).thenReturn(quiz);

    assertEquals(
        quizService.createQuiz(moduleId, QuizDtoGenerator.generateCreateQuizDto()),
        Optional.of(quiz));
    verify(moduleRepository, times(1)).findById(anyLong());
    verify(quizRepository, times(1)).save(any());
  }
}
