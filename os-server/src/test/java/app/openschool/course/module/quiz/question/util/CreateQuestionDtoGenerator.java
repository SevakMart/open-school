package app.openschool.course.module.quiz.question.util;

import app.openschool.course.module.quiz.question.answer.api.dto.CreateAnswerOptionDto;
import app.openschool.course.module.quiz.question.api.dto.CreateQuestionDto;
import java.util.Set;

public class CreateQuestionDtoGenerator {
  private static final String QUESTION = "question";
  private static final int RIGHT_ANSWER_COUNT = 1;
  private static final String MATCHING = "MATCHING";

  private CreateQuestionDtoGenerator() {}

  public static CreateQuestionDto generateCreateQuestionDto() {
    CreateQuestionDto createQuestionDto = CreateQuestionDto.getInstance();
    createQuestionDto.setQuestion(QUESTION);
    createQuestionDto.setRightAnswersCount(RIGHT_ANSWER_COUNT);
    createQuestionDto.setQuestionType(MATCHING);
    createQuestionDto.setAnswerOptions(Set.of(CreateAnswerOptionDto.getInstance()));
    return createQuestionDto;
  }
}
