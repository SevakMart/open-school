package app.openschool.course.module.quiz.api.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import app.openschool.course.module.Module;
import app.openschool.course.module.quiz.Quiz;
import app.openschool.course.module.quiz.api.dto.QuizDto;
import app.openschool.course.module.quiz.util.QuizDtoGenerator;
import app.openschool.course.module.quiz.util.QuizGenerator;
import org.junit.jupiter.api.Test;

class QuizMapperTest {

  @Test
  void createQuizDtoToQuiz() {
    Quiz quiz =
        QuizMapper.createQuizDtoToQuiz(
            QuizDtoGenerator.generateCreateQuizDto(), Module.getInstance());

    assertThat(quiz)
        .hasOnlyFields(
            "id", "maxGrade", "studentGrade", "passingScore", "quizStatus", "questions", "module");
  }

  @Test
  void quizToQuizDto() {
    QuizDto quizDto = QuizMapper.quizToQuizDto(QuizGenerator.generateQuiz());

    assertThat(quizDto)
        .hasOnlyFields(
            "id",
            "moduleId",
            "maxGrade",
            "studentGrade",
            "passingScore",
            "quizStatus",
            "questions");
  }
}
