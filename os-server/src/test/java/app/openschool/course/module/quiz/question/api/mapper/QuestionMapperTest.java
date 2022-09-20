package app.openschool.course.module.quiz.question.api.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import app.openschool.course.module.quiz.Quiz;
import app.openschool.course.module.quiz.question.Question;
import app.openschool.course.module.quiz.question.util.CreateQuestionDtoGenerator;
import org.junit.jupiter.api.Test;

class QuestionMapperTest {

  @Test
  void createQuestionDtoToQuestion() {
    Question question =
        QuestionMapper.createQuestionDtoToQuestion(
            CreateQuestionDtoGenerator.generateCreateQuestionDto(), Quiz.getInstance());

    assertThat(question)
        .hasOnlyFields(
            "id", "question", "rightAnswersCount", "questionType", "answerOptions", "quiz");
  }
}
