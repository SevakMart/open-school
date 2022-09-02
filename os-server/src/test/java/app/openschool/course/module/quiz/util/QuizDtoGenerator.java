package app.openschool.course.module.quiz.util;

import app.openschool.course.module.quiz.api.dto.CreateQuizDto;
import app.openschool.course.module.quiz.question.answer.api.dto.CreateAnswerOptionDto;
import app.openschool.course.module.quiz.question.api.dto.CreateQuestionDto;
import java.util.Set;

public final class QuizDtoGenerator {

  private QuizDtoGenerator() {}

  private static final int MAX_GRADE = 10;
  private static final int PASSING_SCORE = 7;
  private static final String QUESTION = "question";
  private static final String MULTIPLE_CHOICE = "MULTIPLE_CHOICE";
  private static final String ANSWER_OPTION = "answerOption";
  private static final Boolean IS_RIGHT_ANSWER = true;

  public static CreateQuizDto generateCreateQuizDto() {
    CreateQuizDto createQuizDto = CreateQuizDto.getInstance();
    createQuizDto.setMaxGrade(MAX_GRADE);
    createQuizDto.setPassingScore(PASSING_SCORE);
    createQuizDto.setQuestions(generateCreateQuestionDtoSet());
    return createQuizDto;
  }

  private static Set<CreateQuestionDto> generateCreateQuestionDtoSet() {
    CreateQuestionDto createQuestionDto = CreateQuestionDto.getInstance();
    createQuestionDto.setQuestion(QUESTION);
    createQuestionDto.setQuestionType(MULTIPLE_CHOICE);
    createQuestionDto.setAnswerOptions(generateCreateAnswerOptionDtoSet());
    return Set.of(createQuestionDto);
  }

  private static Set<CreateAnswerOptionDto> generateCreateAnswerOptionDtoSet() {
    CreateAnswerOptionDto createAnswerOptionDto = CreateAnswerOptionDto.getInstance();
    createAnswerOptionDto.setAnswerOption(ANSWER_OPTION);
    createAnswerOptionDto.setRightAnswer(IS_RIGHT_ANSWER);
    return Set.of(createAnswerOptionDto);
  }
}
