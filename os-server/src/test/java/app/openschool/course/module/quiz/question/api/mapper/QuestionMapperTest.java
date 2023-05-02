package app.openschool.course.module.quiz.question.api.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import app.openschool.course.module.quiz.Quiz;
import app.openschool.course.module.quiz.question.Question;
import app.openschool.course.module.quiz.question.api.dto.QuestionDto;
import app.openschool.course.module.quiz.question.util.CreateQuestionDtoGenerator;
import app.openschool.course.module.quiz.question.util.QuestionGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

public class QuestionMapperTest {
  @Test
  void createQuestionDtoToQuestion() {
    Question question =
        QuestionMapper.createQuestionDtoToQuestion(
            CreateQuestionDtoGenerator.generateCreateQuestionDto(), Quiz.getInstance());

    assertThat(question)
        .hasOnlyFields(
            "id", "question", "rightAnswersCount", "questionType", "answerOptions", "quiz");
  }

  @Test
  void toQuestionDtoPage() {

    Page<QuestionDto> questionDtoPage =
        QuestionMapper.toQuestionDtoPage(QuestionGenerator.generateQuestionPage());

    assertThat(questionDtoPage).hasOnlyElementsOfTypes(QuestionDto.class);
  }

  @Test
  void toQuestionDto() {
    QuestionDto questionDto = QuestionMapper.toQuestionDto(QuestionGenerator.generateQuestion());
    assertThat(questionDto)
        .hasOnlyFields("id", "question", "rightAnswersCount", "questionType", "answerOptions");
  }
}
