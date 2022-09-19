package app.openschool.course.module.quiz.api.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import app.openschool.course.module.Module;
import app.openschool.course.module.quiz.EnrolledQuiz;
import app.openschool.course.module.quiz.Quiz;
import app.openschool.course.module.quiz.api.dto.EnrolledQuizDto;
import app.openschool.course.module.quiz.api.dto.QuizDto;
import app.openschool.course.module.quiz.util.EnrolledQuizGenerator;
import app.openschool.course.module.quiz.util.QuizDtoGenerator;
import app.openschool.course.module.quiz.util.QuizGenerator;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class QuizMapperTest {

  @Test
  void createQuizDtoToQuiz() {
    Quiz quiz =
        QuizMapper.createQuizDtoToQuiz(
            QuizDtoGenerator.generateCreateQuizDto(), Module.getInstance());

    assertThat(quiz).hasOnlyFields("id", "maxGrade", "passingScore", "questions", "module");
  }

  @Test
  void quizToQuizDto() {
    QuizDto quizDto = QuizMapper.quizToQuizDto(QuizGenerator.generateQuiz());

    assertThat(quizDto).hasOnlyFields("id", "moduleId", "maxGrade", "passingScore", "questions");
  }

  @Test
  void toEnrolledQuizDtoSet() {
    Set<EnrolledQuiz> enrolledQuizSet = new HashSet<>();
    enrolledQuizSet.add(EnrolledQuizGenerator.generateEnrolledQuiz());
    Set<EnrolledQuizDto> enrolledQuizDtoSet =
        QuizMapper.enrolledQuizzesToEnrolledQuizDtoSet(enrolledQuizSet);

    assertEquals(1, enrolledQuizDtoSet.size());
  }
}
