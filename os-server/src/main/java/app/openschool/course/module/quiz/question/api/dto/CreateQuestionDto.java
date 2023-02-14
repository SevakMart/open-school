package app.openschool.course.module.quiz.question.api.dto;

import app.openschool.course.module.quiz.question.answer.api.dto.CreateAnswerOptionDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

public class CreateQuestionDto {

  @Schema(
      description = "Regular quiz question or multiple choice question",
      example = "What is Java bytecode")
  private String question;

  @Schema(description = "The right answers count", example = "3")
  private int rightAnswersCount;

  @ArraySchema(schema = @Schema(implementation = CreateAnswerOptionDto.class))
  private Set<CreateAnswerOptionDto> answerOptions;

  @Schema(
      description = "One of quiz question type (MATCHING or MULTIPLE_CHOICE)",
      example = "MULTIPLE_CHOICE")
  private String questionType;

  public static CreateQuestionDto getInstance() {
    return new CreateQuestionDto();
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public Set<CreateAnswerOptionDto> getAnswerOptions() {
    return answerOptions;
  }

  public void setAnswerOptions(Set<CreateAnswerOptionDto> answerOptions) {
    this.answerOptions = answerOptions;
  }

  public String getQuestionType() {
    return questionType;
  }

  public void setQuestionType(String questionType) {
    this.questionType = questionType;
  }

  public int getRightAnswersCount() {
    return rightAnswersCount;
  }

  public void setRightAnswersCount(int rightAnswersCount) {
    this.rightAnswersCount = rightAnswersCount;
  }
}
